package org.firstinspires.ftc.teamcode.Final_Code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.HashMap;
import java.util.List;

/**
 * @author Nerds of the North
 * Daniel created this but the original code structure is from Sami and Thor
 */
@SuppressWarnings("ALL")  
@Autonomous(name="Crater Autonomous", group="Linear Opmode")
public class AutonomousTask extends LinearOpMode {

    public int[] power = new int[] {1, 1, 1, 1};
    public int[] rotations = new int[] {1, 1, 1, 1};
    

    DcMotor leftFront, leftBack, rightFront, rightBack, armLift, armRotate;
    public Servo item;
    private ElapsedTime runtime = new ElapsedTime();
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";   //    ASSET
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";       //     GOLD
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";   //   SILVER
    //Nice plaintext API key :thumbs_up:
    private static final String VUFORIA_KEY = "AQYNN0//////AAABmeBGDwd4s0UkgGceRPKE4yeCzY2Nkmj7J15evwERwC16TDzbe1BbRpYNU3wMJJ5473aJgTzyjs/1eeI9Nq8EoXEN6lQVCO+04d0yUK2eKYEqlIC6+RXUQjgZDBV1wiBUOtMgD9qiQpmbrq17lRneXhDuWsfRR9iA7GGI4XhTINNRK5IV2d6242wnZLl913NPsb/yiwd4ltXvq2ZFIq4RXzgMgM8bpFuTHfe8tEWYguG6R7lRZ5W8IyJTe9RmXjcIeuROCz/32jWelgd+6p3ubE2JzquKplm7VC7XkLsnrHX5OaUHB/3IhtPGr/troy0vvqNJmigSL9V8fxVO4b/psyT6WCbhdLMjpsCWbnrJQ3Re";
    private final int waitTime = 50;
    private VuforiaLocalizer vuforia;  //  Vuforia Initilization
    private TFObjectDetector tfod;     //  ??
    private HashMap<String, Double[]> directions = new HashMap<String, Double[]>();
    private boolean foundGold = false;
    public int goldPos = -1;                   //used to decide how to turn to face the corner
    public int posOneRotation = 100;           //encoder value for turning to face the corner when the gold is on the left
	public int posTwoRotation = 1000;          //encoder value for turning to face the corner when the gold is in the center
	public int posThreeRotation = 10000;       //encoder value for turning to face the corner when the gold is on the right
    public int posOneMove = 100;               //encoder value to move forward into the corner when the gold is on the left
	public int posTwoMove = 1000;              //encoder value to move forward into the corner when the gold is in the center
	public int posThreeMove = 10000;           //encoder value to move forward into the corner when the gold is on the left
    /**
     * What runs this mess
     */

    public void moveDirection(String direction, Double power, int time){
        //idk why this is here but Sami put this every time he wanted to move so I'm putting it here
        leftFront.setMode(  DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(   DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(  DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Same with this block
        rightBack.setMode(  DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode( DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(  DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(   DcMotor.RunMode.RUN_USING_ENCODER);


        //Do movement

        //Calculate the time the robot should stop moving
        //TODO: Figure out if getRuntime() returns seconds or milliseconds (probably seconds)
        double stopTime = getRuntime() + time/1000.0;

        //`directions` stores the directions each motor needs to rotate to move in the specified manner
        leftFront.setPower(  directions.get(direction)[0]*power);
        rightFront.setPower( directions.get(direction)[1]*power);
        leftBack.setPower(   directions.get(direction)[2]*power);
        rightBack.setPower(  directions.get(direction)[3]*power);

        //Wait until it's time to stop (or stop button pressed)
        while(opModeIsActive() && getRuntime() < stopTime){
            idle();
        }

        //Stop the motors
        leftFront.setPower(  0.0);
        rightFront.setPower( 0.0);
        leftBack.setPower(   0.0);
        rightBack.setPower(  0.0);

        //TODO: Stop the program if the stop button was pressed
        //TODO: Petition for Java to have a GOTO statement, because that would make this *a lot* easier
    }

    @Override // Replace default method
    public void runOpMode() throws InterruptedException{
        directions.put("forwards",   (new Double[] { 1.0, -1.0,  1.0, -1.0}));
        directions.put("backwards",  (new Double[] {-1.0,  1.0, -1.0,  1.0}));
        directions.put("clockwise",  (new Double[] {-1.0, -1.0, -1.0, -1.0}));
        directions.put("counter",    (new Double[] { 1.0,  1.0,  1.0,  1.0}));
        directions.put("stop",       (new Double[] { 0.0,  0.0,  0.0,  0.0}));
        directions.put("right", (new Double[] { 1.0,  1.0, -1.0, -1.0}));
        directions.put("left", (new Double[] {-1.0, -1.0,  1.0,  1.0}));

        waitForStart();
        int move = 1;
        int armLocation = 3;

        initTask();
        //lander();
        //getVision();
        drop();
    }

    /**
     * Current "Screen"
     */
    public boolean repeat = true;
    public void getVision() throws InterruptedException{
        if (repeat){initVuforia();}

        //TODO: Whatever it takes to change ClassFactory to CheesecakeFactory
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {             // Sorry
            telemetry.addData("Connor", "This device is not compatible with TFOD");
        }

        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            /* Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive() && !foundGold) {
                if (tfod != null) {

                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());

                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;

                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX     =  (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X  =  (int) recognition.getLeft();
                                } else {
                                    silverMineral2X  =  (int) recognition.getLeft();
                                }
                            }
                            //Test to see if all the minerals can be seen
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                //Find which position the gold mineral is in

                                //A smaller x-value means more towards the left
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    //telemetry.update();
                                    moveDirection("counter",  0.25,400);
                                    moveDirection("forward",  0.25,2500);
                                    moveDirection("clockwise",0.25,800);
                                    moveDirection("stop",     0.25,waitTime);
                                    goldPos = 1;
                                    moveDrop();
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    //telemetry.update();
                                    moveDirection("clockwise",0.25,400);
                                    moveDirection("forward",  0.25,2500);
                                    moveDirection("counter",  0.25,800);
                                    moveDirection("stop",     0.25, waitTime);
                                    goldPos = 3;
                                    moveDrop();
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    //telemetry.update();
                                    moveDirection("forward",  0.25,2100);
                                    moveDirection("stop",     0.25, waitTime);
                                    goldPos = 2;
                                    moveDrop();
                                }
                                foundGold = true;
                            }
                        }
                        else if(repeat){
                            moveDirection("counter",  0.25,300);
                            moveDirection("stop",     0.25, waitTime);
                            repeat = false;
                            if (tfod != null) {
                                tfod.shutdown();
                            }
                            getVision();
                        } else {
                            moveDirection("counter",  0.25,10);
                            moveDirection("stop",     0.25, waitTime);
                        }
                        telemetry.update();
                    }
                }
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initializes Vuforia crap
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initializes whatever TFOD is
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    /**
     * Moves. That's it. What the heck else would it do.
     *
     * @param power      power / speed
     * @param location   wheels i guess, not sure
     */
    public void move(int[] power, int[] location) throws InterruptedException{
        int rotate = 2240;
        //rotate *= Math.PI;
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setTargetPosition(location[0]);
        leftFront.setPower(.25);
        leftBack.setTargetPosition(location[1]);
        leftBack.setPower(.25);
        rightFront.setTargetPosition(location[2]);
        rightFront.setPower(.25);
        rightFront.setTargetPosition(location[3]);
        rightFront.setPower(.25);
        while (opModeIsActive() &&
                (runtime.seconds() < 6) &&
                (leftBack.isBusy())) {
        }armLift.setPower(0);


       // Thread.sleep(100);
    }

    /**
     * Initializes whatever this monstrosity is
     */
    public void initTask(){

        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        armLift = hardwareMap.dcMotor.get("ArmLift");
        armRotate = hardwareMap.dcMotor.get("ArmRotate");

        item = hardwareMap.servo.get("dropper");

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /*  public void drive(int degree){
     *       int rotation = 2240;
     *       int[] location = new int[] {rotation * degree, rotation * degree, rotation * degree, rotation * degree}; //set zero to 360 degree rotation
     *       int[] power = new int[]{1, 1, 1, 1};
     *       move(power,location);
     *  }
     */

    /**
     * OH, HM, I BET IT MOVES THE ARM!? HUH!?
     *
     * @param power      power / speed
     * @param location   really not sure what
     */
    public void lander() throws InterruptedException{
        //Descending
        armMove();


        moveDirection("right",  0.25,300);
        moveDirection("stop",   0.25, waitTime);



        moveDirection("forward",0.25,200);
        moveDirection("stop",   0.25, waitTime);

        moveDirection("left",0.25,300);
        moveDirection("stop",   0.25, waitTime);

        moveDirection("forward",0.25,2000);
        moveDirection("stop",   0.25, waitTime);



    }
    public void drop() throws InterruptedException{

         for(int x = 0; x<1; x+=.1){item.setPosition(x); Thread.sleep(1);}


    };
    public void moveView(){
        //enter stuff here to get to the view
    }

    public void ArmRotate(int power, int location){
        //up down
        int rotation = 2240;

        armRotate.setTargetPosition(location/rotation);
        armRotate.setPower(power);
    }

    /**
     * 
     *
     * @param power      power / speed
     * @param location   ??
     */
    public void armMove(){
        //rotate arm

        int rotation = 2240;
        armLift.setTargetPosition(8700);
        armLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLift.setPower(1);
        while (opModeIsActive() &&
                (runtime.seconds() < 5) &&
                (armLift.isBusy())) {
    }armLift.setPower(0);}
}

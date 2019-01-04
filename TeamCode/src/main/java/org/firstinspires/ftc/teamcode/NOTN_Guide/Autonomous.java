package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 * @author Nerds of the North
 *         Sami wrote this, Connor (V) made it less ugly.
 *
 * For FIRST FTC, 'totally' copyrighted
 */
@SuppressWarnings("ALL")  //  Because they $%&@ing suck
@Autonomous(name="BUFFmaster AutoNomus", group="Linear Opmode")
public class AutoOp extends LinearOpMode {

    public int[] power = new int[] {1, 1, 1, 1};
    public int[] rotations = new int[] {1, 1, 1, 1};

    DcMotor leftFront, leftBack, rightFront, rightBack, armLift, armRotate;
    public Servo item;
    private ElapsedTime runtime = new ElapsedTime();
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";   //    ASSET
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";       //     GOLD
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";   //   SILVER
    private static final String VUFORIA_KEY = "AQYNN0//////AAABmeBGDwd4s0UkgGceRPKE4yeCzY2Nkmj7J15evwERwC16TDzbe1BbRpYNU3wMJJ5473aJgTzyjs/1eeI9Nq8EoXEN6lQVCO+04d0yUK2eKYEqlIC6+RXUQjgZDBV1wiBUOtMgD9qiQpmbrq17lRneXhDuWsfRR9iA7GGI4XhTINNRK5IV2d6242wnZLl913NPsb/yiwd4ltXvq2ZFIq4RXzgMgM8bpFuTHfe8tEWYguG6R7lRZ5W8IyJTe9RmXjcIeuROCz/32jWelgd+6p3ubE2JzquKplm7VC7XkLsnrHX5OaUHB/3IhtPGr/troy0vvqNJmigSL9V8fxVO4b/psyT6WCbhdLMjpsCWbnrJQ3Re";

    private VuforiaLocalizer vuforia;  //  Vuforia Initilization
    private TFObjectDetector tfod;     //        idk wtf this is

    /**
     * What runs this mess
     */
    @Override // Replace default method
    public void runOpMode() throws InterruptedException{
        waitForStart();
        int move = 1;
        int armLocation = 3;

        initTask();
        //armMove();
        lander();
        getVision();
        //drop();
    }

    /**
     * Current "Screen"
     */
    public boolean repeat = true;
    public void getVision() throws InterruptedException{
        if (repeat){initVuforia();}


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

            while (opModeIsActive()) {
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

                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    //telemetry.update();
                                    leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    leftFront.setPower(.25);
                                    rightFront.setPower(.25);
                                    leftBack.setPower(.25);
                                    rightBack.setPower(.25);
                                    Thread.sleep(400);
                                    leftFront.setPower(.25);
                                    rightFront.setPower(-.25);
                                    leftBack.setPower(.25);
                                    rightBack.setPower(-.25);
                                    Thread.sleep(2500);
                                    leftFront.setPower(-.25);
                                    rightFront.setPower(-.25);
                                    leftBack.setPower(-.25);
                                    rightBack.setPower(-.25);
                                    Thread.sleep(800);
                                    leftFront.setPower(0);
                                    rightFront.setPower(0);
                                    leftBack.setPower(0);
                                    rightBack.setPower(0);
                                    moveDrop();
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    //telemetry.update();
                                    leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    leftFront.setPower(-.25);
                                    rightFront.setPower(-.25);
                                    leftBack.setPower(-.25);
                                    rightBack.setPower(-.25);
                                    Thread.sleep(400);
                                    leftFront.setPower(.25);
                                    rightFront.setPower(-.25);
                                    leftBack.setPower(.25);
                                    rightBack.setPower(-.25);
                                    Thread.sleep(2500);
                                    leftFront.setPower(.25);
                                    rightFront.setPower(.25);
                                    leftBack.setPower(.25);
                                    rightBack.setPower(.25);
                                    Thread.sleep(800);
                                    leftFront.setPower(0);
                                    rightFront.setPower(0);
                                    leftBack.setPower(0);
                                    rightBack.setPower(0);
                                    moveDrop();

                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    //telemetry.update();
                                    leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                    rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                                    leftFront.setPower(.25);
                                    rightFront.setPower(-.25);
                                    leftBack.setPower(.25);
                                    rightBack.setPower(-.25);
                                    Thread.sleep(2100);
                                    leftFront.setPower(0);
                                    rightFront.setPower(0);
                                    leftBack.setPower(0);
                                    rightBack.setPower(0);
                                    moveDrop();

                                }
                            }
                        }
                        else if(repeat){
                            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            leftFront.setPower(.25);
                            rightFront.setPower(.25);
                            leftBack.setPower(.25);
                            rightBack.setPower(.25);
                            Thread.sleep(300);
                            leftFront.setPower(0);
                            rightFront.setPower(0);
                            leftBack.setPower(0);
                            rightBack.setPower(0);
                            repeat = false;
                            if (tfod != null) {
                                tfod.shutdown();
                            }
                            getVision();
                        } else {
                            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            leftFront.setPower(-.25);
                            rightFront.setPower(-.25);
                            leftBack.setPower(-.25);
                            rightBack.setPower(-.25);
                            Thread.sleep(10);
                            leftFront.setPower(0);
                            rightFront.setPower(0);
                            leftBack.setPower(0);
                            rightBack.setPower(0);
                            Thread.sleep(25);
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
     * Moves. That's it. What the ^#@$ else would it do.
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
        armLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //armLift.setPower(.5);
        //Thread.sleep(2950);
        //armLift.setPower(0);
        armMove();
        leftFront.setPower(.25);
        rightFront.setPower(.25);
        leftBack.setPower(-.25);
        rightBack.setPower(-.25);
        Thread.sleep(400);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setPower(.25);
        rightFront.setPower(-.25);
        leftBack.setPower(.25);
        rightBack.setPower(-.25);
        Thread.sleep(50);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setPower(-.5);
        rightFront.setPower(-.5);
        leftBack.setPower(.5);
        rightBack.setPower(.5);
        Thread.sleep(300);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setPower(.25);
        rightFront.setPower(-.25);
        leftBack.setPower(.25);
        rightBack.setPower(-.25);
        Thread.sleep(100);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);



    }
    public void drop() throws InterruptedException{

         for(int x = 0; x<1; x+=.1){item.setPosition(x); Thread.sleep(1);}


    };
    public void moveView(){
        //enter stuff here to get to the view
    }
    public void moveDrop(){
        //enter stuff to get to the drop zone
    }
    public void ArmRotate(int power, int location){
        //up down
        int rotation = 2240;

        armRotate.setTargetPosition(location/rotation);
        armRotate.setPower(power);
    }

    /**
     * HM DUR DURRR, WONDER WHAT ROTATE ARM DOES!?!?1?!!11!?
     *
     * @param power      power / speed
     * @param location   idk wtf this is though
     */
    public void armMove(){
        //rotate arm

        int rotation = 2240;
        armLift.setTargetPosition(8750);
        armLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLift.setPower(1);
        while (opModeIsActive() &&
                (runtime.seconds() < 5) &&
                (armLift.isBusy())) {
    }armLift.setPower(0);}
}

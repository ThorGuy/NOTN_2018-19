package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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
@Autonomous(name="AutoNomus", group="Linear Opmode")
public class AutoOp extends LinearOpMode {

    public int[] power = new int[] {1,1,1,1};
    public double[] rotations = new double[] {1/4,1/4,1/4,1/4};

    DcMotor leftFront, leftBack, rightFront, rightBack, armLift, armRotate;

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
    public void runOpMode(){
        int move = 1;
        int armLocation = -2;

        initTask();
        armMove(move,armLocation);
        move(power, rotations);

        // Reassign all to 1
        rotations[1] = 1;
        rotations[2] = 1;
        rotations[3] = 1;
        rotations[4] = 1;

        move(power, rotations);

        rotations[1] = -1/4;
        rotations[2] = -1/4;
        rotations[3] = -1/4;
        rotations[4] = -1/4;

        move(power, rotations);
        getVision();
    }

    /**
     * Current "Screen"
     */
    public void getVision() {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {             // Sorry
            telemetry.addData("$#^@!", "This device is not compatible with TFOD");
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
                                    rotations[0] = 1/4;
                                    rotations[1] = 1/4;
                                    rotations[2] = -1/4;
                                    rotations[3] = -1/4;
                                    move(power,rotations);
                                    rotations[0] = 2;
                                    rotations[1] = 2;
                                    rotations[2] = 2;
                                    rotations[3] = 2;
                                    move(power,rotations);
                                    rotations[0] = 1/2;
                                    rotations[1] = 1/2;
                                    rotations[2] = -1/2;
                                    rotations[3] = -1/2;

                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    rotations[0] = -1/4;
                                    rotations[1] = -1/4;
                                    rotations[2] = 1/4;
                                    rotations[3] = 1/4;
                                    move(power,rotations);
                                    rotations[0] = 2;
                                    rotations[1] = 2;
                                    rotations[2] = 2;
                                    rotations[3] = 2;
                                    move(power,rotations);
                                    rotations[0] = 1/2;
                                    rotations[1] = 1/2;
                                    rotations[2] = -1/2;
                                    rotations[3] = -1/2;

                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    rotations[0] = 4;
                                    rotations[1] = 4;
                                    rotations[2] = 4;
                                    rotations[3] = 4;
                                    move(power,rotations);
                                }
                            }
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
    public void move(int[] power, double[] location){
        int rotate = 2240;
        //rotate *= Math.PI;
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setTargetPosition(location[0] * rotate);
        leftFront.setPower(power[0]);
        leftBack.setTargetPosition(location[1] * rotate);
        leftBack.setPower(power[1]);
        rightFront.setTargetPosition(location[2] * rotate);
        rightFront.setPower(power[2]);
        rightFront.setTargetPosition(location[3] * rotate);
        rightFront.setPower(power[3]);
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
    public void armMove(int power, int location){
        //up down
        int rotation = 2240;
        armRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRotate.setTargetPosition(location/rotation);
        armRotate.setPower(power);
    }

    /**
     * HM DUR DURRR, WONDER WHAT ROTATE ARM DOES!?!?1?!!11!?
     *
     * @param power      power / speed
     * @param location   idk wtf this is though
     */
    public void rotateArm(int power, int location){
        //rotate arm
        armLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int rotation = 2240;
        armLift.setTargetPosition(location*rotation);
        armLift.setPower(power);
    }
}

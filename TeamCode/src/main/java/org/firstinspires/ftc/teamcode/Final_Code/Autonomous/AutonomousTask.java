package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

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
 *         Sami wrote this, Connor (V) made it less ugly.
 *         Thorbachev tore down the walls of code
 *
 * For FIRST FTC, 'totally' copyrighted
 */
@SuppressWarnings("ALL")  //  Because they hecking inhale
@Autonomous(name="BUFFmaster AutoNomus", group="Linear Opmode")
public class AutonomousTask extends LinearOpMode {

    public int[] power = new int[] {1, 1, 1, 1};
    public int[] rotations = new int[] {1, 1, 1, 1};

    public Servo item;
    private ElapsedTime runtime = new ElapsedTime();
    //Nice plaintext API key :thumbs_up:
    private final int waitTime = 50;

    /**
     * What runs this mess
     */


    @Override // Replace default method
    public void runOpMode() throws InterruptedException{

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

    /**
     * Initializes Vuforia crap
     */


    /**
     * Initializes whatever TFOD is
     */

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
        armRotate = hardwareMap.dcMotor.get("ArmRotate");

        item = hardwareMap.servo.get("dropper");
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
     * @param location   idk wtp this is though
     */

}

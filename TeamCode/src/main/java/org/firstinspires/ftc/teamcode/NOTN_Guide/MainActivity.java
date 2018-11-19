package notn_guide.teamcode.ftc.firstinspires.org.ftcrobotics;

// go to fixed position (in encoder ticks) of linear slide
// Position is determined by global variable linear_position
// while it is running, it sets variable linear_moving to be true
package org.firstinspires.ftc.teamcode.NOTN_Guide;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@TeleOp(name = "linear motor", group = "linear motor")
public class MainActivity extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive, rightDrive, leftBack, rightBack = null;
    private DcMotor beltDrive = null;



    //
    @Override
    public void runOpMode() {



        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Initialize the hardware variables. Note that the strings used here as parameters
// to 'get' must correspond to the names assigned during the robot configuration
// step (using the FTC Robot Controller app on the phone).
        leftDrive = hardwareMap.get(DcMotor.class, "leftfront");
        rightDrive = hardwareMap.get(DcMotor.class, "rightfront");
        leftBack = hardwareMap.get(DcMotor.class, "leftfront");
        rightBack = hardwareMap.get(DcMotor.class, "rightfront");
//beltDrive = hardwareMap.get(DcMotor.class, "belt_drive");
// Most robots need the motor on one side to be reversed to drive forward
// Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
// Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

// run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

// Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;

// Choose to drive using either Tank Mode, or POV Mode

import android.os.Bundle;


            protected void positionLinear(Bundle savedInstanceState) {
                //indicate we are moving:
                boolean linear_moving = true; // compute distance to move
                int distanceToMove = nMotorEncoder[linear1]; // set target
                nMotorEncoder[linearl] = distanceToMove;
                wait1Msec(10); //wait for communication with the controller to complete
                if (distanceToMove > 0) {
                    nMotorEncoder[linearl] = 70;// moving up
                } else {
                    nMotorEncoder[linearl] = -70; // moving down
                }
                ;
                while (nMotorRunState[linearl] != runStateIdle) {
                }
                ;
                nMotorEncoder[linearl] = 0;
                linear_moving = false;
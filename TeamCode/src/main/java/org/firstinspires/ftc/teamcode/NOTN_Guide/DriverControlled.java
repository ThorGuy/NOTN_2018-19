package org.firstinspires.ftc.teamcode.NOTN_Guide; //Lets the library know that this code exists

import com.qualcomm.robotcore.eventloop.opmode.OpMode;   //Allows us to use the library
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode") //Tells the app this is an opmode
public class DriverControlled extends OpMode{ //Tells the library this is an opmode

	//Creating the motor variables
	private DcMotor leftFront, rightFront, leftBack, rightBack, arm_height, arm_rotation;
	private Servo sweeper;

    public void init(){
        //Initialization Code

        //Assigning the motor variables to the actual motors
        //Try/catch so we don't have to rebuild the code when we disconnect/reconnect a component on the robot
        try{
            leftFront = hardwareMap.dcMotor.get("leftFront");
            rightFront = hardwareMap.dcMotor.get("rightFront");
            leftBack = hardwareMap.dcMotor.get("leftBack");
            rightBack = hardwareMap.dcMotor.get("rightBack");
        }catch(Exception e){}

        try{
		    arm_height = hardwareMap.dcMotor.get("arm_height");
            arm_rotation = hardwareMap.dcMotor.get("arm_rotation");
            sweeper = hardwareMap.servo.get("sweeper");
        }catch(Exception e){}
    }

    public void loop() {
        //Code to run during the driver-control period
        if(drivetrainLoaded())
            driveTrain();
        if(armLoaded())
            arm();
    }
    public void driveTrain() {
        //Get the input stuff

        float xv = gamepad1.left_stick_x;
        float yv = gamepad1.left_stick_y;

        //Create a buffer, so not every slight movement of the joystick will make the robot move
        if (Math.abs(xv) < 0.3) xv = 0;
        if (Math.abs(yv) < 0.3) yv = 0;

				//Slow down movement by half is a is pressed
				if(gamepa1.a){
					xv *= 0.5;
					yv *= 0.5;
				}

        //Rotation of the robot
        int rv = 0;
        if (gamepad1.right_trigger > 0.5) rv++;
        if (gamepad1.left_trigger > 0.5) rv--;
        //rv is now -1, 0, or 1

//TODO:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //Simple mechanum drive
        //the power needs to be negated on the left side since the motors are facing the other direction
        //xv needs to be different for the front and back motors in order to move laterally
        //TODO: Drive Train
        if (rv != 0) {
            //rotate the robot
            rightFront.setPower(rv);
            rightBack.setPower(rv);
            leftFront.setPower(rv);
            leftBack.setPower(rv);
        } else {
            //move laterally
            rightFront.setPower(-yv + xv);
            rightBack.setPower(yv - xv);
            leftFront.setPower(yv + xv);
            leftBack.setPower(-yv - xv);
        }
    }

    public void arm(){
        //TODO: Arm
        float rotV = gamepad2.left_stick_y;
        float riseV = gamepad2.right_stick_y;

        double sweeperSpeed = 0.5;
        if(gamepad2.a) sweeperSpeed+=0.5;
        if(gamepad2.b) sweeperSpeed-=0.5;

        arm_height.setPower(riseV);
        arm_rotation.setPower(rotV);

        sweeper.setPosition(sweeperSpeed);

    }

    public boolean drivetrainLoaded(){
        return leftFront!=null && rightFront!=null && leftBack!=null && rightBack!=null;
    }
    public boolean armLoaded(){
        return arm_height!=null && arm_rotation!=null && sweeper!=null;
    }
}

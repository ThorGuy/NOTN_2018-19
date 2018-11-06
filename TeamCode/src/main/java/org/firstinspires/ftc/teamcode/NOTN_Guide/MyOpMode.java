package org.firstinspires.ftc.teamcode.NOTN_Guide; //Lets the library know that this code exists

import com.qualcomm.robotcore.eventloop.opmode.OpMode;   //Allows us to use the library
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode") //Tells the app this is an opmode
public class MyOpMode extends OpMode{ //Tells the library this is an opmode

	//Creating the motor variables
	private DcMotor leftFront, rightFront, leftBack, rightBack, arm_height, arm_rotation;

    public void init(){
        //Initialization Code

        //Assigning the motor variables to the actual motors

        leftFront = hardwareMap.dcMotor.get("leftFront");
		rightFront = hardwareMap.dcMotor.get("rightFront");
		leftBack = hardwareMap.dcMotor.get("leftBack");
		rightBack = hardwareMap.dcMotor.get("rightBack");
        
		arm_height = hardwareMap.dcMotor.get("arm_height");
        arm_rotation = hardwareMap.dcMotor.get("arm_rotation");
    }

    public void loop(){
        //Code to run during the driver-control period

        //Get the input stuff

        //Create a buffer, so not every slight movement of the joystick will make the robot move
        float xv = gamepad1.left_stick_x;
        float yv = gamepad1.left_stick_y;
        float rotV = gamepad2.left_stick_y;
        float riseV = gamepad2.right_stick_y;
        if(Math.abs(xv) < 0.3) xv = 0;
        if(Math.abs(yv) < 0.3) yv = 0;
        float rv = 0;

        if(gamepad1.right_trigger>0.5) rv++;
        if(gamepad1.left_trigger>0.5) rv--;
        //rv is now -1, 0, or 1

//TODO:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //Simple mechanum drive
        //the power needs to be negated on the left side since the motors are facing the other direction
        //xv needs to be different for the front and back motors in order to move laterally
        //TODO: Drive Train
        if(rv != 0){
            //rotate the robot
            rightFront.setPower(rv);
            rightBack.setPower(rv);
            leftFront.setPower(rv);
            leftBack.setPower(rv);
        }else{
            //move laterally
            rightFront.setPower(yv - xv);
            rightBack.setPower(yv + xv);
            leftFront.setPower(-yv - xv);
            leftBack.setPower(-yv + xv);
        }


        //TODO: Arm
        arm_height.setPower(riseV);
        arm_rotation.setPower(rotV);

    }
}

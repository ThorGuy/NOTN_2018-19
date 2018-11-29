package org.firstinspires.ftc.teamcode.NOTN_Guide; //Lets the library know that this code exists

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Dab on it", group="Iterative Opmode") //Tells the app this is an opmode
public class KMZMyOpMode extends OpMode{ //Tells the library this is an opmode

	//Creating the motor variables
	private DcMotor leftFront, rightFront, leftBack, rightBack, ArmRotate, ArmLift;
	private Servo item, dropper;
	private CRServo sweeper, rotate;
    boolean drop;
	float speedMultiplier == 1;
    public void init(){
        //Initialization Code

        //Assigning the motor variables to the actual motors
        //servoone = hardwareMap.
        leftFront = hardwareMap.dcMotor.get("leftFront");
		rightFront = hardwareMap.dcMotor.get("rightFront");
		leftBack = hardwareMap.dcMotor.get("leftBack");
		rightBack = hardwareMap.dcMotor.get("rightBack");
		ArmRotate = hardwareMap.dcMotor.get("ArmRotate");
        ArmLift = hardwareMap.dcMotor.get("ArmLift");

        item = hardwareMap.servo.get("item");
        rotate = hardwareMap.crservo.get("rotate");
        sweeper = hardwareMap.crservo.get("sweeper");
        dropper = hardwareMap.servo.get("dropper");
        telemetry.addData("Encoder: ",leftFront.getCurrentPosition());
		
    }

    public void loop(){
        //Code to run during the driver-control period

        //Get the input stuff

        //Create a buffer, so not every slight movement of the joystick will make the robot move
        double xv = gamepad1.left_stick_x;
        double yv = gamepad1.left_stick_y;
        if(Math.abs(xv) < 0.3) xv = 0;
        if(Math.abs(yv) < 0.3) yv = 0;
        float rv = 0;
        double repeat = 1.0;

        if(gamepad1.right_trigger>0.5) rv++;
        if(gamepad1.left_trigger>0.5) rv--;
        //rv is now -1, 0, or 1
//rotates arm to x degree
       /* if(gamepad2.dpad_up) {
            ArmRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmRotate.setTargetPosition(100);
            ArmRotate.setPower(1);
        }
        if(gamepad2.dpad_down) {
            ArmRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmRotate.setTargetPosition(00);
            ArmRotate.setPower(1);
        }*/
        if(gamepad2.dpad_down) {
            ArmRotate.setPower(1.0);
        }
        if(gamepad2.dpad_up) {
            ArmRotate.setPower(-1.0);
        }
        if(!gamepad2.dpad_down || !gamepad2.dpad_up){
            ArmRotate.setPower(0.0);
        }



//lifts arm for final stage
        if(gamepad2.a) {
            ArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmLift.setTargetPosition(100);
            ArmLift.setPower(1);
        }
//lifts arm back and forth
        if(gamepad2.left_stick_y>0.5 || gamepad2.left_stick_y<0.5){
            ArmLift.setPower(gamepad2.left_stick_y);
        }
//rotate sweeper
        if(gamepad2.x){
            sweeper.setPower(1);
        }
        if(gamepad2.b){
            sweeper.setPower(0);
        }
        if(gamepad2.y){
            rotate.setPower(1);
        }else{
            rotate.setPower(0.5);
        }
        if(gamepad2.right_bumper){
            item.setPosition(.5);
        }
        if(gamepad2.left_bumper){
            item.setPosition(0);
        }
        if(drop){
            dropper.setPosition(.5);
        }

//wheel movement
        if(rv!=0){
            //rotate the robot
            rightFront.setPower(rv);
            rightBack.setPower(rv);
            leftFront.setPower(rv);
            leftBack.setPower(rv);
        }else{
            //move laterally
            rightFront.setPower(yv * speedMultiplier - xv * speedMultiplier);
            rightBack.setPower(yv * speedMultiplier + xv * speedMultiplier);
            leftFront.setPower(-yv * speedMultiplier - xv * speedMultiplier);
            leftBack.setPower(-yv * speedMultiplier + xv * speedMultiplier);
						telemetry.addData("Testing encoders: ", rightFront.getCurrentPosition());
        }
        
//Changing speed
        		if(gamepad1.dpad_up){
								if(speedMultiplier <= .75){
										speedMultiplier += .25;
								}
						}
						
						if(gamepad1.dpad_down){
								if(.5 <= speedMultiplier){
										speedMultiplier -= .25;
								}
						}
				}
    }
}

package org.firstinspires.ftc.teamcode.NOTN_Guide; //Lets the library know that this code exists

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Tua Mater", group="Iterative Opmode") //Tells the app this is an opmode
public class KMZMyOpMode extends OpMode{ //Tells the library this is an opmode

	//Creating the motor variables
	private DcMotor leftFront, rightFront, leftBack, rightBack, ArmRotate, ArmLift;
	private Servo sweeper, rotate, item;
    double sweeperR;
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
        sweeper = hardwareMap.servo.get("sweeper");
        rotate = hardwareMap.servo.get("rotate");
        item = hardwareMap.servo.get("item");

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
        if(gamepad2.dpad_up) {

            ArmRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmRotate.setTargetPosition(100);
            ArmRotate.setPower(1);
        }
        if(gamepad2.dpad_down) {

            ArmRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmRotate.setTargetPosition(00);
            ArmRotate.setPower(1);
        }
//lifts arm for final stage
        if(gamepad2.a) {
            ArmLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmLift.setTargetPosition(100);
            ArmLift.setPower(1);
        }
//lifts arm back and forth
        if(gamepad2.left_stick_y!=0){
            ArmLift.setPower(gamepad2.left_stick_y);
        }
//rotate sweeper
        if(gamepad2.x){
            sweeperR+=0.1;
            Range.clip(sweeperR, 0.0, 1.0);
            sweeper.setPosition(sweeperR);
        }
        if(gamepad2.b){
            sweeperR-=0.1;
            Range.clip(sweeperR, 0.0, 1.0);
            sweeper.setPosition(sweeperR);
        }
        if(gamepad2.y){
            rotate.setPosition(repeat);
            if(repeat == 0){
                repeat += 1;
            }else{
                repeat -= 1;
            }
        }
        if(gamepad2.right_bumper){
            item.setPosition(.5);
        }
        if(gamepad2.left_bumper){
            item.setPosition(0);
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
            rightFront.setPower(yv - xv);
            rightBack.setPower(yv + xv);
            leftFront.setPower(-yv - xv);
            leftBack.setPower(-yv + xv);
						telemetry.addData("Testing encoders: ", rightFront.getCurrentPosition());
        }
    }
}

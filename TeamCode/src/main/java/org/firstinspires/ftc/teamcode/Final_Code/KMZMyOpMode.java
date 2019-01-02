package org.firstinspires.ftc.teamcode.Final_Code;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="BUFFMasterJay", group="Iterative Opmode") //Tells the app this is an opmode
public class KMZMyOpMode extends OpMode{ //Tells the library this is an opmode

	//Creating the motor variables
	private DcMotor leftFront, rightFront, leftBack, rightBack, ArmRotate, ArmLift;
	private Servo item, dropper, sweeper, rotate;
    boolean Rotate;
    boolean hasMotors,hasArm,hasBucket = false;
    float speedMultiplier = 1;
    public void init(){
        //Initialization Code

        //Assigning the motor variables to the actual motors
        //servoone = hardwareMap.
        try {
            leftFront = hardwareMap.dcMotor.get("leftFront");
            rightFront = hardwareMap.dcMotor.get("rightFront");
            leftBack = hardwareMap.dcMotor.get("leftBack");
            rightBack = hardwareMap.dcMotor.get("rightBack");
            hasMotors = true;
        }catch(Exception e){}

        try {
            ArmRotate = hardwareMap.dcMotor.get("ArmRotate");
            ArmLift = hardwareMap.dcMotor.get("ArmLift");
            hasArm = true;
        }catch(Exception e){}

        try{
            item = hardwareMap.servo.get("item");//rotates bucket
            rotate = hardwareMap.servo.get("rotate");
            sweeper = hardwareMap.servo.get("sweeper");
            hasBucket = true;
        }catch(Exception e){}
        dropper = hardwareMap.servo.get("dropper");

        if(hasMotors) {
            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        if(hasArm) {
            ArmRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ArmLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            ArmLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            ArmRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }

    public void loop(){
        //Code to run during the driver-control period
        if(hasMotors) {
            telemetry.addData("Encoder leftFront: ", leftFront.getCurrentPosition());
            telemetry.addData("Encoder rightFront: ", rightFront.getCurrentPosition());
            telemetry.addData("Encoder leftBack: ", leftBack.getCurrentPosition());
            telemetry.addData("Encoder rightBack: ", rightBack.getCurrentPosition());
        }
        if(hasArm) {
            telemetry.addData("Encoder Rotate: ", ArmRotate.getCurrentPosition());
            telemetry.addData("Encoder Lift: ", ArmLift.getCurrentPosition());
        }
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
       if(hasArm) {
           //Rotate arm
           if (gamepad2.dpad_down) {
               ArmRotate.setPower(0.75);
           } else if (gamepad2.dpad_up) {
               ArmRotate.setPower(-0.75);
           } else {
               ArmRotate.setPower(0.0);
           }
           //Lift arm
           if(Math.abs(gamepad2.left_stick_y)>0.2 ) {
               //if (ArmLift.getCurrentPosition() > -3400) {
               ArmLift.setPower(-gamepad2.left_stick_y);
               //}
           } else {ArmLift.setPower(0);}
       }



        if(hasBucket) {
            //rotate sweeper
            if (gamepad2.x) {
                dropper.setPosition(1.0);
            }

            telemetry.addData("Rotate", rotate.getPosition());
            if (gamepad2.y) {
                if (Rotate) {
                    rotate.setPosition(1);
                    Rotate = false;
                } else {
                    rotate.setPosition(0);
                    Rotate = true;
                }
            }

            if (gamepad2.right_bumper) {
                item.setPosition(.5);
            }
            if (gamepad2.left_bumper) {
                item.setPosition(0.02);
            }


            telemetry.addData("rTrigger", gamepad2.right_trigger);
            telemetry.addData("lTrigger", gamepad2.left_trigger);
            if (gamepad2.right_trigger > 0.5) {
                sweeper.setPosition(1);
            } else if (gamepad2.left_trigger > 0.5) {
                sweeper.setPosition(0);
            } else {
                sweeper.setPosition(0.5);
            }
        }
        if(hasMotors) {
            //wheel movement
            if (rv != 0) {
                //rotate the robot
                rightFront.setPower(-rv * speedMultiplier);
                rightBack.setPower(-rv * speedMultiplier);
                leftFront.setPower(-rv * speedMultiplier);
                leftBack.setPower(-rv * speedMultiplier);
            } else {
                //move laterally
                rightFront.setPower(-yv * speedMultiplier - xv * speedMultiplier);
                rightBack.setPower(-yv * speedMultiplier + xv * speedMultiplier);
                leftFront.setPower(yv * speedMultiplier - xv * speedMultiplier);
                leftBack.setPower(yv * speedMultiplier + xv * speedMultiplier);
            }
            if (gamepad1.dpad_left) {
                rightFront.setPower(-speedMultiplier);
                rightBack.setPower(speedMultiplier);
                leftFront.setPower(speedMultiplier);
                rightBack.setPower(-speedMultiplier);
            }
            if (gamepad1.dpad_right) {
                rightFront.setPower(speedMultiplier);
                rightBack.setPower(-speedMultiplier);
                leftFront.setPower(-speedMultiplier);
                rightBack.setPower(speedMultiplier);
            }
        }
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
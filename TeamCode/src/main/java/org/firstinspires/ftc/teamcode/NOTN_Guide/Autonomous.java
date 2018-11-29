package org.firstinspires.ftc.teamcode.NOTN_Guide;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;   //Allows us to use the library
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

public class Autonomous extends LinearOpMode{
    //This HashMap lets us select the values to set our motors to using a string instead of a number, so we can say "move forwards" instead of "move in direction 0"
    HashMap<String, Double[]> directions = new HashMap<String, Double[]>();
    DcMotor ArmLift;
    //Initializing motors in a list, so they can be selected with a for loop.
    DcMotor[] driveMotors;


    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        moveMotor(ArmLift, -100, 0.1);
//moveDirection("backwards", 100);
    }

    public void moveMotor(DcMotor motor, int encoderSteps, double power){
        motor.setTargetPosition(encoderSteps);
        motor.setPower(power);
        while (opModeIsActive() && motor.isBusy()){
            idle();
        }
        motor.setPower(0.0);
    }

    public void initialize(){
        ArmLift = hardwareMap.dcMotor.get("ArmLift");

        driveMotors[0] = hardwareMap.dcMotor.get("rightFront");
        driveMotors[1] = hardwareMap.dcMotor.get("rightBack");
        driveMotors[2] = hardwareMap.dcMotor.get("leftFront");
        driveMotors[3] = hardwareMap.dcMotor.get("leftBack");

        directions.put("forwards",  (new Double[] {-1.0,  1.0,  1.0, -1.0}));
        directions.put("backwards", (new Double[] { 1.0, -1.0, -1.0,  1.0}));
        directions.put("left",      (new Double[] { 1.0, -1.0,  1.0, -1.0}));
        directions.put("right",     (new Double[] {-1.0,  1.0, -1.0,  1.0}));
    }

    public void moveDirection(String direction, long encoderSteps) throws InterruptedException{
        //Set the motors' target positions
        for(int i=0;i<4;i++){
            driveMotors[i].setTargetPosition(encoderSteps);
        }
        //Tell the motors to start moving
        for(int i=0;i<4;i++){
            driveMotors[i].setPower(directions.get(direction)[i]*0.5);
        }
        //Wait for the motors reach target
        while (opModeIsActive() && leftMotor.isBusy()){
            idle();
        }
        //Tell the motors to stop moving
        for(int i=0;i<4;i++){
            driveMotors[i].setPower(0.0);
        }
    }
}

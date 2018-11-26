package org.firstinspires.ftc.teamcode.NOTN_Guide;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;   //Allows us to use the library
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

public class Autonomous extends LinearOpMode{
    //This HashMap lets us select the values to set our motors to using a string instead of a number, so we can say "move forwards" instead of "move in direction 0"
    HashMap<String, Double[]> directions = new HashMap<String, Double[]>();


    DcMotor[] motors = {
        hardwareMap.dcMotor.get("rightFront"),
        hardwareMap.dcMotor.get("rightBack"),
        hardwareMap.dcMotor.get("leftFront"),
        hardwareMap.dcMotor.get("leftBack"),
    };


    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        moveDirection("forward", 1000);
    }

    public void initialize(){
        directions.put("forwards",  (new Double[] {-1.0,  1.0,  1.0, -1.0}));
        directions.put("backwards", (new Double[] { 1.0, -1.0, -1.0,  1.0}));
        directions.put("left",      (new Double[] { 1.0, -1.0,  1.0, -1.0}));
        directions.put("right",     (new Double[] {-1.0,  1.0, -1.0,  1.0}));
    }

    public void moveDirection(String direction, int milliseconds) throws InterruptedException{
        //Set the motors to the correct speed to move in the specified direction
        for(int i=0;i<4;i++){
            motors[i].setPower(directions.get(direction)[i]);
        }
        //wait an amount of time to allow the robot to move in said direction
        wait(milliseconds);
        //set the motor's speed back to 0
        for(int i=0;i<4;i++){
            motors[i].setPower(0.0);
        }
    }
}

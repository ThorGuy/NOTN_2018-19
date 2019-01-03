package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

import java.util.HashMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
* @author Thor
* Handy code to use in all portions of the Autonomous methods
*/
public class autoUtil extends LinearOpMode{
    public static HashMap<String, Double[]> directions = new HashMap<String, Double[]>();
    public static DcMotor[] driveMotors = DcMotor[4];
    public static void init(){
        directions.put("forwards",  (new Double[] { 1.0, -1.0,  1.0, -1.0}));
        directions.put("backwards", (new Double[] {-1.0,  1.0, -1.0,  1.0}));
        directions.put("clockwise", (new Double[] {-1.0, -1.0, -1.0, -1.0}));
        directions.put("counter",   (new Double[] { 1.0,  1.0,  1.0,  1.0}));
        directions.put("stop",      (new Double[] { 0.0,  0.0,  0.0,  0.0}));
        directions.put("right",     (new Double[] { 1.0,  1.0, -1.0, -1.0}));
        directions.put("left",      (new Double[] {-1.0, -1.0,  1.0,  1.0}));

        driveMotors[0] = hardwareMap.dcMotor.get("leftFront");
        driveMotors[1] = hardwareMap.dcMotor.get("rightFront");
        driveMotors[2] = hardwareMap.dcMotor.get("leftBack");
        driveMotors[4] = hardwareMap.dcMotor.get("rightBack");


    }
    public void moveDirection(String direction, Double power, int time){
        //idk why this is here but Sami put this every time he wanted to move so I'm putting it here
        for(DcMotor motor : driveMotors){
          motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }


        //Do movement

        //Calculate the time the robot should stop moving
        double stopTime = getRuntime() + time;

        //`directions` stores the directions each motor needs to rotate to move in the specified manner
        for(int i=0;i<4;i++){
          driveMotors[i].setPower(directions.get(direction)[i]*power);
        }

        //Wait until it's time to stop (or stop button pressed)
        while(opModeIsActive() && getRuntime() < stopTime){
            idle();
        }

        //Stop the motors
        for(int i=0;i<4;i++){
          driveMotors[i].setPower(0.0);
        }
        
    }
}

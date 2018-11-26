package org.firstinspires.ftc.teamcode.NOTN_Guide;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;   //Allows us to use the library
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

public class Autonomous extends LinearOpMode{

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
        directions.put("forwards",(new Double[] {-1.0, 1.0, 1.0, -1.0}));
        directions.put("backwards",(new Double[] {1.0, -1.0, -1.0, 1.0}));
        directions.put("left",(new Double[] {1.0, -1.0, 1.0, -1.0}));
        directions.put("right",(new Double[] {-1.0, 1.0, -1.0, 1.0}));
    }

    public void moveDirection(String direction, int milliseconds) throws InterruptedException{
        for(int i=0;i<4;i++){
            motors[i].setPower(directions.get(direction)[i]);
        }
        wait(milliseconds);
    }
}

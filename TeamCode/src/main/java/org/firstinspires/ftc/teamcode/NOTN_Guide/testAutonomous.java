package org.firstinspires.ftc.teamcode.NOTN_Guide;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;   //Allows us to use the library
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

import java.util.HashMap;


public class testAutonomous extends LinearOpMode{

  final String path = "Crater-Auto";


  File file = new File(path);
  BufferedReader reader = new BufferedReader(new FileReader(file));

  HashMap<String, DcMotor> motors = new HashMap<String, DcMotor>();
  HashMap<String, Servo> servos = new HashMap<String, Servo>();

  public void runOpMode(){
    //Load the file
    telemetry.addData("File Path", path);
    String line="";
    telemetry.addData("Test",line);

    //Initialize motors and servos
    while(!(line = reader.readLine()).equals("start")){
      String[] args = line.split(":");

      if(args[0].equals("dcMotor")){
        motors.put(args[1],hardwareMap.dcMotor.get(args[2]));
      }else if(args[0].equals("servo")){
        servos.put(args[1],hardwareMap.servo.get(args[2]));
      }

    }

    //Wait until the start button is pressed
    waitForStart();

    //Go until "end" is reached
    while(!(line = reader.readLine()).equals("end")){
      //Read actions to be taken simultaneously
      while(!(line = reader.readLine()).equals("wait")){
        String[] args = line.split(":");
        //Test to see if a motor or servo needs to be moved
        if(motors.containsKey(args[0])){
          //Add the encoder ticks to move to the current encoder position to get the target position
          motors.get(args[0]).setTargetPosition(args[1]+motors.getCurrentPosition());
          //TODO:Possibly set power after "wait" is reached
          motors.get(args[0]).setPower(args[2]);
        }else{
          //TODO:Possibly set position after "wait" is reached
          servos.get(args[0]).setPosition(args[1]);
        }
      }
      //Wait for everything to reach their positions
      boolean wait = true;
      while(wait){
        //Test to see if all the motors have reached their positions
        wait = false;
        for(DcMotor motor : motors){
          if(motor.isBusy()){
            wait = true;
          }else{
            motor.setPower(0);
          }
        }
        idle();
      }
      //Time buffer to allow for any extra motion
      wait(500);
    }
  }
}

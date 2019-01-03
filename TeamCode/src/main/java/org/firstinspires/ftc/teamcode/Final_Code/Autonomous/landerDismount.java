package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class landerDismount extends LinearOpMode{
  private static DcMotor armLift;
  public static dismount(){
    armLift = hardwareMap.dcMotor.get("ArmLift");
    armRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    //Descending
    armMove();


    moveDirection("right",  0.25,300);
    moveDirection("stop",   0.25, waitTime);



    moveDirection("forward",0.25,200);
    moveDirection("stop",   0.25, waitTime);

    moveDirection("left",0.25,300);
    moveDirection("stop",   0.25, waitTime);

    moveDirection("forward",0.25,2000);
    moveDirection("stop",   0.25, waitTime);
  }
  private static void armMove(){
      //rotate arm

      int rotation = 2240;
      armLift.setTargetPosition(8700);
      armLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      armLift.setPower(1);
      while (opModeIsActive() &&
              (runtime.seconds() < 5) &&
              (armLift.isBusy())) {
  }armLift.setPower(0);}
}

package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Auto: Close to dropzone", group="Linear Opmode")
// Runs IF close to the Drop Zone.
public class autoStartClose extends LinearOpMode{

  public void runOpMode(){
    autoMain.runAuto("Close");
  }
}

package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class pushMineral extends LinearOpMode{
  public static boolean repeat = true;
  public static String pushMineral(autoUtil AUtil) throws InterruptedException{
      if (repeat){initVuforia();}

      //TODO: Whatever it takes to change ClassFactory to CheesecakeFactory
      if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
          initTfod();
      } else {             // Sorry
          telemetry.addData("Connor", "This device is not compatible with TFOD");
      }

      telemetry.update();
      waitForStart();

      if (opModeIsActive()) {
          /* Activate Tensor Flow Object Detection. */
          if (tfod != null) {
              tfod.activate();
          }

          while (opModeIsActive() && !foundGold) {
              if (tfod != null) {

                  List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                  if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());

                      if (updatedRecognitions.size() == 3) {
                          int goldMineralX = -1;
                          int silverMineral1X = -1;
                          int silverMineral2X = -1;

                          for (Recognition recognition : updatedRecognitions) {
                              if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                  goldMineralX     =  (int) recognition.getLeft();
                              } else if (silverMineral1X == -1) {
                                  silverMineral1X  =  (int) recognition.getLeft();
                              } else {
                                  silverMineral2X  =  (int) recognition.getLeft();
                              }
                          }
                          //Test to see if all the minerals can be seen
                          if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                              //Find which position the gold mineral is in

                              //A smaller x-value means more towards the left
                              if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                  telemetry.addData("Gold Mineral Position", "Left");
                                  //telemetry.update();
                                  AUtil.moveDirection("counter",  0.25,400);
                                  AUtil.moveDirection("forward",  0.25,2500);
                                  AUtil.moveDirection("clockwise",0.25,800);
                                  AUtil.moveDirection("stop",     0.25,waitTime);
                                  return "Left";
                              } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                  telemetry.addData("Gold Mineral Position", "Right");
                                  //telemetry.update();
                                  AUtil.moveDirection("clockwise",0.25,400);
                                  AUtil.moveDirection("forward",  0.25,2500);
                                  AUtil.moveDirection("counter",  0.25,800);
                                  AUtil.moveDirection("stop",     0.25, waitTime);
                                  return "Right";
                              } else {
                                  telemetry.addData("Gold Mineral Position", "Center");
                                  //telemetry.update();
                                  AUtil.moveDirection("forward",  0.25,2100);
                                  AUtil.moveDirection("stop",     0.25, waitTime);
                                  return "Center";
                              }
                              foundGold = true;
                          }
                      }
                      else if(repeat){
                          AUtil.moveDirection("counter",  0.25,300);
                          AUtil.moveDirection("stop",     0.25, waitTime);
                          repeat = false;
                          if (tfod != null) {
                              tfod.shutdown();
                          }
                          getVision();
                      } else {
                          AUtil.moveDirection("counter",  0.25,10);
                          AUtil.moveDirection("stop",     0.25, waitTime);
                      }
                      telemetry.update();
                  }
              }
          }
      }
      if (tfod != null) {
          tfod.shutdown();
      }
  }
}

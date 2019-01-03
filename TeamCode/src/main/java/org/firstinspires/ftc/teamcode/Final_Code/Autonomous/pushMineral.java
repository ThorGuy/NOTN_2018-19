package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class pushMineral extends LinearOpMode{


  private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";   //    ASSET
  private static final String LABEL_GOLD_MINERAL = "Gold Mineral";       //     GOLD
  private static final String LABEL_SILVER_MINERAL = "Silver Mineral";   //   SILVER
  private static final String VUFORIA_KEY = "AQYNN0//////AAABmeBGDwd4s0UkgGceRPKE4yeCzY2Nkmj7J15evwERwC16TDzbe1BbRpYNU3wMJJ5473aJgTzyjs/1eeI9Nq8EoXEN6lQVCO+04d0yUK2eKYEqlIC6+RXUQjgZDBV1wiBUOtMgD9qiQpmbrq17lRneXhDuWsfRR9iA7GGI4XhTINNRK5IV2d6242wnZLl913NPsb/yiwd4ltXvq2ZFIq4RXzgMgM8bpFuTHfe8tEWYguG6R7lRZ5W8IyJTe9RmXjcIeuROCz/32jWelgd+6p3ubE2JzquKplm7VC7XkLsnrHX5OaUHB/3IhtPGr/troy0vvqNJmigSL9V8fxVO4b/psyT6WCbhdLMjpsCWbnrJQ3Re";

  private static VuforiaLocalizer vuforia;  //  Vuforia Initilization
  private static TFObjectDetector tfod;
  private static boolean foundGold = false;
  private static boolean repeat = true;

  private static void initVuforia() {
      /*
       * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
       */
      VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

      parameters.vuforiaLicenseKey = VUFORIA_KEY;
      parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

      //  Instantiate the Vuforia engine
      vuforia = ClassFactory.getInstance().createVuforia(parameters);

      // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
  }

  private static void initTfod() {
      int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
        "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
      TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
      tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
      tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
  }

  public static String doPushMineral() throws InterruptedException{
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
                                  autoUtil.moveDirection("counter",  0.25,400);
                                  autoUtil.moveDirection("forward",  0.25,2500);
                                  autoUtil.moveDirection("clockwise",0.25,800);
                                  autoUtil.moveDirection("stop",     0.25,waitTime);
                                  return "Left";
                              } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                  telemetry.addData("Gold Mineral Position", "Right");
                                  //telemetry.update();
                                  autoUtil.moveDirection("clockwise",0.25,400);
                                  autoUtil.moveDirection("forward",  0.25,2500);
                                  autoUtil.moveDirection("counter",  0.25,800);
                                  autoUtil.moveDirection("stop",     0.25, waitTime);
                                  return "Right";
                              } else {
                                  telemetry.addData("Gold Mineral Position", "Center");
                                  //telemetry.update();
                                  autoUtil.moveDirection("forward",  0.25,2100);
                                  autoUtil.moveDirection("stop",     0.25, waitTime);
                                  return "Center";
                              }
                              foundGold = true;
                          }
                      }
                      else if(repeat){
                          autoUtil.moveDirection("counter",  0.25,300);
                          autoUtil.moveDirection("stop",     0.25, waitTime);
                          repeat = false;
                          if (tfod != null) {
                              tfod.shutdown();
                          }
                          getVision();
                      } else {
                          autoUtil.moveDirection("counter",  0.25,10);
                          autoUtil.moveDirection("stop",     0.25, waitTime);
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

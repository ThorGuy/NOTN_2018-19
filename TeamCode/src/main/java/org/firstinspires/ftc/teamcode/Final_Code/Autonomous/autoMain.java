package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

public class autoMain{
  public static runAuto(String startPos) throws InterruptedException{
    landerDismount.dismount();
    String mineralPosition = pushMineral.doPushMineral();
    //moveToDrop.move(startPos, mineralPosition)
    dropMarker.drop();
  }
}

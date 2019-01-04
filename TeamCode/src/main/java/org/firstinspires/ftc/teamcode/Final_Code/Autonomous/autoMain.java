package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

public class autoMain{
  public static runAuto(String startPos) throws InterruptedException{
    landerDismount.dismount();
    String mineralPosition = pushMineral.doPushMineral();
    MoveDrop.moveDrop(startPos, mineralPosition);
    dropMarker.drop();
  }
}

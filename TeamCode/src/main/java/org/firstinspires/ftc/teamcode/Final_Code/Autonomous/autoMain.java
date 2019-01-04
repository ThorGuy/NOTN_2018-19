package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

public class autoMain{

  public static runAuto(String startPos) throws InterruptedException{
    landerDismount.dismount(); // Dismount on start

    // Variable Assignment
    String mineralPosition  =  pushMineral.doPushMineral();
    boolean moveSuccess     =  MoveDrop.moveDrop(mineralPosition, startPos);

    if (moveSuccess) {
      dropMarker.drop();
    }
  }
}

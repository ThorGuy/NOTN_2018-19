package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

public class MoveDrop{
	public void moveDrop(String goldPos, String startPos){
		if(goldPos.equals("Left")){
			autoUtil.moveDirection("clockwise",0.25,posOneRot);
			autoUtil.moveDirection("forward",0.25,posOneMove);
		}else if(goldPos.equals("Center")){
			autoUtil.moveDirection("clockwise",0.25,posTwoRot);
			autoUtil.moveDirection("forward",0.25,posTwoMove);
		}else{
			autoUtil.moveDirection("clockwise",0.25,posThreeRot);
			autoUtil.moveDirection("forward",0.25,posThreeMove);
		}
	}
}

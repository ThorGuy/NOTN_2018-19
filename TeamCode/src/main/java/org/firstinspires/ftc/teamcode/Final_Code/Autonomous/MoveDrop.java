package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

public class MoveDrop{
	public boolean moveDrop(String goldPos, String startPos){
		if(goldPos.equals("Left")){
			autoUtil.moveDirection("clockwise",0.25,posOneRot);
			autoUtil.moveDirection("forward",0.25,posOneMove);
		}else if(goldPos.equals("Center")){
			autoUtil.moveDirection("clockwise",0.25,posTwoRot);
			autoUtil.moveDirection("forward",0.25,posTwoMove);
		}else if(goldPos.equals("Right")){
			autoUtil.moveDirection("clockwise",0.25,posThreeRot);
			autoUtil.moveDirection("forward",0.25,posThreeMove);
		}else{
			return false;
		}
		return true;
	}
}

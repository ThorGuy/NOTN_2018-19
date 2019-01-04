package org.firstinspires.ftc.teamcode.NOTN_Guide;

public class MoveDrop{
	public void moveDrop{
		if(goldPos == 1){
			moveDirection("clockwise",0.25,posOneRot);
			moveDirection("forward",0.25,posOneMove);
		}else if(goldPos == 2){
			moveDirection("clockwise",0.25,posTwoRot);
			moveDirection("forward",0.25,posTwoMove);
		}else{
			moveDirection("clockwise",0.25,posThreeRot);
			moveDirection("forward",0.25,posThreeMove);
		}
	}
}


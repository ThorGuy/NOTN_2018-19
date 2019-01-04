package org.firstinspires.ftc.teamcode.Final_Code.Autonomous;

public class MoveDrop {

		public int posOneRot     =  0;      //encoder value for turning to face the corner when the gold is on the left
		public int posTwoRot     =  0;      //encoder value for turning to face the corner when the gold is in the center
		public int posThreeRot   =  0;      //encoder value for turning to face the corner when the gold is on the right
    public int posOneMove    =  0;      //encoder value to move forward into the corner when the gold is on the left
		public int posTwoMove    =  0;      //encoder value to move forward into the corner when the gold is in the center
		public int posThreeMove  =  0;      //encoder value to move forward into the corner when the gold is on the left

		public boolean moveDrop(String goldPos, String startPos){

				if(startPos == Close){
						posOneRot     =  10;
						posTwoRot     =  500;
						posThreeRot   =  1000;
						posOneMove    =  10;
						posTwoMove    =  500;
						posThreeMove  =  1000;
				} else {
						posOneRot     =  -10;
						posTwoRot     =  -500;
						posThreeRot   =  -1000;
						posOneMove    =  -10;
						posTwoMove    =  -500;
						posThreeMove  =  -1000;
				}

				if (goldPos.equals("Left")) {
						autoUtil.moveDirection("clockwise", 0.25, posOneRot);
						autoUtil.moveDirection("forward", 0.25, posOneMove);
				} else if (goldPos.equals("Center")) {
						autoUtil.moveDirection("clockwise", 0.25, posTwoRot);
						autoUtil.moveDirection("forward", 0.25, posTwoMove);
				} else if (goldPos.equals("Right")) {
						autoUtil.moveDirection("clockwise", 0.25, posThreeRot);
						autoUtil.moveDirection("forward", 0.25, posThreeMove);
				} else {
						return false;
				}
		return true;
		}
}

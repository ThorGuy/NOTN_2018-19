package org.firstinspires.ftc.teamcode.NOTN_Guide;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Just made a cleaned up version, not sure if this works.
 *
 * @author Connor Vann, stole half of this from Kathryn
 */
@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
public class CSVCrappyTeleOpThing extends OpMode {

  private DcMotor leftFront, rightFront, leftBack, rightBack, ArmRotate, ArmLift;
  private float velocityX = 0.0f, velocityY = 0.0f, rotationalVelocity = 0.0f;

  public void init() { // Initialize Values
    leftFront  = hardwareMap.dcMotor.get("leftFront");
    rightFront = hardwareMap.dcMotor.get("rightFront");
    leftBack   = hardwareMap.dcMotor.get("leftBack");
    rightBack  = hardwareMap.dcMotor.get("rightBack");
    ArmRotate  = hardwareMap.dcMotor.get("ArmRotate");
    ArmLift    = hardwareMap.dcMotor.get("ArmLift");
  }

  public void loop() { // Functions as you would imagine
    if(gamepad1.left_stick_x > 0.3f)
      velocityX = gamepad1.left_stick_x;
    else velocityX = 0.0f;

    if(gamepad1.left_stick_y > 0.3f)
      velocityY = gamepad1.left_stick_y;
    else velocityY = 0.0f;

    rotationalVelocity = 0.0f;
    if(gamepad1.right_trigger > 0.5f && gamepad1.left_trigger > 0.5f) {
      rotationalVelocity = 0.0f;
    } else if (gamepad1.right_trigger > 0.5f) {
      rotationalVelocity = 1.0f;
    } else if (gamepad1.left_trigger > 0.5f) {
      rotationalVelocity = -1.0f;
    }

    if(rotationalVelocity != 0.0f) {
      rightFront.setPower(rotationalVelocity);
      rightBack.setPower(rotationalVelocity);
      leftFront.setPower(rotationalVelocity);
      leftBack.setPower(rotationalVelocity);
    } else {
      rightFront.setPower(velocityY + velocityX);
      rightBack.setPower(velocityY - velocityX);
      leftFront.setPower(velocityY + velocityX);
      leftBack.setPower(velocityY - velocityX);
    }
  }

  public float currentVelocityX() {
    return velocityX;
  }

  public float currentVelocityY() {
    return velocityY;
  }

  public float currentRotationalVelocity() {
    return rotationalVelocity;
  }
}

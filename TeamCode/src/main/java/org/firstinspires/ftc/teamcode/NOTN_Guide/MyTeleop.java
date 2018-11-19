package org.firstinspires.ftc.teamcode.NOTN_Guide;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;   //Allows us to use the library
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
public class MyTeleop extends OpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack, ArmRotate, ArmLift;

    public void init(){
        //Initialization Code

        //Assigning the motor variables to the actual motors
        //servoone = hardwareMap.
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        ArmRotate = hardwareMap.dcMotor.get("ArmRotate");
        ArmLift = hardwareMap.dcMotor.get("ArmLift");

}
public void loop(){
    float lrmovement = gamepad1.left_stick_x;
    float fbmovement = gamepad1.left_stick_y;
    float rotation = gamepad1.right_stick_x;
    double [] vecsetone = new double[4];
    vecsetone[1] = fbmovement;
    vecsetone[2] = fbmovement;
    vecsetone[3] = fbmovement;
    vecsetone[4] = fbmovement;
    double [] vecsettwo = new double[4];
    vecsettwo[1] = lrmovement;
    vecsettwo[2] = lrmovement*-1;
    vecsettwo[3] = lrmovement*-1;
    vecsettwo[4] = lrmovement;
    double [] vecsetthree = new double[4];
    vecsetthree[1] = rotation;
    vecsetthree[2] = rotation*-1;
    vecsetthree[3] = rotation;
    vecsetthree[4] = rotation*-1;
    double [] finalvecs = new double[4];
    finalvecs[1] = (vecsetone[1]+vecsettwo[1]+vecsetthree[1])/3;
    finalvecs[2] = (vecsetone[2]+vecsettwo[2]+vecsetthree[2])/3;
    finalvecs[3] = (vecsetone[3]+vecsettwo[3]+vecsetthree[3])/3;
    finalvecs[4] = (vecsetone[4]+vecsettwo[4]+vecsetthree[4])/3;

    leftFront.setPower(finalvecs[1]);
    rightFront.setPower(finalvecs[2]);
    leftBack.setPower(finalvecs[3]);
    rightBack.setPower(finalvecs[4]);
    ArmLift.setPower(gamepad2.left_stick_y);
    ArmRotate.setPower(gamepad2.right_stick_y);



    }

}
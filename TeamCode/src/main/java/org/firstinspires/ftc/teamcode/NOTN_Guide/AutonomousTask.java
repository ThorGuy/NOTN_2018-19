import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

/**
 * Uses Vuforia to accomplish the Autonomous Task
 *
 * @author Nerds of the North
 */
public class AutonomousTask extends LinearOpMode {

  private static final String LICENSE_KEY = "ASi9SYr/////AAABma/cyoOFHEPdrzkaih5zX/Z/ynaGPQlAyfODszkNWyNcQzAptBle2+ojpu2121BH/a7WdgOwfFDZLHOSpQKCL0j2ZVq2o2qrsL3Dmqc/WPBkAXcatv5rWSnlRVRUcFbdVH92oWrmbMEPs70sCC/n1w8k8KnS0yNfW/OhsbJNnsIhX5m/zmvrR1ufBvfMzPi1ncVuM07xlglIChjWvXhMf+5/7hiyNePujoCw+aQgHrMI2vWCFLAsK1AK7+9q0w/zUdS6irTc38WsniwHOlvr13N2DmHU6NI2P4Kx5Z0z2AsLVoqZ+P+4EgTccPsabXruLkbsKnx34am/8FUlI/TfG98PsxGQoLh4deAv8vICruOD";
  private VuforiaLocalizer vuforia;
  private OpenGLMatrix lastLocation;

  float mmPerInch = 25.4f;
  float mmBotWidth, mmFTCFieldWidth;

  public AutonomousTask(float mmBotWidth, float mmFTCFieldWidth) {
    this.mmBotWidth = mmBotWidth;
    this.mmFTCFieldWidth = mmFTCFieldWidth;
  }

  @Override
  public void runOpMode() {
    
    int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
    VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
    parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
    vuforia = ClassFactory.getInstance().createVuforia(parameters);

    VuforiaTrackables roverRuckus    = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
    VuforiaTrackable  bluePerimeter  = roverRuckus.get(0);
    VuforiaTrackable  redPerimeter   = roverRuckus.get(1);
    VuforiaTrackable  frontPerimeter = roverRuckus.get(2);
    VuforiaTrackable  backPerimeter  = roverRuckus.get(3);
    bluePerimeter.setName("BluePerimeter");
    redPerimeter.setName("RedPerimeter");
    frontPerimeter.setName("FrontPerimeter");
    backPerimeter.setName("BackPerimeter");

    List<VuforiaTrackable> allTrackables = new ArrayList<>();
    allTrackables.addAll(roverRuckus);

    OpenGLMatrix redLocation = OpenGLMatrix
            .translation(-mmFTCFieldWidth/2, 0, 0) // TODO Fix Translation
            .multiplied(Orientation.getRotationMatrix(
                    AxesReference.EXTRINSIC, AxesOrder.XZX,
                    AngleUnit.DEGREES, 90, 90, 0));
    redPerimeter.setLocation(redLocation);
    RobotLog.ii(TAG, "Red Target = %s", format(redTargetLocationOnField));
  }

  String format(OpenGLMatrix transformationMatrix) {
      return transformationMatrix.formatAsTransform();
  }
}

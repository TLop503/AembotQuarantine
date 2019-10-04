package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * This OpMode is used for testing skystone positions
 * @author Will Richards
 */
@TeleOp(name = "Vuforia Track Test", group = "Test")
@Disabled
public class VuforiaTrackTest extends OpMode {

    OpenGLMatrix lastLocation = null;

    //Create the actual tracking object
    VuforiaLocalizer vuforia;

    //Holds the possible objects to track
    VuforiaTrackables trackables;

    //The object we want to track
    VuforiaTrackable skystoneTarget;

    //API Key for using Vuforia
    private final String VUFORIA_KEY = "AXYyTwz/////AAAAGSZopKRDQkUWstJxKlGUqq0dBRoXhOdaJSvoDa+Wq00FKnLAUYSqP4OENgv1Q9BVMXFj9LG6L69Wc3fbJuVL7ZetjnVLIzjd9Cn9hvh5rp6HiSJ1rFrlfx0sgtkHda7a/B7HivbiVjfXq+Dta1L3IgQ+GSEmvdkXioXG6kA5ZDpQ8yG2o4cyzzvWTBzBQbrHumt1ek8qcGYAiv+552WCDMdTvrMC+NQf5R+CQdzub9pHC1rHEu6fCpQT9oq+zM6Vk3TMwxQ8KWhII0AXh5815A0yCvSyMqQFX++empRQj9o/hT6rSfz6hsHCeSg/RxptC2TpsfSos14e6rswW/Z/dh45fI3YTNfxxrkfzPjr2NAY";

    @Override
    public void init() {

        //Gets the camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        //Creates a new set of localizer parameters based off the camera
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //Assign the parameters to the vuforia instance
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        //Load the game images
        trackables = this.vuforia.loadTrackablesFromAsset("Skystone");

        //Create a new target with the skystone as the src
        skystoneTarget = trackables.get(0);
        skystoneTarget.setName("Sky Stone");


    }

    @Override
    public void loop() {

        //If the skystone is visible the display the name and the X offset
        if(((VuforiaTrackableDefaultListener)skystoneTarget.getListener()).isVisible()){
            telemetry.addData("Visible Target: ", skystoneTarget.getName());

            //The getPosePhone returns a location of the object relative to the phone's center, the 0th index is the X axis
            telemetry.addData("X:", ((VuforiaTrackableDefaultListener) skystoneTarget.getListener()).getPosePhone().getData()[0]);
        }
    }

    /**
     * Called once when the robot is first enabled
     * Activates the tracking
     */
    @Override
    public  void init_loop(){ trackables.activate();}

}

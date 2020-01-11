package org.firstinspires.ftc.teamcode.Utilities.Vuforia;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Class used to allow easy access to Vuforia methods
 * @author FTC Team 6443
 */
public class VuforiaWrapper {

    //API Key for using Vuforia
    private final String VUFORIA_KEY = "AXYyTwz/////AAAAGSZopKRDQkUWstJxKlGUqq0dBRoXhOdaJSvoDa+Wq00FKnLAUYSqP4OENgv1Q9BVMXFj9LG6L69Wc3fbJuVL7ZetjnVLIzjd9Cn9hvh5rp6HiSJ1rFrlfx0sgtkHda7a/B7HivbiVjfXq+Dta1L3IgQ+GSEmvdkXioXG6kA5ZDpQ8yG2o4cyzzvWTBzBQbrHumt1ek8qcGYAiv+552WCDMdTvrMC+NQf5R+CQdzub9pHC1rHEu6fCpQT9oq+zM6Vk3TMwxQ8KWhII0AXh5815A0yCvSyMqQFX++empRQj9o/hT6rSfz6hsHCeSg/RxptC2TpsfSos14e6rswW/Z/dh45fI3YTNfxxrkfzPjr2NAY";

    //Localizer object
    private VuforiaLocalizer vuforia;

    //Trackable object set
    private VuforiaTrackables trackables;

    //The object we want to track
    VuforiaTrackable skystoneTarget;

    /**
     * Constructs the variables required for the class
     * @param hardwareMap a reference to the hardwareMap
     */
    public VuforiaWrapper(HardwareMap hardwareMap){

        //Gets the camera id
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        //Creates a parameter set for the vuforia object
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //Assigns the API key to the localizer object
        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        //Sets the camera to the back
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //Creates a new vuforia object with the parameters
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        //Loads the Skystone trackable set
        trackables = vuforia.loadTrackablesFromAsset("Skystone");

        //Create a new trackable with the skystone images as the src, and names it "Sky Stone"
        skystoneTarget = trackables.get(0);
        skystoneTarget.setName("Sky Stone");
    }

    /**
     * Get the detected object's transform if no object return null
     * @return detected objects transform, if non null
     */
    private String getDetectedTransform(){

        try {
            //If the target is present then return the trasnform if not return null
            if(((VuforiaTrackableDefaultListener)skystoneTarget.getListener()).isVisible()){
                return ((VuforiaTrackableDefaultListener)skystoneTarget.getListener()).getPosePhone().formatAsTransform();
            }
        }
        catch (NullPointerException e){ return  ""; }

        return "";
    }

    /**
     * Get the orientation of a Skystone relative to the robot.
     * @return The Orientation of the Skystone
     */
    public Orientation getStoneOrientation() {
        OpenGLMatrix skystonePose;

        try {
            skystonePose = ((VuforiaTrackableDefaultListener) skystoneTarget.getListener()).getPose();

            if(skystonePose != null) {
                return Orientation.getOrientation(skystonePose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            }
        }
        catch (NullPointerException e) { }

        return null;
    }

    public double getStoneAngle() {
        OpenGLMatrix pose;

        try {
            pose = ((VuforiaTrackableDefaultListener)skystoneTarget.getListener()).getPose();
            return Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle;
        } catch(NullPointerException e) { System.out.println(e); }

        return 0.0;
    }

    /**
     * Get angle to SkyStone with a specific Z offset
     * @param zOffset the z offset to calculate the angle with
     * @return the angle to a point offset from the SkyStone by zOffset
     */
    public double getAngleOffset(double xOffset, double zOffset) {
        // Get X and Z coordinates relative to stone
        double stoneX = getX();
        double stoneZ = getZ();

        double newXCoordinate = stoneX - xOffset;
        double newZCoordinate = stoneZ - zOffset;

        return Math.atan2(newZCoordinate, newXCoordinate);
    }

    /**
     * Find the distance to drive to a stone with a specific z offset
     * @param zOffset the z-offset from the SkyStone
     * @return the distance to drive
     */
    public double getDistanceZOffset(double zOffset) {
        // Get X and Z coordinates relative to stone
        double stoneX = getX();
        double stoneZ = getZ();

        double newZcoordinate = stoneZ - zOffset;

        // Use the Pythagorean theorem to calculate the distance to drive
        return Math.hypot(stoneX, stoneZ);
    }

    /**
     * Parses the X axis offset out of the getDetected transform
     * 4242 is a set impossible number that is unlikely to occur, ignore if it is
     * @return the value of X as a double
     */
    public double getX(){
        /*
        String[] splitTransform = getDetectedTransform().split(" ");

        //If the transform string is greater than 0 return the normal X, if not return 4242 because that number is impossible to reach in this case and also 42..
        if(splitTransform.length > 0)
            return Double.parseDouble(splitTransform[6]);
        else
            return 4242;

         */
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)skystoneTarget.getListener()).getPose();

        if(pose != null) {
            return pose.getTranslation().get(0);
        } else {
            return 0.0;
        }
    }

    /**
     * Parses the Y axis offset out of the getDetected transform
     * 4242 is a set impossible number that is unlikely to occur, ignore if it is
     * @return the value of Y as an double
     */
    public double getY(){
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)skystoneTarget.getListener()).getPose();

        //If the transform string is greater than 0 return the normal X, if not return 4242 because that number is impossible to reach in this case and also 42..
        if(pose != null)
            return pose.getTranslation().get(1);
        else
            return 0.0;
    }

    /**
     * Parses the Z axis offset out of the getDetected transform
     * 4242 is a set impossible number that is unlikely to occur, ignore if it is
     * @return the value of Z as an double
     */
    public double getZ(){
        /*
        String[] splitTransform = getDetectedTransform().split(" ");

        //If the transform string is greater than 0 return the normal X, if not return 4242 because that number is impossible to reach in this case and also 42..
        if(splitTransform.length > 0)
            return Double.parseDouble(splitTransform[4]);
        else
            return 4242;

         */

        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)skystoneTarget.getListener()).getPose();

        if(pose != null) {
            return pose.getTranslation().get(2);
        } else {
            return 0.0;
        }
    }

    /**
     * returns the current position of the skystone out of the three possible positions
     * @return the Skystone position in terms of the SkystonePosition enum
     */
    public SkystonePostion getPosition(){

        //Gets the current x and z offsets from the center of the camera (left to right and front to back respectively)
        double stoneX = getX();
        double stoneY = getY();

        // Return position of the stone if it has been detected (y will never be 0)
        if (stoneY != 0.0) {
            if (stoneX < -100) {
                return SkystonePostion.LEFT;
            }
            else if (stoneX > 100) {
                return SkystonePostion.RIGHT;
            }
            else {
                return SkystonePostion.CENTER;
            }
        }
        else {
            return SkystonePostion.NONE;
        }
    }

    public String getPositionString() {
        switch (getPosition()) {
            case LEFT:
                return "Left";

            case CENTER:
                return "Center";

            case RIGHT:
                return "Right";

            case NONE:
                return "None";
        }

        return "Error";
    }

    /**
     * This method calculates the angle required to drive to the skystone
     * @return the angle
     */
    public double getAngleToSkystone(){
        return Math.toDegrees(Math.atan2(getZ(),getX()));
    }

    /**
     * Start Tracking
     */
    public void initVuforia(){
        trackables.activate();
    }

    /**
     * Stop Tracking
     */
    public  void stopVuforia(){
        trackables.deactivate();
    }
}

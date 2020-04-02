package org.firstinspires.ftc.teamcode.Test.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Utilities.Vuforia.SkystonePostion;
import org.firstinspires.ftc.teamcode.Utilities.Vuforia.VuforiaWrapper;

/**
 * This OpMode was used for testing if VuForia could calculate the
 * orientation of a SkyStone once detected
 * @author Zane Othman
 */

@Autonomous(name = "Vuforia Stone Orientation", group = "test")
@Disabled
public class VuforiaStoneOrientation extends OpMode {
    // Variables to store the position and orientation of the SkyStone
    private SkystonePostion position = SkystonePostion.NONE;
    private Orientation stoneOrientation;

    // Reference to Vuforia wrapper
    private VuforiaWrapper vuforia;

    // Variables for finding average X/Z values over time
    private double xAverage;
    private double zAverage;

    /**
     * Called when "INIT" button is pressed
     *
     * Initializes Vuforia wrapper with the hardwareMap (for the camera)
     */
    @Override
    public void init() {
        vuforia = new VuforiaWrapper(hardwareMap);

        xAverage = vuforia.getX();
        zAverage = vuforia.getZ();
    }

    /**
     * This function is called repeatedly when the "INIT" button is pressed
     *
     * Starts Vuforia instance/wrapper
     */
    @Override
    public void init_loop() {
        vuforia.initVuforia();
    }

    /**
     * Prints SkyStone position and orientation relative to robot
     */
    @Override
    public void loop() {
        double currentX = vuforia.getX();
        double currentZ = vuforia.getZ();

        // Get the orientation of the SkyStone
        // secondAngle in this case references the angle along the vertical axis
        //stoneOrientation = vuforia.getStoneOrientation();
        // telemetry.addData("Orientation: ", vuforia.getStoneAngle());

        telemetry.addData("Stone X: ", vuforia.getX());
        telemetry.addData("Stone Z: ", vuforia.getZ());

        zAverage = (currentZ + zAverage) / 2;

        telemetry.addData("Average Z: ", zAverage);

        telemetry.addData("Stone Position: ", vuforia.getPositionString());
    }

}

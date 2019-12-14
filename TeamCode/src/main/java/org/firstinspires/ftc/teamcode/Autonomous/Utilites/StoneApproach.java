package org.firstinspires.ftc.teamcode.Autonomous.Utilites;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Constants;
import org.firstinspires.ftc.teamcode.Utilities.Control.PID;
import org.firstinspires.ftc.teamcode.Utilities.Vuforia.VuforiaWrapper;
import org.firstinspires.ftc.teamcode.Utilities.Vuforia.SkystonePostion;

/**
 * A class to facilitate the approach of a SkyStone during the autonomous period.
 * @author Zane Othman-Gomez
 */
public class StoneApproach {
    private VuforiaWrapper vuforia;
    private SwerveController swerve;
    private HardwareMap hardwareMap;
    private PID drivePid;

    private final double leftArmOffset = -10;
    private final double rightArmOffset = 10;
    // TODO: Decide whether to use left or right arm if block is straight ahead
    // Set to right for now, might change later
    private final double centerArmOffset = 10;

    /**
     * A method to construct a StoneApproach instance
     * @param vuforia A reference to a VuforiaWrapper
     * @param swerve The SwerveController used in an OpMode
     * @param hardwareMap The hardware map of the robot
     */
    public StoneApproach(VuforiaWrapper vuforia, SwerveController swerve, HardwareMap hardwareMap) {
        this.vuforia = vuforia;
        this.swerve = swerve;
        this.hardwareMap = hardwareMap;

        // Initialize the PID instance inside this class
        // TODO: Tune the P scalar to actually work; 5 is just a placeholder value
        this.drivePid = new PID(Constants.DRIVE_P, Constants.DRIVE_I, Constants.DRIVE_D);

        drivePid.setSetpoint(0);
        drivePid.setMaxOutput(1);
        // TODO: Also tune this acceptable range; coordinate values are weird
        drivePid.setAcceptableRange(10);
    }

    // TODO: Add correction for angle offset
    /**
     * The method responsible for actually driving up to the SkyStone.
     * Note that this method assumes Vuforia can see a Skystone; if it can't, this might crash.
     * @param zOffset How far we want to be from the SkyStone in Vuforia coordinate units.
     * @param marginError The range you want to be within when approaching the Skystone
     */
    public boolean approachStone(double zOffset, double marginError) {
        // Get the x and z coordinates relative to the Skystone with Vuforia
        double xStoneDistance = vuforia.getX();
        double zStoneDistance = vuforia.getZ();

        // Variable to store original stone position
        SkystonePostion stonePos;

        // Define where the stone is relative to the robot and store that placement with the SkytonePosition enum
        // TODO: Measure and tune these values to be more accurate
        if(xStoneDistance < -50) {
            stonePos = SkystonePostion.LEFT;
        } else if(-50 < xStoneDistance && xStoneDistance < 50) {
            stonePos =  SkystonePostion.CENTER;
        } else if(xStoneDistance > 50) {
            stonePos =  SkystonePostion.RIGHT;
        } else {
            stonePos = SkystonePostion.NONE;
        }

        /*
        // Drive the modules using PID until you reach the SkyStone
        // TODO: This range is related to acceptable range in constructor; update accordingly
        while(!drivePid.isInRange()) {
            double distanceToStone = vuforia.getDistanceZOffset(zOffset);
            double speedCalculated = drivePid.calcOutput(distanceToStone);
            moduleAngle = vuforia.getAngleZOffset(zOffset);

            swerve.activeControl(moduleAngle, speedCalculated);

            xStoneDistance = vuforia.getX();
        }
        */

        // Initialize variable
        double distanceToStoneOffset;

        // TODO: Find x-offsets required to end up with left or right arm in front of the stone
        switch(stonePos) {
            /*
            case LEFT:
                while(xStoneDistance <= marginError + leftArmOffset && xStoneDistance >= marginError - leftArmOffset) {
                    // Update x-distance and regular distance to stone for while loop purposes
                    xStoneDistance = vuforia.getX();
                    distanceToStoneOffset = Math.hypot(xStoneDistance - this.leftArmOffset, zStoneDistance - zOffset);

                    // Calculated angle to stone
                    double approachModuleAngle = vuforia.getAngleOffset(this.leftArmOffset, zOffset);

                    // PID-calculated speed to get to stone
                    double calculatedSpeed = drivePid.calcOutput(distanceToStoneOffset);

                    // Run motors at correct angle and speed
                    swerve.activeControl(approachModuleAngle, calculatedSpeed);

                    xStoneDistance = vuforia.getX();
                }

                break;

             */

            default:
            case LEFT: // This should be removed when the left arm starts working
            case CENTER:
            case RIGHT:
                // FIXME: Consider refactoring to separate function
                // FIXME: This while loop also has 4 conditions, so those could probably be split into different functions like isInRangeX/Z or a single function below
                while(isInRange(10, 10)) {
                    // Update x-distance and regular distance to stone for while loop purposes
                    xStoneDistance = vuforia.getX();
                    distanceToStoneOffset = Math.hypot(xStoneDistance - this.rightArmOffset, zStoneDistance - zOffset);

                    // Calculated angle to stone
                    double approachModuleAngle = vuforia.getAngleOffset(this.rightArmOffset, zOffset);

                    // PID-calculated speed to get to stone
                    double calculatedSpeed = drivePid.calcOutput(distanceToStoneOffset);

                    // Run motors at correct angle and speed
                    swerve.activeControl(approachModuleAngle, calculatedSpeed);
                }

                swerve.stopModules();

                return true;

            // FIXME: Add NONE case so this doesn't do weird things when no Skystone is detected
        }

        // TODO: Orient robot correctly relative to stone to correct, if necessary

    }

    /**
     * A function to check if we are close enough to a Skystone to pick it up.
     * @param xRange How accurate we want to be horizontally in terms of Vuforia coordinates.
     * @param zRange How accurate we want to be forward/backward relative to the Skystone.
     * @return Whether we are in range or not, where true.
     */
    private boolean isInRange(double xRange, double zRange) {
        double xDistance = vuforia.getX();
        double zDistance = vuforia.getZ();

        return xDistance <= xRange && xDistance >= -xRange && zDistance <= zRange && zDistance >= -zRange;
    }
}

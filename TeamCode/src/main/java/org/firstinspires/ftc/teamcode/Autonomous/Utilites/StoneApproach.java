package org.firstinspires.ftc.teamcode.Autonomous.Utilites;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Control.PID;
import org.firstinspires.ftc.teamcode.Utilities.Vuforia.VuforiaWrapper;

/**
 * A class to facilitate the approach of a SkyStone during the autonomous period
 * @author Zane Othman-Gomez
 */
public class StoneApproach {
    private VuforiaWrapper vuforia;
    private SwerveController swerve;
    private HardwareMap hardwareMap;
    private PID drivePid;

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
        this.drivePid = new PID(5, 0, 0);

        drivePid.setSetpoint(0);
        drivePid.setMaxOutput(1);
        // TODO: Also tune this acceptable range; coordinate values are weird
        drivePid.setAcceptableRange(10);
    }

    /**
     * A method to rotate the modules in preparation for driving to the stone
     * @param zOffset How far to aim away from the stone in Vuforia coordinate units
     * @param moduleAngle The angle to rotate the modules to
     */
    private void rotateModules(double zOffset, double moduleAngle) {
        // While loop to continue adjusting the angle until it is aligned properly
        boolean angleReached = false;

        while(angleReached) {
            angleReached = swerve.controlModules(moduleAngle);
        }
    }

    // TODO: Add correction for angle offset
    /**
     * The method responsible for actually driving up to the SkyStone.
     * @param zOffset How far we want to be from the SkyStone in Vuforia coordinate units.
     */
    public void approachStone(double zOffset) {
        double moduleAngle = vuforia.getAngleZOffset(zOffset);

        // Calculate the x-distance to the SkyStone for PID purposes
        double xStoneDistance = vuforia.getX();

        // Drive the modules using PID until you reach the SkyStone
        // TODO: This range is related to acceptable range in constructor; update accordingly
        while(xStoneDistance >= 10 && xStoneDistance <= -10) {
            double distanceToStone = vuforia.getDistanceZOffset(zOffset);
            double scaleFactor = drivePid.calcOutput(distanceToStone);

            swerve.controlModulesScaled(moduleAngle, scaleFactor);

            xStoneDistance = vuforia.getX();
        }

        swerve.stopModules();
    }
}

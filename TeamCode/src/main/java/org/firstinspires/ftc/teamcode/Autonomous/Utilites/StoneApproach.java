package org.firstinspires.ftc.teamcode.Autonomous.Utilites;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Control.PID;
import org.firstinspires.ftc.teamcode.Utilities.Vuforia.VuforiaWrapper;
import org.firstinspires.ftc.teamcode.Autonomous.Utilites.StonePosition;

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

    // TODO: Add correction for angle offset
    /**
     * The method responsible for actually driving up to the SkyStone.
     * @param zOffset How far we want to be from the SkyStone in Vuforia coordinate units.
     */
    public void approachStone(double zOffset) {
        // Calculate the x-distance to the SkyStone for PID purposes
        double xStoneDistance = vuforia.getX();

        // Variable to store original stone position
        StonePosition stonePos;

        // Define where the stone is relative to the robot and store that placement with the StonePosition enum
        // TODO: Measure and tune these values to be more accurate
        if(xStoneDistance < -50) {
            stonePos = StonePosition.LEFT;
        } else if(-50 < xStoneDistance && xStoneDistance < 50) {
            stonePos = StonePosition.STRAIGHT;
        } else {
            stonePos = StonePosition.RIGHT;
        }

        double moduleAngle = vuforia.getAngleZOffset(zOffset);

        /*
        // Drive the modules using PID until you reach the SkyStone
        // TODO: This range is related to acceptable range in constructor; update accordingly
        while(xStoneDistance >= 10 && xStoneDistance <= -10) {
            double distanceToStone = vuforia.getDistanceZOffset(zOffset);
            double speedCalculated = drivePid.calcOutput(distanceToStone);
            moduleAngle = vuforia.getAngleZOffset(zOffset);

            swerve.activeControl(moduleAngle, speedCalculated);

            xStoneDistance = vuforia.getX();
        }
        */

        // TODO: Find x-offsets required to end up with left or right arm in front of the stone
        switch(stonePos) {
            case LEFT:
                break;
            case RIGHT:
                break;
            case STRAIGHT:
                break;
        }

        swerve.stopModules();
    }
}

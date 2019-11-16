package org.firstinspires.ftc.teamcode.Test.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Utilities.Control.PID;
import org.firstinspires.ftc.teamcode.Utilities.Vuforia.VuforiaWrapper;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;

@Autonomous(name = "Drive to Skystone Test", group = "Test")
@Disabled
public class DriveToStoneTest extends OpMode {
    private VuforiaWrapper vuforia;
    private SwerveController swerveController;

    // Initialize PID controller for movement
    private PID drivePid;

    // Z Offset to be relative to the stone
    private double zOffset = 10;

    private double driveAngle;
    private boolean hasRun = false;

    @Override
    public void init() {
        // Initialize swerve modules
        swerveController = new SwerveController(hardwareMap);

        // TODO: Swerve motors must also be grabbed from hardwareMap for driving

        // Initialize the PID control class
        // TODO: Tune the P scalar for driving, as 5 was just a random number
        drivePid = new PID(5, 0, 0);

        // TODO: This also has to be calibrated, as 1 coordinate unit is not 1 inch, etc.
        drivePid.setAcceptableRange(10);

        // Set target x-offset to be 0
        drivePid.setSetpoint(0);

        // Set max output to be 1 (maximum motor power)
        drivePid.setMaxOutput(1);

        // Calculate the angle to drive at in order to get in front of the SkyStone
        // TODO: the z-offset must also be tuned based on the robot
        driveAngle = vuforia.getAngleZOffset(zOffset);
    }

    @Override
    public void init_loop() {
        vuforia.initVuforia();
    }

    @Override
    public void loop() {
        if(!hasRun) {
            // Set angle of swerve modules
            swerveController.controlModulesScaled(driveAngle, 1);

            boolean reachedTarget = false;

            double initialOutput = vuforia.getDistanceZOffset(zOffset);
            double currentX;

            // Loop until the loop is broken (the robot is within 10 units of the target)
            while (true) {
                currentX = vuforia.getX();
                if(currentX < 10 && currentX > -10) {
                    break;
                }

                double power = drivePid.calcOutput(currentX);

                // Set motor powers based on above power (one positive, one negative)
                // Also see if WheelDirection could be integrated into this (forward, backward, etc)
            }

            // Set motor powers to 0

            // Stop the op mode
            hasRun = true;
        }
        else{
            swerveController.stopModules();
        }
    }
}

package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.StoneGripController;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.GripArmPosition;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.MoveArmDirection;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

@Autonomous

public class FoundationAuto extends OpMode {
    private SwerveController swerve;
    private StoneGripController stoneArms;
    private Servo LeftBlockGrip;
    private Servo RightBlockGrip;

    // This list is used for progression in actions executed.
    private boolean hasRun = false;

    @Override
    public void init() {
        // Initialize the swerve module controller
        swerve = new SwerveController(null, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);

        RightBlockGrip = hardwareMap.get(Servo.class, "svGrip");
        LeftBlockGrip = hardwareMap.get(Servo.class, "LeftBlockGrip");

        // Initialize the controller for the stone-grabbing arms
        stoneArms = new StoneGripController(hardwareMap, null, telemetry);
    }

    @Override
    public void loop() {
        if (!hasRun) {
            boolean swerveFinishedMoving1 = false;

            while (!swerveFinishedMoving1) {
                swerveFinishedMoving1 = swerve.autoControlModules(0, 48, .8);
            }

            stoneArms.autoPivot(GripArmPosition.LEFT, MoveArmDirection.DOWN, 1000);
            stoneArms.autoPivot(GripArmPosition.RIGHT, MoveArmDirection.DOWN, 1000);

            boolean swerveFinishedMoving2 = false;

            while (!swerveFinishedMoving2) {
                swerveFinishedMoving2 = swerve.autoControlModules(180, 48, .8);
            }

            // TODO: This might work, but it hasn't actually been tested yet. Be sure to do that before we compete.
            hasRun = true;
        } else {
            requestOpModeStop();
        }
    }
}


package org.firstinspires.ftc.teamcode.Autonomous.OpModes.BlueAuto;


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

public class BlueMiddleStone extends OpMode {
    private SwerveController swerve;
    private StoneGripController stoneArms;
    private Servo LeftBlockGrip;
    private Servo RightBlockGrip;

    // This list is used for progression in actions executed.
    private boolean[] actionCompletions = {false, false, false, false, false, false, false};

    @Override
    public void init() {
        // Initialize the swerve module controller
        swerve = new SwerveController(null, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);

        RightBlockGrip = hardwareMap.get(Servo.class, "svGrip");
        LeftBlockGrip = hardwareMap.get(Servo.class, "svLeftGrip");

        // Initialize the controller for the stone-grabbing arms
        stoneArms = new StoneGripController(hardwareMap, null, telemetry);
    }

    @Override
    public void loop() {
        // Drive up to the stone.
        if (!actionCompletions[0]) {
            actionCompletions[0] = swerve.autoControlModules(0, 26, 0.5);
        }

        // Pick up the middle stone using the right arm
        else if (!actionCompletions[1]) {
            stoneArms.ungripArm(GripArmPosition.RIGHT);
            stoneArms.autoPivot(GripArmPosition.RIGHT, MoveArmDirection.DOWN, 1000);
            stoneArms.gripArm(GripArmPosition.RIGHT);
            stoneArms.autoPivot(GripArmPosition.RIGHT, MoveArmDirection.UP, 1000);

            actionCompletions[1] = true;
        }

        // Drive sideways to the foundation
        else if (!actionCompletions[2]) {
            actionCompletions[2] = swerve.autoControlModules(90, 56, 0.5);
        }

        // Lower arms, release stone and grab foundation
        else if (!actionCompletions[3]) {
            // Drop stone and left arm
            stoneArms.autoPivot(GripArmPosition.RIGHT, MoveArmDirection.DOWN, 1000);
            stoneArms.ungripArm(GripArmPosition.RIGHT);

            // Drop left arm
            stoneArms.autoPivot(GripArmPosition.LEFT, MoveArmDirection.DOWN, 1000);

            actionCompletions[3] = true;
        }

        // Back up and put foundation in building site
        else if (!actionCompletions[4]) {
            actionCompletions[4] = swerve.autoControlModules(180, 14, 0.5);
        }

        // Raise arms so we don't take the foundation with us
        else if (!actionCompletions[5]) {
            stoneArms.autoPivot(GripArmPosition.RIGHT, MoveArmDirection.UP, 1000);
            stoneArms.autoPivot(GripArmPosition.LEFT, MoveArmDirection.UP, 1000);
            actionCompletions[5] = true;
        }

        // Drive under the bridge
        else if (!actionCompletions[6]) {

        }

        // Stop the op mode
        else {
            requestOpModeStop();
        }
    }
}


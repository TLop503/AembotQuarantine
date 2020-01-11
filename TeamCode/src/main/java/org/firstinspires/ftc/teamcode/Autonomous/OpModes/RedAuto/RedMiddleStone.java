package org.firstinspires.ftc.teamcode.Autonomous.OpModes.RedAuto;

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



public class RedMiddleStone extends OpMode {
    private SwerveController swerve;
    private StoneGripController stoneArms;
    private Servo LeftBlockGrip;
    private Servo RightBlockGrip;

    private boolean ran1 = false;
    private boolean ran2 = false;
    private boolean ran3 = false;
    private boolean ran4 = false;
    private boolean ran5 = false;
    private boolean ran6 = false;
    private boolean ran7 = false;

    // This list is used for progression in actions executed.
    private boolean[] actionCompletions = {false, false, false, false, false, false, false};

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
        // Drive up to the stone.
        while (ran1 != true) {
            swerve.autoControlModules(0, 24, 0.25);
            ran1 = true;
        }

        // Pick up the middle stone using the right arm
        while (ran2 != true) {
            stoneArms.ungripArm(GripArmPosition.LEFT);
            stoneArms.autoPivot(GripArmPosition.LEFT, MoveArmDirection.DOWN, 1000);
            stoneArms.gripArm(GripArmPosition.LEFT);
            stoneArms.autoPivot(GripArmPosition.LEFT, MoveArmDirection.UP, 1000);

            ran2 = true;
        }

        // Drive sideways to the foundation
        while (ran3 != true) {
            swerve.autoControlModules(90, 56, 0.5);
            ran3 = true;
        }

        // Lower arms, release stone and grab foundation
        while (ran4 != true) {
            // Drop stone and left arm
            stoneArms.autoPivot(GripArmPosition.LEFT, MoveArmDirection.DOWN, 1000);
            stoneArms.ungripArm(GripArmPosition.LEFT);

            // Drop left arm
            stoneArms.autoPivot(GripArmPosition.RIGHT, MoveArmDirection.DOWN, 1000);

            ran4 = true;
        }

        // Back up and put foundation in building site
        while (ran5 != true) {
            ran5 = true;
        }

        // Raise arms so we don't take the foundation with us
        while (ran6 != true) {
            stoneArms.autoPivot(GripArmPosition.RIGHT, MoveArmDirection.UP, 1000);
            stoneArms.autoPivot(GripArmPosition.LEFT, MoveArmDirection.UP, 1000);
            ran6 = true;
        }

        // Drive under the bridge
        while (ran7 != true) {
            swerve.autoControlModules(270, 40, 0.5);
            ran7 = true;
        }

        // Stop the op mode

        requestOpModeStop();
    }
}


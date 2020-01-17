package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.StoneGripController;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.GripArmPosition;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.MoveArmDirection;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

@Autonomous(name = "Foundation - Linear", group = "Autonomous")
public class FoundationAutoLinear extends LinearOpMode {
    private SwerveController swerve;
    private StoneGripController stoneArms;

    @Override
    public void runOpMode() throws InterruptedException {
        swerve = new SwerveController(null, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);
        stoneArms = new StoneGripController(hardwareMap, null, telemetry);

        waitForStart();

        // Drive to foundation
        boolean drivenToFoundation = false;
        while (!drivenToFoundation) {
            drivenToFoundation = swerve.autoControlModules(0, 30, 0.5);
        }

        stoneArms.autoPivot(GripArmPosition.LEFT, MoveArmDirection.DOWN, 1000);
        stoneArms.autoPivot(GripArmPosition.RIGHT, MoveArmDirection.DOWN, 1000);

        boolean pulledFoundationBack = false;
        while (!pulledFoundationBack) {
            pulledFoundationBack = swerve.autoControlModules(180, 48, .8);
        }
    }
}

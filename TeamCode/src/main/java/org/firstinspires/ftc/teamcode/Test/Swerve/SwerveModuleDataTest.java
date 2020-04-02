package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.Swerve.Enums.ModulePosition;
import org.firstinspires.ftc.teamcode.Swerve.SwerveModule;

@Autonomous(name="Right Swerve Module Data Test", group="Test")
//@Disabled
public class SwerveModuleDataTest extends OpMode {

    public SwerveModule rightModule;

    @Override
    public void init() {
        rightModule = new SwerveModule(ModulePosition.RIGHT , hardwareMap, gamepad1, telemetry);
        rightModule.resetBottomEncoder();
        rightModule.resetTopEncoder();

    }

    @Override
    public void loop() {

        rightModule.autoPIDControl(0, 50, .5);

    }
}

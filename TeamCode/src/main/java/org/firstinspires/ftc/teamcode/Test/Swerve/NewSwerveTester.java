package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Class used for re-testing swerve values
 */
@Autonomous(name = "New Swerve Test", group = "Test")
@Disabled
public class NewSwerveTester extends OpMode {

    DcMotor TopSwerveMotor;
    DcMotor BottomSwerveMotor;

    @Override
    public void init() {
        TopSwerveMotor = hardwareMap.get(DcMotor.class, "TopSwerveMotor");
        BottomSwerveMotor = hardwareMap.get(DcMotor.class, "BottomSwerveMotor");

        TopSwerveMotor.setTargetPosition(0);
        BottomSwerveMotor.setTargetPosition(-4318);

        TopSwerveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BottomSwerveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        TopSwerveMotor.setPower(0.5);
        BottomSwerveMotor.setPower(0.5);

        telemetry.addData("Top Motor Encoder: ", TopSwerveMotor.getCurrentPosition());
    }
}

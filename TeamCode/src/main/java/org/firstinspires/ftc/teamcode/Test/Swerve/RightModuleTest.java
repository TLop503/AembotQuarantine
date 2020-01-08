package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Right Module Test", group="Test")
@Disabled
public class RightModuleTest extends OpMode {
    private DcMotor dcSwerveTop;
    private DcMotor dcSwerveBottom;

    @Override
    public void init() {
        dcSwerveTop = hardwareMap.dcMotor.get("RightTopSwerveMotor");
        dcSwerveBottom = hardwareMap.dcMotor.get("RightBottomSwerveMotor");
    }

    @Override
    public void loop() {
        dcSwerveTop.setPower(gamepad1.right_stick_y);
        dcSwerveBottom.setPower(gamepad1.left_stick_y);
    }
}

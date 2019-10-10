package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Use this method to test any motors you may need
 */
@TeleOp(name = "Swerve Motor Test", group = "Test")
@Disabled
public class SwerveMotorTest extends OpMode {

    private DcMotor TopSwerveMotor;
    private DcMotor BottomSwerveMotor;

    @Override
    public void init() {
        TopSwerveMotor = hardwareMap.get(DcMotor.class, "TopSwerveMotor");
        BottomSwerveMotor = hardwareMap.get(DcMotor.class, "BottomSwerveMotor");
    }

    @Override
    public void loop() {

        TopSwerveMotor.setPower(100);
        BottomSwerveMotor.setPower(100);

    }
}

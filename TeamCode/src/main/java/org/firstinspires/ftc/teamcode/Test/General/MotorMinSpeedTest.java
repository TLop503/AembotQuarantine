package org.firstinspires.ftc.teamcode.Test.General;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Min Speed Test", group = "Test")
public class MotorMinSpeedTest extends OpMode {

    DcMotor RightTopSwerve;
    DcMotor RightBottomSwerve;

    double motorSpeed = 0;

    @Override
    public void init() {
        RightTopSwerve = hardwareMap.get(DcMotor.class, "RightTopSwerveMotor");
        RightBottomSwerve = hardwareMap.get(DcMotor.class, "RightBottomSwerveMotor");

        RightTopSwerve.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightBottomSwerve.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {

        motorSpeed = gamepad1.left_stick_y;

        RightTopSwerve.setPower(motorSpeed);
        RightBottomSwerve.setPower(motorSpeed);

        telemetry.addData("Motor Speed: ", motorSpeed);


    }
}

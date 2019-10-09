package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Swerve.SwerveMath;

/**
 * Class used to test calculated joystick angle
 * 10/8 TODO: Test Tmrw. If broken restore previous commit
 * @author Will Richards
 */

@TeleOp(name = "Joystick Test", group = "Test")
public class JoystickAngleTest extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        telemetry.addData("Wanted Module Angle: ", SwerveMath.normalizeJoystickAngle(gamepad1));
        telemetry.addData("X,Y:", gamepad1.left_stick_x + "," + gamepad1.left_stick_y*-1);
    }
}

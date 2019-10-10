package org.firstinspires.ftc.teamcode.Test.Misc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Swerve.SwerveMath;

/**
 * Class used to test calculated joystick angle and print out to the driver station
 * 10/8 TODO: Test Tmrw. If broken restore previous commit
 * @author Will Richards
 */

@TeleOp(name = "Joystick Test", group = "Test")
public class JoystickAngleTest extends OpMode {

    @Override
    public void init() {

    }

    /**
     * Loops every 20ms
     * This loop is used for testing the joystick angles thus it simply references the static SwerveMath class and calculates, module angle and wheel direction
     */
    @Override
    public void loop() {
        telemetry.addData("Wanted Module Angle: ", SwerveMath.normalizeJoystickAngle(gamepad1));
        telemetry.addData("Wheel Direction: ", SwerveMath.getWheelDirection(gamepad1).toString());
    }
}

package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Class used to test calculated joystick angle
 * @author Will Richards
 */

@TeleOp(name = "Joystick Test", group = "Test")
public class JoystickAngleTest extends OpMode {

    private String wheelDirection = "Static";

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        //ATAN2(X,Y)
        double trueAngle = Math.toDegrees(Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x));
        double normalizedAngle = calcAngle(trueAngle);

        telemetry.addData("Calculated Angle: ", normalizedAngle);
        telemetry.addData("Wheel Direction: ", wheelDirection);
    }

    /**
     * Normalizes joystick angle to top half
     * @param trueAngle the true joystick
     * @return the normalized angle
     */
    private double calcAngle(double trueAngle){
        if(gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0 ){
            wheelDirection = "Static";
            return 0;
        }
        if(trueAngle > 0 && trueAngle < 180){
            wheelDirection = "Backwards";
            return trueAngle - 180;
        }
        else{
            wheelDirection = "Forward";
            return trueAngle;
        }
    }
}

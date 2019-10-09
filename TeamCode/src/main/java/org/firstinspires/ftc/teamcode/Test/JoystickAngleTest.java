package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Class used to test calculated joystick angle
 * @author Will Richards
 */

@TeleOp(name = "Joystick Test", group = "Test")
public class JoystickAngleTest extends OpMode {


    @Override
    public void init() {

    }

    @Override
    public void loop() {
        //ATAN2(X,Y)
        double trueAngle = Math.toDegrees(Math.atan2(gamepad1.left_stick_y*-1, gamepad1.left_stick_x));
        double normalizedAngle = calcAngle(gamepad1.left_stick_x, gamepad1.left_stick_y*-1, trueAngle);

        //telemetry.addData("True Angle: ", trueAngle);
        telemetry.addData("Normalized Angle: ", normalizedAngle);
        telemetry.addData("X,Y:", gamepad1.left_stick_x + "," + gamepad1.left_stick_y*-1);
    }

    /**
     * Normalizes joystick angle to top half
     * @param x joystick X axis
     * @param y joystick Y axis
     * @param trueAngle the true joystick
     * @return the normalized angle
     */
    private double calcAngle(double x, double y, double trueAngle){
        if(x==0 && y==0)
            return 0;
        else if(trueAngle>=0 && trueAngle<=180)
            return 90-trueAngle;
        else if(x<=0&&y==0)
            return -90;
        else if(y<=0&&x<=0)
            return 90-(trueAngle+180);
        else if(y<=0&&x>=0)
            return  (-90)-trueAngle;
        else {
            return trueAngle;
        }
    }
}

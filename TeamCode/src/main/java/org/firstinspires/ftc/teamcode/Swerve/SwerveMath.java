package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created to house any math involved in controlling the diff. swerve
 * @author Will Richards
 */
public class SwerveMath {
    /**
     * Static method used to normalize the joystick
     * Y is inverted because for some strange reason someone decided that up on the joystick should be negative
     * @param gamepad1 the primary driving controller
     * @return the angle the module should be facing in terms of the top half of the joystick
     */
    public static double normalizeJoystickAngle(Gamepad gamepad1){
        double trueAngle = Math.toDegrees(Math.atan2(gamepad1.left_stick_y*-1, gamepad1.left_stick_x));
        if(gamepad1.left_stick_x==0 && gamepad1.left_stick_y*-1==0)
            return 0;
        else if(trueAngle>=0 && trueAngle<=180)
            return 90-trueAngle;
        else if(gamepad1.left_stick_x<=0&&gamepad1.left_stick_y*-1==0)
            return -90;
        else if(gamepad1.left_stick_y*-1<=0&&gamepad1.left_stick_x<=0)
            return 90-(trueAngle+180);
        else if(gamepad1.left_stick_y*-1<=0&&gamepad1.left_stick_x>=0)
            return  (-90)-trueAngle;
        else {
            return trueAngle;
        }
    }
}

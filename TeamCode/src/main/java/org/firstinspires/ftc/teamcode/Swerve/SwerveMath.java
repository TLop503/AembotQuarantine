package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Swerve.Enums.WheelDirection;

/**
 * Created to house any math involved in controlling the diff. swerve
 * @author Will Richards
 */
public class SwerveMath {

    /**
     * Static method used to normalize the joystick
     * Y is inverted because for some strange reason someone decided that up on the joystick should be negative
     * @param gamepad1 the primary driving controller
     * @return the angle in terms of module rotations
     */
    public static double normalizeJoystickAngle(Gamepad gamepad1){

        double alteredAngle = 0;
        double trueAngle = Math.toDegrees(Math.atan2(gamepad1.left_stick_y*-1, gamepad1.left_stick_x));
        if(gamepad1.left_stick_x==0 && gamepad1.left_stick_y*-1==0)
            alteredAngle = 0;
        else if(trueAngle>=0 && trueAngle<=180)
            alteredAngle = 90-trueAngle;
        else if(gamepad1.left_stick_x<=0&&gamepad1.left_stick_y*-1==0)
            alteredAngle = -90;
        else if(gamepad1.left_stick_y*-1<=0&&gamepad1.left_stick_x<=0)
            alteredAngle = 90-(trueAngle+180);
        else if(gamepad1.left_stick_y*-1<=0&&gamepad1.left_stick_x>=0)
            alteredAngle =  (-90)-trueAngle;
        else {
            alteredAngle = trueAngle;
        }

        return alteredAngle/360;
    }


    /**
     * Returns the direction the wheel should be running based on the current Y axis, not really math but whatever
     * @param gamepad1 ref. to the primary gamepad
     * @return the direction the wheel should run
     */
    public static WheelDirection getWheelDirection(Gamepad gamepad1){
        if(gamepad1.left_stick_y*-1 == 0){
            return WheelDirection.STATIC;
        }
        else if (gamepad1.left_stick_y*-1 < 0){
            return WheelDirection.BACKWARD;
        }
        else{
            return WheelDirection.FORWARD;
        }
    }

    /**
     * Calculates the current module rotation from the current tick count
     * @param topMotorTicks number of total ticks on top
     * @param bottomMotorTicks number of total ticks on bottom
     * @return the position in % of a rotation
     */
    public static double getModulePosition(int topMotorTicks, int bottomMotorTicks){
        return (((double)topMotorTicks+(double)bottomMotorTicks)/2250);
    }

}

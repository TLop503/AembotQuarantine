package org.firstinspires.ftc.teamcode.NormalSwerve;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Swerve.Enums.WheelDirection;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.IMU;

public class NormalSwerveMath {

    public static final double WHEEL_DIAMETER = 4; //inches
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER*Math.PI;

    public static double normalizeAngle(double angle){
        return angle/360;
    }

    public static double getWheelPosition(int topMotorTicks){
        return (topMotorTicks)/2240;
    }

    public static double calculateWheelPosition(double distance){
        return (distance / WHEEL_CIRCUMFERENCE);
    }

}

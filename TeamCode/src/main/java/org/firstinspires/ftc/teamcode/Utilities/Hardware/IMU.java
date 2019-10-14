package org.firstinspires.ftc.teamcode.Utilities.Hardware;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Class used as a general wrapper for the REV Robotics IMU
 * @author Will Richards
 */
public class IMU {

    private HardwareMap hardwareMap;

    private BNO055IMU imu;

    private Orientation angleOffset;

    /**
     * Initializes the IMU
     * @param hardwareMap a reference to the hardware map
     */
    public IMU(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;

        //Setup the IMU
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        //Create a new parameter set that allows the IMU to track angle and acceleration
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        angleOffset = new Orientation();

        //Init the IMU with the created parameter set
        imu.initialize(parameters);
    }


    /**
     * Get the current robot heading
     * @return the robot angle relative to 0
     */
    public double getHeading(){
        return (imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES)).firstAngle - angleOffset.firstAngle;
    }

    /**
     * Set the angle offset to the current angle
     */
    public void reset(){
        angleOffset = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
    }


}

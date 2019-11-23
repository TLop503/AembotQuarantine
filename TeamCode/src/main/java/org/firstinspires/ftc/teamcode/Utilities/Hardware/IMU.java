package org.firstinspires.ftc.teamcode.Utilities.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

/**
 * Class used as a general wrapper for the REV Robotics Control Hub IMU
 * @author Will Richards
 */
public class IMU {

    //Reference to the hardwareMap
    private HardwareMap hardwareMap;

    //Reference to the IMU
    private BNO055IMU imu;

    //Orientation to hold angle assigned that will be the offset
    public Orientation angleOffset;

    IMUOrientation orientation;

    /**
     * Initializes the IMU
     * @param hardwareMap a reference to the hardware map
     * @param orientation used to set the orientation of the hub so that proper heading values can be gotton
     */
    public IMU(HardwareMap hardwareMap, IMUOrientation orientation){
        this.hardwareMap = hardwareMap;

        this.orientation = orientation;

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

        angleOffset = (imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES));
    }


    /**
     * TODO: Check and see if the correct axis is being used on the gyro
     * Get the current robot heading
     * Take into account the orientation of the rev hub
     * @return the robot angle relative to 0
     */
    public double getHeading(){
        if(orientation.equals(IMUOrientation.HORIZONTAL))
            return (imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES)).firstAngle - angleOffset.firstAngle;
        else
            // This originally used the thirdAngle for the offset
            return (imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES)).secondAngle - angleOffset.secondAngle;
    }

    public boolean getIsGyroCalibrated(){
        return  imu.isGyroCalibrated();
    }

    /**
     * Set the angle offset to the current angle
     */
    public void reset(){
        angleOffset = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

    }


}

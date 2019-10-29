package org.firstinspires.ftc.teamcode.Test.General;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Utilities.Hardware.IMU;

@Autonomous(name = "TestGyro", group = "Test")
@Disabled
public class GyroTest extends OpMode {

    private IMU imu;

    @Override
    public void init() {
        imu = new IMU(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("Heading: ", imu.getHeading());
        telemetry.addData("Offset: ", imu.angleOffset.firstAngle);
    }
}

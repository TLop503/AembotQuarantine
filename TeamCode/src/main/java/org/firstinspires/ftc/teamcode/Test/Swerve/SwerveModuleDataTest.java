package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.robotcore.external.android.AndroidAccelerometer;
import org.firstinspires.ftc.teamcode.Swerve.Enums.ModulePosition;
import org.firstinspires.ftc.teamcode.Swerve.SwerveModule;

@Autonomous(name="Right Swerve Module Data Test", group="Test")
//@Disabled
public class SwerveModuleDataTest extends OpMode {


    public SwerveModule rightModule;
    public AndroidAccelerometer accelerometer;

    @Override
    public void init() {

        //rightModule = new SwerveModule(ModulePosition.RIGHT , hardwareMap, gamepad1, telemetry);
        //rightModule.resetBottomEncoder();
        //rightModule.resetTopEncoder();
        accelerometer = new AndroidAccelerometer();
    }

    @Override
    public void loop() {

        //rightModule.autoPIDControl(0, 20, 1);
        accelerometer.startListening();
        telemetry.addData("Distance unit : ", accelerometer.getDistanceUnit());
        telemetry.addData("X: ", accelerometer.getX());
        telemetry.addData("Y: ", accelerometer.getY());
        telemetry.addData("Z: ", accelerometer.getZ());
        telemetry.addData("Acceleration: ", accelerometer.getAcceleration());
        telemetry.update();
        accelerometer.stopListening();
    }
}

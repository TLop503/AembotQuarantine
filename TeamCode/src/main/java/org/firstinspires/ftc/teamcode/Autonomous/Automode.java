
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;


@Autonomous(name = "Auto Mode", group = "Autonomous")

public class Automode extends OpMode {


    private DcMotor dcDriveL;
    private DcMotor dcDriveR;

    @Override
    public void init() {
        dcDriveL = hardwareMap.get(DcMotor.class, "dcDriveL");
        dcDriveR = hardwareMap.get(DcMotor.class, "dcDriveR");


    }

    @Override
    public void loop() {
        driveInches(27, .8);

    }

    private void driveInches(double distance, double motorspeed){

        double ticks = 288 * (distance / 12.566);

        dcDriveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcDriveL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcDriveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcDriveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcDriveL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        dcDriveR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        dcDriveR.setTargetPosition((int)ticks);
        dcDriveL.setTargetPosition((int)ticks);

        dcDriveR.setPower(-motorspeed);
        dcDriveL.setPower(motorspeed);


    }
}
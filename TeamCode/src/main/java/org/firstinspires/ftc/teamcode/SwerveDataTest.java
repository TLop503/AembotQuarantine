package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Swerve Data Test", group = "test")
public class SwerveDataTest extends LinearOpMode {

    DcMotor mainMotor;

    public void runOpMode(){
        mainMotor = hardwareMap.get(DcMotor.class, "mainMotor");

        double motorSpeed = 0.5;

        waitForStart();


        driveInches(24, motorSpeed);

    }
    public void driveInches(double distance, double motorSpeed){


        double totalDistance = (2240 / 12.566) * distance;


        mainMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        mainMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        mainMotor.setTargetPosition((int)totalDistance);

        mainMotor.setPower(motorSpeed);


        while (opModeIsActive() && mainMotor.isBusy()) {
            idle();
        }

        mainMotor.setPower(0);
    }
}
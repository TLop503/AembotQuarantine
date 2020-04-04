package org.firstinspires.ftc.teamcode.NormalSwerve;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "Run Swerve Forward", group = "test")
public class RunSwerveForward extends LinearOpMode {

    DcMotor mainMotor;
    CRServo mainServo;

    public void runOpMode() {
        mainMotor = hardwareMap.get(DcMotor.class, "mainMotor");

        mainServo = hardwareMap.get(CRServo.class, "mainServo");
        double motorSpeed = 0.5;

        waitForStart();

        driveInches(24, motorSpeed);
    }

    public void driveInches(double distance, double motorSpeed) {


        double totalDistance = (2240 / 12.566) * distance;


        mainMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        mainMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        mainMotor.setTargetPosition((int) totalDistance);

        mainMotor.setPower(motorSpeed);


        while (opModeIsActive() && mainMotor.isBusy()) {
            idle();
        }

        mainMotor.setPower(0);
    }
}
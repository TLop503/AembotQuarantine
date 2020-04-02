package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Motor Feedforward Test", group = "test")
@Disabled
public class MotorFeedForwardTest extends LinearOpMode {

    private DcMotorEx motor;

    private double currentVelocity;

    private double maxVelocity = 0.0;


    @Override

    public void runOpMode() {

        motor = hardwareMap.get(DcMotorEx.class, "LeftTopSwerveMotor");

        waitForStart();


        while (opModeIsActive()) {
            motor.setPower(1);
            currentVelocity = motor.getVelocity();



            if (currentVelocity > maxVelocity) {

                maxVelocity = currentVelocity;

            }



            telemetry.addData("current velocity", currentVelocity);

            telemetry.addData("maximum velocity", maxVelocity);

            telemetry.update();

        }

    }


}

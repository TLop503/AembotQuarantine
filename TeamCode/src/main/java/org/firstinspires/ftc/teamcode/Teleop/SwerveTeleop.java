package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Swerve.Enums.WheelDirection;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Swerve.SwerveMath;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

/**
 * Class created to test driving the swerve module to a wanted angle, using a jopystick
 * @author Will Richards, Troy Lopez
 */



@TeleOp(name = "SwerveTeleop", group = "Teleop")
public class SwerveTeleop extends OpMode {

    //Creates a new swerve controller
    private SwerveController swerveController;

    private Servo svTop;
    private Servo svBottom;

    private DcMotor dcElevator;

    @Override
    public void init() {

        //Initialize the swerve controller
        swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.HORIZONTAL, false);
        svTop = hardwareMap.get(Servo.class, "svTop");
        svBottom = hardwareMap.get(Servo.class, "svBottom");

        dcElevator = hardwareMap.get(DcMotor.class, "dcElevator");

    }

    @Override
    public void loop(){

        //Control the modules
        swerveController.controlModules();

        //Might need to be reversed later. Controls Elevator.
        dcElevator.setPower(gamepad2.left_stick_y);

        if (gamepad2.dpad_up) {
            svTop.setPosition(.857);
            svBottom.setPosition(.857);
        }
        //In?
        else if (gamepad2.dpad_down) {
            svTop.setPosition(0.286);
            svBottom.setPosition(0.286);
        }

    }
}

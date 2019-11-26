package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.IndependentArmController;
import org.firstinspires.ftc.teamcode.Subsystems.ServoArmController;
import org.firstinspires.ftc.teamcode.Swerve.Enums.WheelDirection;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Swerve.SwerveMath;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

/**
 * Class created to test various new mechanisms
 * @author Troy Lopez
 */

@TeleOp(name = "SwerveTeleop", group = "Teleop")

public class SwerveTeleop extends OpMode {

    //Creates a new swerve controller
    //private SwerveController swerveController;

    private IndependentArmController independentArmController;

    //private Servo svTop;
    //private Servo svBottom;

    //private DcMotor dcElevator;

    //private Boolean armSwitch;

    @Override
    public void init() {

        independentArmController = new IndependentArmController(hardwareMap, gamepad1);

        //Initialize the swerve controller
        //swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.HORIZONTAL, false);
        //servoArmController = new ServoArmController(hardwareMap);

        //servoArmController.zeroArms();

        // Motors related to elevator
        //dcElevator = hardwareMap.get(DcMotor.class, "dcElevator");
        //svTop = hardwareMap.get(Servo.class, "svTop");
        //svBottom = hardwareMap.get(Servo.class, "svBottom");

        //armSwitch = false;

    }

    @Override
    public void loop(){

        independentArmController.controlArms();

        /*

         * Swerve drive

        swerveController.controlModules();

        /**
         * Moves Elevator

        if (gamepad1.dpad_down && dcElevator.getCurrentPosition() > 0){
            dcElevator.setPower(-.7);
        }

        /**
         * Adjusts rack and pinion
         *
        if (gamepad2.right_bumper) {
            svTop.setPosition(.857);
            svBottom.setPosition(.857);
        }
        else if (gamepad2.left_bumper) {
            svTop.setPosition(0.286);
            svBottom.setPosition(0.286);
        }
        */



    }
}

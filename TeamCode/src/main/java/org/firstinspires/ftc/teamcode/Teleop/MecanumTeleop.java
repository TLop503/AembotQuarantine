package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.teamcode.Subsystems.ServoArmController;

/**
 * Emergency fallback teleop incase swerve breaks
 * @author Troy Lopez
 */

@Disabled

@TeleOp(name = "MecanumTeleop",  group = "Teleop")
public class MecanumTeleop extends OpMode {

    private Servo svTop;
    private Servo svBottom;
    private DcMotor dcElevator;
    private ServoArmController servoArmController;
    private Boolean armSwitch;
    private DcMotor dcFrontRight;
    private DcMotor dcFrontLeft;
    private DcMotor dcBackLeft;
    private DcMotor dcBackRight;

    @Override
    public void init() {

        servoArmController = new ServoArmController(hardwareMap);

        servoArmController.zeroArms();

        dcElevator = hardwareMap.get(DcMotor.class, "dcElevator");
        svTop = hardwareMap.get(Servo.class, "svTop");
        svBottom = hardwareMap.get(Servo.class, "svBottom");

        dcBackLeft = hardwareMap.get(DcMotor.class, "dcBackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "dcBackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "dcFrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "dcFrontRight");

        armSwitch = false;

    }

    @Override
    public void loop() {

        dcFrontLeft.setPower(gamepad1.left_stick_y);
        dcBackLeft.setPower(gamepad1.left_stick_y);
        dcFrontRight.setPower(gamepad1.right_stick_y);
        dcBackRight.setPower(gamepad1.right_stick_y);

        /**
         * Moves Elevator
         */
        if (gamepad1.dpad_down && dcElevator.getCurrentPosition() > 0){
            dcElevator.setPower(-.7);
        }

        /**
         * Adjusts rack and pinion
         */
        if (gamepad2.right_bumper) {
            svTop.setPosition(.857);
            svBottom.setPosition(.857);
        }
        else if (gamepad2.left_bumper) {
            svTop.setPosition(0.286);
            svBottom.setPosition(0.286);
        }

        /**
         * Toggles active arm
         */
        if (gamepad2.y){
            armSwitch = !armSwitch;

            if (armSwitch == false){
                telemetry.addData("Left Arm Active", "\u200b");
            }
            else if (armSwitch == true){
                telemetry.addData("Right Arm Active", "\u200b");
            }

        }

        /**
         * Manipulates right arm
         */
        if (armSwitch == true){
            if (gamepad2.dpad_down){
                //servoArmController.moveRightDown();
            }
            if (gamepad2.dpad_up){
                //servoArmController.moveRightUp();
            }
            if (gamepad2.a){
            //    servoArmController.gripRight();
            }
            if (gamepad2.b){
              //  servoArmController.unGripRight();
            }
        }

        /**
         * Manipulates left arm
         */
        else {
            if (gamepad2.dpad_down){
                //servoArmController.moveLeftDown();
            }
            if (gamepad2.dpad_up){
            //    servoArmController.moveLeftUp();
            }
            if (gamepad2.a){
              //  servoArmController.gripLeft();
            }
            if (gamepad2.b){
                //servoArmController.unGripLeft();
            }
        }

    }
}
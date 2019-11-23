package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.ServoArmController;
import org.firstinspires.ftc.teamcode.Swerve.Enums.WheelDirection;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Swerve.SwerveMath;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

/**
 * Class created to drive the swerve modules to a wanted angle, and manipulate arms and elevators
 * using a pair of gamepads
 * @author Will Richards, Troy Lopez
 */



@TeleOp(name = "SwerveTeleop", group = "Teleop")
public class SwerveTeleop extends OpMode {

    //Creates a new swerve controller
    private SwerveController swerveController;

    private ServoArmController servoArmController;

    private Servo svTop;
    private Servo svBottom;

    private DcMotor dcElevator;

    private Boolean armSwitch;

    @Override
    public void init() {

        //Initialize the swerve controller
        swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.HORIZONTAL, false);
        servoArmController = new ServoArmController(hardwareMap);

        servoArmController.zeroArms();

        // Motors related to elevator
        dcElevator = hardwareMap.get(DcMotor.class, "dcElevator");
        svTop = hardwareMap.get(Servo.class, "svTop");
        svBottom = hardwareMap.get(Servo.class, "svBottom");

        armSwitch = false;

    }

    @Override
    public void loop(){

        /**
         * Swerve drive
         */
        swerveController.controlModules();

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
                servoArmController.moveRightDown();
            }
            if (gamepad2.dpad_up){
                servoArmController.moveRightUp();
            }
            if (gamepad2.a){
                servoArmController.gripRight();
            }
            if (gamepad2.b){
                servoArmController.unGripRight();
            }
        }

        /**
         * Manipulates left arm
         */
        else {
            if (gamepad2.dpad_down){
                servoArmController.moveLeftDown();
            }
            if (gamepad2.dpad_up){
                servoArmController.moveLeftUp();
            }
            if (gamepad2.a){
                servoArmController.gripLeft();
            }
            if (gamepad2.b){
                servoArmController.unGripLeft();
            }
        }


    }
}

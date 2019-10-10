
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Teleop",  group = "Teleop")
public class Teleop extends OpMode {

    private Servo svTop;
    private Servo svBottom;

    private double svTopZero;
    private double svBottomZero;

    /**
     * This method is run on robot init
     * Use as constructor
     */



    @Override
    public void init() {
        svTop = hardwareMap.get(Servo.class, "svTop");
        svBottom = hardwareMap.get(Servo.class, "svBottom");

        svTopZero = svTop.getPosition();
        svBottomZero = svBottom.getPosition();
    }

    /**
     * This method is looped when the robot is enabled
     * Normal control / etc. code goes here
     */
    @Override
    public void loop() {
        if (gamepad1.dpad_left){
            svTop.setPosition(svTopZero - 0.5);
            svBottom.setPosition(svBottomZero - 0.5);
        }
        else if (gamepad1.dpad_right){
            svTop.setPosition(svTopZero + 0.5);
            svBottom.setPosition(svTopZero + 0.5);
        }
        }

    }

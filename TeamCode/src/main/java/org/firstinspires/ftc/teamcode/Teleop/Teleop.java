
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


    /**
     * This method is run on robot init
     * Use as constructor
     */



    @Override
    public void init() {
        svTop = hardwareMap.get(Servo.class, "svTop");
        svBottom = hardwareMap.get(Servo.class, "svBottom");

    }

    /**
     * This method is looped when the robot is enabled
     * Normal control / etc. code goes here
     */
    @Override
    public void loop() {
            if (gamepad1.dpad_left){
             svTop.setPosition(1);
              svBottom.setPosition(1);
             }
            else if (gamepad1.dpad_right){
               svTop.setPosition(0);
             svBottom.setPosition(0);
            }
            else if (gamepad1.b){
                svTop.setPosition(0.5);
                svBottom.setPosition(0.5);
            }
        }

    }


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

    //Fill this space with variables
    private Servo svTop;
    private Servo svBottom;
    private DcMotor dcDriveL;
    private DcMotor dcDriveR;

    @Override
    public void init() {
        //Hardware Mapping + init phase

        svTop = hardwareMap.get(Servo.class, "svTop");
        svBottom = hardwareMap.get(Servo.class, "svBottom");
        dcDriveL = hardwareMap.get(DcMotor.class, "dcDriveL");
        dcDriveR = hardwareMap.get(DcMotor.class, "dcDriveR");

    }

    @Override
    public void loop() {
        //Teleop Button Mapping

            dcDriveL.setPower(gamepad1.left_stick_y);
            dcDriveR.setPower(-1 * gamepad1.right_stick_y);
            /*
            if (gamepad2.dpad_left){
             svTop.setPosition(1);
              svBottom.setPosition(1);
             }
            else if (gamepad2.dpad_right){
               svTop.setPosition(0);
             svBottom.setPosition(0);
            }

            else if (gamepad2.b){
                svTop.setPosition(0.5);
                svBottom.setPosition(0.5);

             */
            }
        }

    }

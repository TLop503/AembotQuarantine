
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

    //Unused Rack and Pinion servos
    /*
    private Servo svTop;
    private Servo svBottom;
    */
    private DcMotor dcDriveL;
    private DcMotor dcDriveR;
    //privaate DcMotor elevator;

    @Override
    public void init() {
        //Hardware Mapping + init phase

        //svTop = hardwareMap.get(Servo.class, "svTop");
        //svBottom = hardwareMap.get(Servo.class, "svBottom");
        dcDriveL = hardwareMap.get(DcMotor.class, "dcDriveL");
        dcDriveR = hardwareMap.get(DcMotor.class, "dcDriveR");

    }

    @Override
    public void loop() {
        //Teleop Button Mapping

        dcDriveL.setPower(gamepad1.left_stick_y);
        dcDriveR.setPower(-gamepad1.right_stick_y);
        /*
        //Unused Rack And Pinion controls
        if (gamepad2.dpad_up) {
            svTop.setPosition(1);
            svBottom.setPosition(1);
        } else if (gamepad2.dpad_down) {
            svTop.setPosition(0);
            svBottom.setPosition(0);
        }
        */

    }
}
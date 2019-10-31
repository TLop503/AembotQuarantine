
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

    private CRServo svTop;
    private CRServo svBottom;

    private DcMotor dcDriveL;
    private DcMotor dcDriveR;
    private DcMotor dcElevator;

    @Override
    public void init() {
        //Hardware Mapping + init phase

        svTop = hardwareMap.get(CRServo.class, "svTop");
        svBottom = hardwareMap.get(CRServo.class, "svBottom");
        dcDriveL = hardwareMap.get(DcMotor.class, "dcDriveL");
        dcDriveR = hardwareMap.get(DcMotor.class, "dcDriveR");
        dcElevator = hardwareMap.get(DcMotor.class, "dcElevator");

    }

    @Override
    public void loop() {

        //Standard tank drive.
        dcDriveL.setPower(gamepad1.left_stick_y);
        dcDriveR.setPower(-gamepad1.right_stick_y);

        //Might need to be reversed. Controls elevator
        dcElevator.setPower(gamepad2.left_stick_y);

        //Rack and pinion control
        //Out
        if (gamepad2.dpad_up) {
            svTop.setPower(1);
            svBottom.setPower(1);
        }
        //In
        else if (gamepad2.dpad_down) {
            svTop.setPower(-1);
            svBottom.setPower(-1);
        }
        //Rest
        else{
            svBottom.setPower(0);
            svTop.setPower(0);
        }

    }

}

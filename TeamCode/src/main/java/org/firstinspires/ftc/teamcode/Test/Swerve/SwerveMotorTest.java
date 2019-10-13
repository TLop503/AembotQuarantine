package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Use this method to test any motors you may need
 * @author Will Richards
 */
@TeleOp(name = "Swerve Motor Test", group = "Test")
@Disabled
public class SwerveMotorTest extends OpMode {

    //Two swerve motors
    private DcMotor TopSwerveMotor;
    private DcMotor BottomSwerveMotor;

    @Override
    public void init() {

        //Initializes them to the motors on board
        TopSwerveMotor = hardwareMap.get(DcMotor.class, "RightTopSwerveMotor");
        BottomSwerveMotor = hardwareMap.get(DcMotor.class, "RightBottomSwerveMotor");
    }

    @Override
    public void loop() {

        //Set the motors to a power
        TopSwerveMotor.setPower(1);
        BottomSwerveMotor.setPower(1);

    }
}

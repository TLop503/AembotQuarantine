package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * A test class for determining if power multipliers for specific motors/modules would help the robot
 * drive in straight lines.
 * @author Zane Othman-Gomez
 */
@TeleOp(name = "Power Multiplier Test", group = "test")
public class PowerMultiplierTest extends OpMode {
    private DcMotor dcRightTop;
    private DcMotor dcRightBottom;
    private DcMotor dcLeftTop;
    private DcMotor dcLeftBottom;

    @Override
    public void init() {
        // Initialize the swerve motors outside of a SwerveController for testing purposes
        dcRightTop = hardwareMap.dcMotor.get("RightTopSwerveMotor");
        dcLeftTop = hardwareMap.dcMotor.get("LeftTopSwerveMotor");
        dcRightBottom = hardwareMap.dcMotor.get("RightBottomSwerveMotor");
        dcLeftBottom = hardwareMap.dcMotor.get("LeftBottomSwerveMotor");
    }

    @Override
    public void loop() {
        if (gamepad1.right_bumper) {
            dcRightTop.setPower(0.5);
        }

        if (gamepad1.right_trigger > 0) {
            dcRightBottom.setPower(-0.5);
        }

        if (gamepad1.left_bumper) {
            dcLeftTop.setPower(0.7);
        }

        if (gamepad1.left_trigger > 0) {
            dcLeftBottom.setPower(-0.5);
        }

        if (gamepad1.atRest()) {
            dcRightTop.setPower(0);
            dcLeftTop.setPower(0);
            dcRightBottom.setPower(0);
            dcLeftBottom.setPower(0);
        }
    }
}

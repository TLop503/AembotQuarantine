package org.firstinspires.ftc.teamcode.Test.General;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This test was written to facilitate finding the necessary position for a servo, just in case
 * that servo cannot be programmed with the servo programmer.
 * @author Zane Othman-Gomez
 */
@TeleOp(name="Servo Position Test", group="test")
@Disabled
public class ServoPositionTest extends OpMode {
    // The rack-and-pinion servo for stacking
    private CRServo svBottom;

    // The current target position of the servo
    private double targetPosition = 0.5;

    @Override
    public void init() {
        // Declare the servo to be tested
        svBottom = hardwareMap.crservo.get("svBottom");
    }

    @Override
    public void loop() {
        //double currentPosition = svBottom.getPosition();
        svBottom.setPower(gamepad1.left_stick_y);

        /*
        // region Large Adjustments
        if (gamepad1.y) {
            targetPosition = targetPosition + 0.1;
            svBottom.setPosition(targetPosition);
        } else if (gamepad1.a) {
            targetPosition = targetPosition - 0.1;
            svBottom.setPosition(targetPosition);
        }
        // endregion

        // region Smaller Adjustments
        if (gamepad1.dpad_up) {
            targetPosition = targetPosition + 0.05;
            svBottom.setPosition(targetPosition);
        } else if (gamepad1.dpad_down) {
            targetPosition = targetPosition - 0.05;
            svBottom.setPosition(targetPosition);
        }
        // endregion

        // region Smallest Adjustments
        if (gamepad1.start) {
            targetPosition = targetPosition + 0.01;
            svBottom.setPosition(targetPosition);
        } else if (gamepad1.back) {
            targetPosition = targetPosition - 0.05;
            svBottom.setPosition(targetPosition);
        }
        // endregion
         */

        // Print current position to telemetry
        telemetry.addData("Current Power: ", gamepad1.left_stick_y);
    }
}

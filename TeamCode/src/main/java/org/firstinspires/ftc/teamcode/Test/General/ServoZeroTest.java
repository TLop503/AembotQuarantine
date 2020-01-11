package org.firstinspires.ftc.teamcode.Test.General;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Zero svBottom", group = "Test")
public class ServoZeroTest extends OpMode {
    private Servo svBottom;
    private boolean hasRun = false;

    @Override
    public void init() {
        svBottom = hardwareMap.servo.get("svBottom");
    }

    @Override
    public void loop() {
        if (!hasRun) {
            svBottom.setPosition(0.5);
            hasRun = true;
        } else {
            requestOpModeStop();
        }
    }
}

package org.firstinspires.ftc.teamcode.Test.General;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class GrabberTest extends OpMode {
    public Servo pinion;

    /**
     * Run when "init" button is pressed
     */
    public void init() {
        pinion = hardwareMap.get(Servo.class, "pinionServo");
    }

    /**
     * Run when play button is pressed
     */
    public void loop() {
        boolean hasRun = false;

        if(!hasRun) {
            pinion.setPosition(0);
            pinion.setPosition(0.1);

            hasRun = true;
        }
    }
}

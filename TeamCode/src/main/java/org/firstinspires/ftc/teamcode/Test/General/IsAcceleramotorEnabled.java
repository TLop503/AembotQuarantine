package org.firstinspires.ftc.teamcode.Test.General;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.android.AndroidAccelerometer;

@Autonomous(name="Is Android Accelerometer Enabled", group="Test")
public class IsAcceleramotorEnabled extends OpMode {

        public AndroidAccelerometer accelerometer;
        public boolean IsEnabled;

        public void init() {

            accelerometer = new AndroidAccelerometer();

        }

        public void loop() {
            IsEnabled = accelerometer.isAvailable();

            telemetry.addData("Is enabled: ", IsEnabled);
            telemetry.update();
        }
    }

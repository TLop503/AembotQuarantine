package org.firstinspires.ftc.teamcode.Test.General;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Autonomous(name = "SparkMini Tester", group = "Test")
@Disabled
public class SparkMINITester extends OpMode {

    private DcMotorSimple TestMotor;
    private DigitalChannel magSwitch;

    @Override
    public void init() {
        TestMotor = hardwareMap.get(DcMotorSimple.class, "TestMotor");
        magSwitch = hardwareMap.get(DigitalChannel.class, "MagSwitch");
    }

    @Override
    public void loop() {
        if(magSwitch.getState())
            TestMotor.setPower(1);
        else
            TestMotor.setPower(0);
    }
}

package org.firstinspires.ftc.teamcode.NormalSwerve;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Autonomous(name = "Swerve Data Test", group = "test")
public class SwerveDataTest extends OpMode {

    private HardwareMap hardwareMap;
    private Gamepad gamepad1;
    private Telemetry telemetry;
    private NormalSwerveModule swerveModule;

    private DcMotor mainMotor;
    private CRServo mainServo;

    @Override
    public void init() {
        mainServo = hardwareMap.get(CRServo.class, "mainServo");
        swerveModule = new NormalSwerveModule(hardwareMap, gamepad1, telemetry);
    }

    @Override
    public void loop() {

        swerveModule.autoPIDControl(50, .5);
    }

}
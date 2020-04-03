package org.firstinspires.ftc.teamcode.NormalSwerve;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "Swerve Data Test", group = "test")
public class SwerveDataTest extends LinearOpMode {

    private HardwareMap hardwareMap;
    private Gamepad gamepad1;
    private Telemetry telemetry;
    private NormalSwerveModule swerveModule;

    private DcMotor mainMotor;
    private Servo mainServo;

    public void runOpMode(){
        mainMotor = hardwareMap.get(DcMotor.class, "mainMotor");
        mainServo = hardwareMap.get(Servo.class, "mainServo");
        double maxMotorSpeed = 0.5;
        swerveModule = new NormalSwerveModule(hardwareMap, gamepad1, telemetry);

        waitForStart();

        swerveModule.autoPIDControl(50, .5);
    }
}
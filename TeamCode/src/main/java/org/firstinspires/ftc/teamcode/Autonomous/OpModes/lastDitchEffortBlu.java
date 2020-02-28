package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Autonomous.Utilites.AutoDistances;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;
import org.firstinspires.ftc.teamcode.Autonomous.Utilites.AutoDistances;
@Disabled
@Autonomous(name = "Linear Foundation Auto V32 - Red ", group = "Autonomous")
public class lastDitchEffortBlu extends LinearOpMode {
    private SwerveController swerve;
    private DcMotor gripMotor;

    //Currently ignores side differences & start rules, array errors need to be patched first
    //TODO: Test code since it was written for old swerve

    @Override
    public void runOpMode() throws InterruptedException {
        swerve = new SwerveController(null, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);
        //elevator = hardwareMap.get(DcMotorSimple.class, "ElevatorMotor");
        gripMotor = hardwareMap.get(DcMotor.class, "gripMotor");

        waitForStart();

        //Works
        //Drives to foundation
        boolean getBlock = false;
        while (!getBlock) {
            getBlock = swerve.autoControlModules(0, 24, 0.4);
        }

        //grab
        gripMotor.setPower(-0.1);
        sleep(500);
        gripMotor.setPower(0);

        sleep(1000);
        boolean takeItBackNowYall = false;
        while (!takeItBackNowYall) {
            takeItBackNowYall = swerve.autoControlModules(0, 24, -0.4);
        }

        sleep(1000);
        boolean strafeToPosition = false;
        while (!strafeToPosition) {
            strafeToPosition = swerve.autoControlModules(90, 36, 0.4);
        }
    }
}

package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

@Autonomous(name = "Linear Foundation Auto V32", group = "Autonomous")
public class FoundationAutoLinear extends LinearOpMode {
    private SwerveController swerve;
    private DcMotorSimple elevator;

    //Currently ignores side differences & start rules, array errors need to be patched first
    //TODO: Test code since it was written for old swerve

    @Override
    public void runOpMode() throws InterruptedException {
        swerve = new SwerveController(null, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);
        elevator = hardwareMap.get(DcMotorSimple.class, "ElevatorMotor");

        waitForStart();

        //Works
        //Drives to foundation
        boolean strafeToPosition = false;
        while (!strafeToPosition) {
            strafeToPosition = swerve.autoControlModules(20, 39, 0.4);
        }

        sleep(1000);
        elevator.setPower(-0.5);
        sleep(1000);
        elevator.setPower(0);

        //Pull foundation back
        boolean drivenToFoundation = false;
        while (!drivenToFoundation) {
            drivenToFoundation = swerve.autoControlModules(0, 48, -0.2);
        }

        elevator.setPower(-0.5);
        sleep(1000);
        elevator.setPower(0.0);
        sleep(1000);

        //Sliiide to the left
        boolean strafeToPark = false;
        while (!strafeToPark) {
            strafeToPark = swerve.autoControlModules(-30, 60, 0.2);
        }

    }
}

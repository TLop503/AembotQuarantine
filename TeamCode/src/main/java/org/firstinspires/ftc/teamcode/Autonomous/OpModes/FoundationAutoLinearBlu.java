package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Autonomous.Utilites.AutoDistances;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.ElevatorPosition;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;
import  org.firstinspires.ftc.teamcode.Subsystems.ElevatorSystemController;


@Autonomous(name = "Linear Foundation Auto V32 - Blu ", group = "Autonomous")
@Disabled
public class FoundationAutoLinearBlu extends LinearOpMode {
    private SwerveController swerve;
    //private ElevatorSystemController elevator;

    //Currently ignores side differences & start rules, array errors need to be patched first
    //TODO: Test code since it was written for old swerve

    @Override
    public void runOpMode() throws InterruptedException {
        swerve = new SwerveController(null, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);

        waitForStart();

        //Works
        //Drives to foundation
        boolean strafeToPosition = false;
        while (!strafeToPosition) {
            strafeToPosition = swerve.autoControlModules(-20, AutoDistances.getFoundation, 0.35);
        }

        //elevator.autoControlElevator(ElevatorPosition.UP);
        //elevator.autoControlElevator(ElevatorPosition.DOWN);

        //sleep(1000);

        //Pull foundation back
        boolean drivenToFoundation = false;
        while (!drivenToFoundation) {
            drivenToFoundation = swerve.autoControlModules(0, AutoDistances.dragFoundation, -0.35);
        }

        //sleep(1000);
        //elevator.autoControlElevator(ElevatorPosition.UP);

        boolean strafeToClear = false;
        while (!strafeToClear) {
            strafeToClear = swerve.autoControlModules(90, AutoDistances.strafeOut, 0.4);
        }
        //sleep(1000);

        boolean strafeToPark = false;
        while (!strafeToPark) {
            strafeToPark = swerve.autoControlModules(30, AutoDistances.parkOnLine, 0.4);
        }

    }
}

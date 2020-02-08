package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Subsystems.StoneGripController;
//import org.firstinspires.ftc.teamcode.Subsystems.Utilities.GripArmPosition;
//import org.firstinspires.ftc.teamcode.Subsystems.Utilities.MoveArmDirection;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

@Autonomous(name = "Linear Foundation Auto V25 ", group = "Autonomous")
public class FoundationAutoLinear extends LinearOpMode {
    private SwerveController swerve;
    private StoneGripController stoneArms;
    private DcMotorSimple elevator;

    //Currently ignores side differences & start rules, array errors need to be patched first

    @Override
    public void runOpMode() throws InterruptedException {
        swerve = new SwerveController(null, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);
        stoneArms = new StoneGripController(hardwareMap, null, telemetry);
        elevator = hardwareMap.get(DcMotorSimple.class, "ElevatorMotor");

        waitForStart();

        //Works
        //Drives to foundation
        boolean strafeToPosition = false;
        while (!strafeToPosition) {
            strafeToPosition = swerve.autoControlModules(75, 38, 0.4);
        }
        /*
        elevator.setPower(-0.5);
        sleep(1000);
        elevator.setPower(0.5);
        sleep(9500);

         */

        sleep(1000);

        //Pull foundation back
        boolean drivenToFoundation = false;
        while (!drivenToFoundation) {
            drivenToFoundation = swerve.autoControlModules(0, -4, 0.2);
        }

        sleep(1000);

        //Sliiide to the left
        boolean strafeToPark = false;
        while (!strafeToPark) {
            strafeToPark = swerve.autoControlModules(-260, 2.5, 0.2);
        }

    }
}

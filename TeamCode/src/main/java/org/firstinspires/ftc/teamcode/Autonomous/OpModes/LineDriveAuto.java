
package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

@Autonomous(name = "Line Drive Auto", group = "Autonomous")
public class LineDriveAuto extends OpMode {

    private boolean hasRun = false;
    private SwerveController swerveController;
    private DcMotor elevator;

    @Override
    public void init() {
        //swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.HORIZONTAL, false);

    }

    @Override
    public void loop() {
        if(!hasRun){
            //Parks On Line
            //hasRun = swerveController.autoControlModules(0, 26, 0.5);
            elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            elevator.setTargetPosition(2240);
            elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elevator.setPower(.25);

            while (elevator.isBusy()){

            }
            elevator.setPower(0);
        }
    }
}
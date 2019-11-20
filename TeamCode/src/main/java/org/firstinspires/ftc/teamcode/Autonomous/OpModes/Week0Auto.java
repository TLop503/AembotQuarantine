
package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

//import com.qualcomm.robotcore.hardware.DcMotorSimple;
@Autonomous(name = "Week0Auto", group = "Autonomous")

public class Week0Auto extends OpMode {

    private boolean hasRun = false;
    private SwerveController swerveController;

    @Override
    public void init() {
        swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.HORIZONTAL, false);
    }

    @Override
    public void loop() {
        if(hasRun == false) {

            //Parks On Line
            swerveController.autoControlModules(0, 26, .5);

            //Ends Program
            hasRun = true;
        }
    }

}
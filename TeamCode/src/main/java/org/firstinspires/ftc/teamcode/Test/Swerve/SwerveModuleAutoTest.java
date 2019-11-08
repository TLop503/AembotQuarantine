package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Swerve.SwerveController;

/**
 * This class is used for testing autonomous control of the swerve module
 * @author Will Richards
 */
@Autonomous(name = "Swerve Auto Test", group = "Test")
@Disabled
public class SwerveModuleAutoTest extends OpMode {

    //Creates a swerve controller
    private SwerveController controller;

    @Override
    public void init() {

        //Instantiates the swerve controller variable
        controller = new SwerveController(gamepad1, hardwareMap, telemetry);
    }

    @Override
    public void loop() {

        //Uses the overloaded controlModules method to allow an angle to be passed instead of nothing
        controller.controlModules(45);
    }
}

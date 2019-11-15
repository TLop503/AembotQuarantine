package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Swerve.Enums.WheelDirection;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Swerve.SwerveMath;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

/**
 * Class created to test driving the swerve module to a wanted angle, using a jopystick
 * @author Will Richards
 */
@TeleOp(name = "Field Centric Test", group = "Test")
public class FieldCentricTest extends OpMode {

    //Creates a new swerve controller
    private SwerveController swerveController;

    @Override
    public void init() {

        //Initialize the swerve controller
        swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.HORIZONTAL);

    }

    @Override
    public void loop() {

        //Control the modules
        swerveController.controlModules();

    }
}

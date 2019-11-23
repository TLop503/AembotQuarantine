package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ServoArmController;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Swerve.SwerveMath;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

/**
 * Class created to test driving the swerve module to a wanted angle, using a jopystick
 * @author Will Richards
 */
@TeleOp(name = "Robot Centric Test", group = "Test")
public class NonFieldCentricTest extends OpMode {

    //Creates a new swerve controller
    private SwerveController swerveController;
    private ServoArmController servoArm;
    @Override
    public void init() {

        //Initialize the swerve controller
        swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);
        servoArm = new ServoArmController(hardwareMap);

        //Set the position of the servos to zero on start
        servoArm.zeroArms();

        //Zero the position of the modules at init
        swerveController.zeroModules();

        telemetry.addData("Wanted Rotation: ", SwerveMath.normalizeJoystickAngle(gamepad1));

        while (swerveController.activeZeroModules()){

        }
    }

    @Override
    public void loop() {

        //Control the modules
        swerveController.controlModules();
        swerveController.getSwitch();

        if (gamepad1.a){
            servoArm.moveDown();
        }
        if (gamepad1.y){
            servoArm.moveUp();
        }
        if (gamepad1.x){
            servoArm.grip();
        }
        if (gamepad1.b){
            servoArm.unGrip();
        }

    }
}

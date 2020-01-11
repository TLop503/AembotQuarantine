package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ElevatorSystemController;
import org.firstinspires.ftc.teamcode.Subsystems.StoneGripController;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;
/**
 * Main TeleOp Mode to be used during comp
 * @author Will Richards, Troy Lopez, Zane Othman-Gomez
 */

@TeleOp(name = "Main TeleOp", group = "Competition")
public class MainTeleOp extends OpMode {

    //Creates a new swerve controller
    private SwerveController swerveController;

    //Used to control the elevator for stacking stones
    private ElevatorSystemController elevatorSystem;

    // This is for the stone grabbing arm with an elevator
    private StoneGripController stoneGripController;

    // FIXME: This isn't used - should we remove it in favor of the IndependentArmController or ServoArmController classes?
    // private Servo svBottom;

    private Boolean armToggle = false;

    @Override
    public void init() {
        //Initialize the swerve controller
        swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);

        //Creates a new elevator controller
        elevatorSystem = new ElevatorSystemController(hardwareMap, gamepad2, telemetry);

        // Instantiate a controller for the stone-grabbing arms
        stoneGripController = new StoneGripController(hardwareMap, gamepad2, telemetry);

        //Zero the position of the modules at init
        swerveController.zeroModules();
    }

    @Override
    public void loop() {
        //Control the swerve modules
        swerveController.controlModules();

        //Controls the elevator
        elevatorSystem.controlElevator();

        // Control the two different stone-gripping arms with switching between them supported
        stoneGripController.controlArms();
    }
}
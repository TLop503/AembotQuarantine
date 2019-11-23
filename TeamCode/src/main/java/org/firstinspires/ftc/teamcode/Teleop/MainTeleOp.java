package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ElevatorSystemController;
import org.firstinspires.ftc.teamcode.Subsystems.ServoArmController;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;

/**
 * Main TeleOp Mode to be used during comp
 * @author Will Richards, Troy Lopez
 */
@TeleOp(name = "Main OpMode", group = "Competition")
public class MainTeleOp extends OpMode {

    //Creates a new swerve controller
    private SwerveController swerveController;

    //Creates a new controller to control the arms
    private ServoArmController servoArm;

    //Used to controll the elevator
    private ElevatorSystemController elevatorSystem;


    @Override
    public void init() {

        //Initialize the swerve controller
        swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);

        //Creates a new servo controller
        servoArm = new ServoArmController(hardwareMap, gamepad2);

        //Creates a new elevator controller
        elevatorSystem = new ElevatorSystemController(hardwareMap, gamepad2);

        //Set the position of the servos to zero on start
        servoArm.zeroArms();

        //Zero the position of the modules at init
        swerveController.zeroModules();

    }

    @Override
    public void loop() {

        //Control the swerve modules
        swerveController.controlModules();

        //Controls the servo arms
        servoArm.controlArms();

        //Controls the elevator
        elevatorSystem.controlElevator();


    }
}

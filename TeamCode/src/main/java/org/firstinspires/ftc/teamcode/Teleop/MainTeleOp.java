package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.ElevatorSystemController;
import org.firstinspires.ftc.teamcode.Subsystems.ServoArmController;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;
import org.firstinspires.ftc.teamcode.Subsystems.ElevatorArmController;

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
    private ElevatorArmController elevatorArmController;

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
        elevatorArmController = new ElevatorArmController(hardwareMap, gamepad2);

        //Zero the position of the modules at init
        swerveController.zeroModules();

        elevatorArmController.setArm();

    }

    @Override
    public void loop() {
        //Control the swerve modules
        swerveController.controlModules();

        //Controls the elevator
        elevatorSystem.controlElevator();

        //elevatorArmController.svElevator();

        elevatorArmController.svPivot();


        // TODO: Wasn't the entire point of having subsystems to eliminate these if/else if/else chains?
        /*if (gamepad1.a) {
            svBottom.setPosition(0.857);

            //TODO: lift
            svBottom.setPosition(0.286);


            //swerveController.autoControlModules(270, 12, .5);

            // TODO: lower

        }*/


        if (gamepad2.right_bumper) {
            armToggle = !armToggle;
        }
        // FIXME: Change this to not !
        if (!armToggle){
            elevatorArmController.controlArms();
        }
        /*
        else {
            elevatorArmController.svPivot();
            // elevatorArmController.svElevator();

            if(gamepad2.x){
                elevatorArmController.closeGrip();
            }
            if (gamepad2.y){
                elevatorArmController.openGrip();
            }
        }
    */

    }
}
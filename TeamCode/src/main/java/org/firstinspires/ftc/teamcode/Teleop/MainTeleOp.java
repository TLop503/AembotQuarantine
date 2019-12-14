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
 * @author Will Richards, Troy Lopez
 */
@TeleOp(name = "Main OpMode", group = "Competition")
public class MainTeleOp extends OpMode {

    //Creates a new swerve controller
    private SwerveController swerveController;

    //Used to control the elevator
    private ElevatorSystemController elevatorSystem;

    private ElevatorArmController elevatorArmController;

    private Servo svBottom;
    private Boolean armToggle;

    @Override
    public void init() {

        //Initialize the swerve controller
        //swerveController = new SwerveController(gamepad1, hardwareMap, telemetry, IMUOrientation.VERTICAL, false);

        //Creates a new elevator controller
        //elevatorSystem = new ElevatorSystemController(hardwareMap, gamepad2);

        elevatorArmController = new ElevatorArmController(hardwareMap, gamepad2);

        //Zero the position of the modules at init
        //swerveController.zeroModules();

        elevatorArmController.setArm();

    }

    @Override
    public void loop() {

        //Control the swerve modules
        //swerveController.controlModules();



        //Controls the elevator
       // elevatorSystem.controlElevator();

        elevatorArmController.svElevator();

        elevatorArmController.svPivot();

        if (gamepad2.a){
            elevatorArmController.closeGrip();
        }

        if (gamepad2.b) {
            elevatorArmController.openGrip();
        }


        /*
        if (gamepad1.a){
            svBottom.setPosition(.857);
            svBottom.setPosition(0.286);

            //lift
            swerveController.autoControlModules(270, 12, .5);

            //lower

        }
        */

        if (gamepad2.y) {
            armToggle = !armToggle;
        }
        if (armToggle == true){
            elevatorArmController.controlArms();
        }
        else{
            elevatorArmController.svPivot();
            elevatorArmController.svElevator();

            if(gamepad2.a){
                elevatorArmController.closeGrip();
            }
            if (gamepad2.b){
                elevatorArmController.openGrip();
            }
        }

    }
}
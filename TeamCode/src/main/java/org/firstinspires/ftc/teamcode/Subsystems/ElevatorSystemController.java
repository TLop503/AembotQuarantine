package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Class created to control the elevator and its parts
 * @author Will Richards
 */
public class ElevatorSystemController {

    //Creates a motor that will control the elevator, it is a "simple" DcMotor becaues it is running a SparkMINI
    private DcMotorSimple elevatorMotor;

    //Servos that control the grippers of the stacker
    private Servo topArmServo;
    private Servo bottomArmServo;

    private Gamepad gamepad;

    /**
     * Constructs all necessary variables for controlling the elevator and its parts
     * @param hardwareMap a reference to the map of all the ports
     */
    public ElevatorSystemController(HardwareMap hardwareMap, Gamepad gamepad){

        this.gamepad = gamepad;

        elevatorMotor = hardwareMap.get(DcMotorSimple.class, "ElevatorMotor");

        topArmServo = hardwareMap.get(Servo.class, "TopArmServo");
        bottomArmServo = hardwareMap.get(Servo.class, "BottomArmServo");

    }

    /**
     * General method created to control the elevator based on input
     */
    public void controlElevator(){

        //Moves the elevator down
        if(gamepad.dpad_down){
            elevatorMotor.setPower(0.7);
        }

        //Moves the elevator up
        else if(gamepad.dpad_up){
            elevatorMotor.setPower(-0.7);
        }

        //If neither are pressed stop the elevator
        else {
            elevatorMotor.setPower(0);
        }

        //Used for opening the stacker
        //TODO: Make sure the values for the servo are correct
        if(gamepad.right_bumper) {
            topArmServo.setPosition(0.55);
            bottomArmServo.setPosition(0.55);
        }

        //Closes the stacker
        else if(gamepad.left_bumper) {
            topArmServo.setPosition(0.286);
            bottomArmServo.setPosition(0.286);
        }
    }
}

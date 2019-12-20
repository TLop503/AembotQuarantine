package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Class created to control the elevator and its parts
 * @author Will Richards
 */

public class ElevatorSystemController {

    //Creates a motor that will control the elevator, it is a "simple" DcMotor becaues it is running a SparkMINI
    private DcMotorSimple elevatorMotor;

    //Servos that control the grippers of the stacker
    //private Servo topArmServo;
    private CRServo svBottom;

    private Gamepad gamepad;

    private Telemetry telemetry;

    /**
     * Constructs all necessary variables for controlling the elevator and its parts
     * @param hardwareMap a reference to the map of all the ports
     */
    public ElevatorSystemController(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry){

        this.gamepad = gamepad;
        this.telemetry = telemetry;

        elevatorMotor = hardwareMap.get(DcMotorSimple.class, "ElevatorMotor");

        //topArmServo = hardwareMap.get(Servo.class, "TopArmServo");
        svBottom = hardwareMap.get(CRServo.class, "svBottom");

    }

    /**
     * General method created to control the elevator based on input
     */

    public void controlElevator(){

        //telemetry.addData("Arm Pos: ", svBottom.getPosition());

        //Moves the elevator down
        if(gamepad.dpad_down){
            elevatorMotor.setPower(-0.7);
        }

        //Moves the elevator up
        else if(gamepad.dpad_up){
            elevatorMotor.setPower(0.7);
        }

        //If neither are pressed stop the elevator
        else {
            elevatorMotor.setPower(0);
        }

        //Used for opening the stacker
        //TODO: Make sure the values for the servo are correct
        if(gamepad.dpad_left) {
            svBottom.setPower(1);
        }

        //Closes the stacker
        else if(gamepad.dpad_right) {
            svBottom.setPower(-1);
        }

        else {
            svBottom.setPower(0);
        }
    }
}
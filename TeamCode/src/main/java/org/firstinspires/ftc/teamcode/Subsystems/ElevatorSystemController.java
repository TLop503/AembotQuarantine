package org.firstinspires.ftc.teamcode.Subsystems;

import android.widget.Switch;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.ElevatorPosition;

import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.ElevatorPosition;

/**
 * Class created to control the elevator and its parts
 * @author Will Richards, Avi Lance
 */

public class ElevatorSystemController {

    //Creates vertical elevator motor and the rack and pinion gripping elevator motor
    private DcMotor gripMotor;
    private DcMotor vertMotor;

    private Gamepad gamepad;

    private Telemetry telemetry;

    /**
     * Constructs all necessary variables for controlling the elevator and its parts
     * @param hardwareMap a reference to the map of all the ports
     * @param gamepad The gamepad to use for controlling the elevator.
     * @param telemetry The telemetry that prints out to the phone for debugging purposes.
     */

    public ElevatorSystemController(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry){

        this.gamepad = gamepad;
        this.telemetry = telemetry;

        gripMotor = hardwareMap.get(DcMotor.class, "GripMotor");
        vertMotor = hardwareMap.get(DcMotor.class, "VerticalMotor");

    }

    /**
     * General method created to control the elevator based on input
     */
    public void controlElevator(){

        //telemetry.addData("Arm Pos: ", svBottom.getPosition());

        //Moves the elevator up
        if (gamepad.dpad_up){
            vertMotor.setPower(0.7);
        }
        //Moves the elevator down
        else if (gamepad.dpad_down){
            vertMotor.setPower(-0.7);
        }
        else{
            vertMotor.setPower(0);
        }

        //Check this to verify that these in fact are the motor powers to open and close it
        //Opens rack and pinion
        if (gamepad.dpad_left){
            gripMotor.setPower(0.6);
        }
        //Closes rack and pinion
        else if (gamepad.dpad_right) {
            gripMotor.setPower(-0.6);
        }
        else{
            gripMotor.setPower(0);
        }

    }

    public void autoControlElevator(ElevatorPosition elevatorPosition){
        //560 ticks to a rotation, 60:1 gearing
        if (elevatorPosition == ElevatorPosition.UP){
            vertMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            vertMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            vertMotor.setTargetPosition(3360);  //This value should trigger 1 rotation, could need to be changed
            vertMotor.setPower(0.5);
            if (vertMotor.isBusy()){
                //I think I can leave this blank?
            }
            else{
                vertMotor.setPower(0);
            }
        }
        //560 ticks to a rotation, 60:1 gearing
        if (elevatorPosition == ElevatorPosition.DOWN){
            vertMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            vertMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            vertMotor.setTargetPosition(3360);  //This value should trigger 1 rotation, could need to be changed
            vertMotor.setPower(-0.5);
            if (vertMotor.isBusy()){
                //I think I can leave this blank?
            }
            else{
                vertMotor.setPower(0);
            }
        }
    }

}
package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;


public class ElevatorArmController {
    private CRServo svElevator;
    private CRServo svPivot;
    private Servo svGrip;
    private Gamepad gamepad;
    private double armPos;

    public ElevatorArmController(HardwareMap hardwareMap, Gamepad gamepad){
        this.gamepad = gamepad;

        svElevator = hardwareMap.get(CRServo.class, "svElevator");
        svPivot = hardwareMap.get(CRServo.class, "svPivot");
        svGrip = hardwareMap.get(Servo.class, "svGrip");


    }

    public void svElevator(){
        svElevator.setPower(gamepad.left_stick_y);
    }

    public void svPivot(){
        svPivot.setPower(gamepad.right_stick_y);
    }

   public void setArm(){
        armPos = svGrip.getPosition();
   }

   public void closeGrip(){
        svGrip.setPosition(armPos + 0.5);
   }

   public void openGrip(){
        svGrip.setPosition(armPos - 0.5);
   }

}

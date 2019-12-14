package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

/**
 * Code written for controlling the elevator arm
 * @author Troy Lopez
 */


public class ElevatorArmController {
    private CRServo svElevator;
    private CRServo svPivot;
    private Servo svGrip;
    private Gamepad gamepad;
    private double armPos;
    private boolean armToggle;

    private Servo leftBlockArm;
    private Servo leftBlockGrip;

    public ElevatorArmController(HardwareMap hardwareMap, Gamepad gamepad){
        this.gamepad = gamepad;

        svElevator = hardwareMap.get(CRServo.class, "svElevator");
        svPivot = hardwareMap.get(CRServo.class, "svPivot");
        svGrip = hardwareMap.get(Servo.class, "svGrip");

        leftBlockArm = hardwareMap.get(Servo.class,"leftBlockArm");
        leftBlockGrip = hardwareMap.get(Servo.class, "leftBlockGrip");

        armToggle = true;

    }



    public void svElevator(){
        svElevator.setPower(-gamepad.left_stick_y);
    }

    public void svPivot(){
        svPivot.setPower(gamepad.right_stick_y);
    }

   public void setArm(){
        armPos = svGrip.getPosition();
   }

   public void closeGrip(){
        if (armPos > 0.6){
        svGrip.setPosition(armPos + 0.5);
        }
   }

   public void openGrip() {
       if (armPos < 1) {
           svGrip.setPosition(armPos - 0.5);

       }
   }

    public void controlArms(){
        if (gamepad.a){
            moveDown();
        }
        if (gamepad.y){
            moveUp();
        }
        if (gamepad.x){
            grip();
        }
        if (gamepad.b){
            unGrip();
        }
    }

    public void zeroArms(){
        leftBlockArm.setPosition(0);
        //rightBlockArm.setPosition(0);
    }


    public void moveDown(){
        leftBlockArm.setPosition(0.4);
        //rightBlockArm.setPosition(1);
    }


    private void moveUp() {
        leftBlockArm.setPosition(0);
        //rightBlockArm.setPosition(0);
    }


    private void grip() {
        leftBlockGrip.setPosition(1);
        //rightBlockGrip.setPosition(0.9);
    }


    private void unGrip() {
        leftBlockGrip.setPosition(0.1);
        //rightBlockGrip.setPosition(0.1);
    }


}
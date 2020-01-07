package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Class created to control the servos on the block arms
 * @author Troy Lopez
 */

public class IndependentArmController {

    //Left block arm
    private Servo leftBlockArm;
    private Servo leftBlockGrip;

    private Gamepad gamepad;

    //Right Block Arm
    private Servo rightBlockArm;
    private Servo rightBlockGrip;

    private boolean armToggle;


    /**
     * The constructor for a servo arm controller.
     * @param hardwareMap The HardwareMap configuration on the phones to get the ports for all 4 arm servos.
     */
    public IndependentArmController(HardwareMap hardwareMap, Gamepad gamepad){
        this.gamepad = gamepad;

        leftBlockArm = hardwareMap.get(Servo.class,"leftBlockArm");
        leftBlockGrip = hardwareMap.get(Servo.class, "leftBlockGrip");


        rightBlockArm = hardwareMap.get(Servo.class,"rightBlockArm");
        rightBlockGrip = hardwareMap.get(Servo.class, "rightBlockGrip");


        rightBlockArm.setDirection(Servo.Direction.REVERSE);
        rightBlockGrip.setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Called to set the arm positions to zero at start
     */
    public void zeroArms(){
        leftBlockArm.setPosition(0);
        rightBlockArm.setPosition(0);
    }

    /**
     * Move Left Arms Down
     */
    public void leftMoveDown(){
        leftBlockArm.setPosition(0.4);

    }

    /**
     * Retract Left Arms
     */
    private void leftMoveUp() {
        leftBlockArm.setPosition(0);

    }

    /**
     * Close the grabber on left arms
     */
    private void leftGrip() {
        leftBlockGrip.setPosition(0.8);
    }

    /**
     * Release the grip on the block on left arms
     */
    private void leftUnGrip() {
        leftBlockGrip.setPosition(0.1);

    }

    /**
     * Move Right Arms Down
     */
    public void rightMoveDown(){
        rightBlockArm.setPosition(0.4);

    }

    /**
     * Retract right Arms
     */
    private void rightMoveUp() {
        rightBlockArm.setPosition(0);

    }

    /**
     * Close the grabber on right arms
     */
    private void rightGrip() {
        rightBlockGrip.setPosition(0.8);
    }

    /**
     * Release the grip on the block on right arms
     */
    private void rightUnGrip() {
        rightBlockGrip.setPosition(0.1);
    }

    /**
     * General method to control the block arms
     */
    public void controlArms(){

        if (gamepad.dpad_up){
            armToggle =! armToggle;
        }

        if (gamepad.dpad_down){
            zeroArms();
        }

        if (armToggle == true){

            if (gamepad.a){
                leftMoveDown();
            }
            if (gamepad.y){
                leftMoveUp();
            }
            if (gamepad.x){
                leftGrip();
            }
            if (gamepad.b){
                leftUnGrip();
            }

            if (gamepad.dpad_down){
                zeroArms();
            }
        }

        if (armToggle == false){

            if (gamepad.a){
                rightMoveDown();
            }
            if (gamepad.y){
                rightMoveUp();
            }
            if (gamepad.x){
                rightGrip();
            }
            if (gamepad.b){
                rightUnGrip();
            }

            if (gamepad.dpad_down){
                zeroArms();
            }
        }
    }

}

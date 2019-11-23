package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Class created to control the servos on the block arms
 * @author Vishy Krishnamurthy, Troy Lopez
 */
public class ServoArmController {

    //Left block arm
    private Servo leftBlockArm;
    private Servo leftBlockGrip;

    //Right Block Arm
    private Servo rightBlockArm;
    private Servo rightBlockGrip;

    /**
     * The constructor for a servo arm controller.
     * @param hardwareMap The HardwareMap configuration on the phones to get the ports for all 4 arm servos.
     */
    public ServoArmController(HardwareMap hardwareMap){
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
     * Move Both Arms Down
     */
    public void moveDown(){
        leftBlockArm.setPosition(0.4);
        rightBlockArm.setPosition(1);
    }

    /**
     * Retract Both Arms
      */
    public void moveUp() {
        leftBlockArm.setPosition(0);
        rightBlockArm.setPosition(0);
    }

    /**
     * Close the grabber on both arms
     */
    public void grip() {
        leftBlockGrip.setPosition(0.9);
        rightBlockGrip.setPosition(0.9);
    }

    /**
     * Release the grip on the block on both arms
     */
    public void unGrip() {
        leftBlockGrip.setPosition(0.1);
        rightBlockGrip.setPosition(0.1);
    }

    public double getRightArm(){
        return rightBlockArm.getPosition();
    }



}

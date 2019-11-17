package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Class created to control the servos on the block arms
 * @author Vishy Krishnamurthy
 */
public class ServoArm {

    //Left block arm
    private Servo leftBlockArm;
    private Servo leftBlockGrip;

    //Right Block Arm
    private Servo rightBlockArm;
    private Servo rightBlockGrip;

    public ServoArm(HardwareMap hardwareMap){
        leftBlockArm = hardwareMap.get(Servo.class,"leftBlockArm");
        leftBlockGrip = hardwareMap.get(Servo.class, "leftBlockGrip");

        rightBlockArm = hardwareMap.get(Servo.class,"rightBlockArm");
        rightBlockGrip = hardwareMap.get(Servo.class, "rightBlockGrip");
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
        rightBlockArm.setPosition(0.4);
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
        leftBlockGrip.setPosition(1);
        rightBlockGrip.setPosition(1);
    }

    /**
     * Release the grip on the block on both arms
     */
    public void unGrip() {
        leftBlockGrip.setPosition(0);
        rightBlockGrip.setPosition(0);
    }
}

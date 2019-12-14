package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Utilities.ArmDirection;


/**
 * Class created to control the servos on the block arms
 * @author Vishy Krishnamurthy
 */
public class ServoArmController {

    //Left block arm
    private Servo LeftBlockArm;
    private Servo LeftBlockGrip;

    private Gamepad gamepad;

    //Right Block Arm
    private Servo rightBlockArm;
    private Servo rightBlockGrip;

    /**
     * The constructor for a servo arm controller.
     * @param hardwareMap The HardwareMap configuration on the phones to get the ports for all 4 arm servos.
     */
    public ServoArmController(HardwareMap hardwareMap, Gamepad gamepad){
        this.gamepad = gamepad;

        LeftBlockArm = hardwareMap.get(Servo.class,"LeftBlockArm");
        LeftBlockGrip = hardwareMap.get(Servo.class, "LeftBlockGrip");

        /*
        rightBlockArm = hardwareMap.get(Servo.class,"rightBlockArm");
        rightBlockGrip = hardwareMap.get(Servo.class, "rightBlockGrip");
         */

        //rightBlockArm.setDirection(Servo.Direction.REVERSE);
        //rightBlockGrip.setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Called to set the arm positions to zero at start
     */
    public void zeroArms(){
        LeftBlockArm.setPosition(0);
        //rightBlockArm.setPosition(0);
    }

    /**
     * Move Both Arms Down
     */
    public void moveDown(){
        LeftBlockArm.setPosition(0.4);
        //rightBlockArm.setPosition(1);
    }

    /**
     * Retract Both Arms
      */
    private void moveUp() {
        LeftBlockArm.setPosition(0);
        //rightBlockArm.setPosition(0);
    }

    /**
     * Close the grabber on both arms
     */
    private void grip() {
        LeftBlockGrip.setPosition(1);
        //rightBlockGrip.setPosition(0.9);
    }

    /**
     * Release the grip on the block on both arms
     */
    private void unGrip() {
        LeftBlockGrip.setPosition(0.1);
        //rightBlockGrip.setPosition(0.1);
    }

    /**
     * General method to control the block arms
     */
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

    /**
     * A method to control the stone servo arms during the autonomous period using the enum ArmDirection.
     * FIXME: Rename ArmDirection enum, as gripping and ungripping aren't really directions
     * @param direction The action you want to do with the servo arms.
     */
    public void controlArmsAutonomous(ArmDirection direction) {
        switch(direction) {
            case DOWN:
                moveDown();
                break;

            case UP:
                moveUp();
                break;

            case GRIP:
                grip();
                break;

            case UNGRIP:
                unGrip();
                break;
        }
    }



}

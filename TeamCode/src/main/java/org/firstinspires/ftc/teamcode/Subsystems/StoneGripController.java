package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.GripArmPosition;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.MoveArmDirection;

/**
 * Code written for controlling the elevator arm
 * @author Troy Lopez, Zane Othman-Gomez
 */
public class StoneGripController {
    // TODO: Update configuration with more descriptive names
    // Right (elevator) arm servos
    private CRServo svElevator;
    private CRServo svRightPivot;
    private Servo svRightGrip;

    // Left (fixed) arm servos
    private CRServo svLeftPivot;
    private Servo svLeftGrip;

    // Gamepad for controlling the stone-gripping arms
    private Gamepad gamepad;

    // The arm that is currently being controlled.
    private GripArmPosition armPos = GripArmPosition.RIGHT;

    // The telemetry for debugging purposes
    private Telemetry telemetry;

    /**
     * The constructor for a StoneGripController instance.
     * @param hardwareMap The hardwareMap from the configuration on the phones.
     * @param gamepad The gamepad for use in controlling the two arms.
     * @param telemetry The telemetry used on the phones for debugging purposes.
     */
    public StoneGripController(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry){
        this.gamepad = gamepad;
        this.telemetry = telemetry;

        svElevator = hardwareMap.get(CRServo.class, "svElevator");
        svRightPivot = hardwareMap.get(CRServo.class, "svPivot");
        svRightGrip = hardwareMap.get(Servo.class, "svGrip");
        //svLeftGrip = hardwareMap.get(Servo.class, "svLeftGrip");

        svLeftPivot = hardwareMap.get(CRServo.class,"LeftBlockArm");
        svLeftGrip = hardwareMap.get(Servo.class, "LeftBlockGrip");

    }

    // region Autonomous Control Methods
    /**
     * A way to pivot the grip arms on the robot autonomously.
     * @param arm The side on which the arm is on, given by the enum GripArmPosition.
     * @param direction The direction that you want to move the arm.
     */
    public void autoPivot(GripArmPosition arm, MoveArmDirection direction) {
        // TODO: Verify power signs
        // Check which arm to move
        if (arm == GripArmPosition.LEFT) {
            // Check which direction to move the left arm
            if (direction == MoveArmDirection.UP) {
                svLeftPivot.setPower(0.5);
            } else if (direction == MoveArmDirection.DOWN) {
                svLeftPivot.setPower(-0.5);
            }

            // Wait for 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Stop the servo
            svLeftPivot.setPower(0);
        } else if (arm == GripArmPosition.RIGHT) {
            // Check which direction to move the left arm
            if (direction == MoveArmDirection.UP) {
                svRightPivot.setPower(0.5);
            } else if (direction == MoveArmDirection.DOWN) {
                svRightPivot.setPower(-0.5);
            }

            // Wait for 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Stop the servo
       }
    }
    // endregion


    /**
     * Used for control in TeleOp via a single method.
     */
    public void controlArms() {
        // Depending on which arm is currently being controlled, use its corresponding servos.
        switch(armPos) {
            case LEFT:
                pivotLeftArm(svLeftPivot);

                if (gamepad.a) {
                    gripArm(svLeftGrip);
                } else if (gamepad.y) {
                    ungripArm(svLeftGrip);
                }

                if (gamepad.start) {
                    armPos = GripArmPosition.RIGHT;
                }


                telemetry.addData("Current Arm: ", "Left");

                break;

            default:
            case RIGHT:
                pivotRightArm(svRightPivot);

                if (gamepad.a) {
                    gripArm(svRightGrip);
                } else if (gamepad.y) {
                    ungripArm(svRightGrip);
                }

                if (gamepad.back) {
                    armPos = GripArmPosition.LEFT;
                }

                rightElevatorControl(gamepad.left_stick_y);

                telemetry.addData("Current Arm: ", "Right");

                // TODO: Add logic for moving the elevator arm up and down.

                break;
        }

        // region Old Logic

        /*
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
         */

        // endregion
    }

    // region Arm Movement Helpers

    /**
     * A more descriptive convenience method to control the servo that controls the height of the right arm.
     * @param power The power that you would like to set for the elevator CR servo.
     */
    private void rightElevatorControl(double power) {
        svElevator.setPower(power);
    }

    /**
     * A method used for pivoting the elevator grip arm.
     */
    public void pivotLeftArm(CRServo svLeftPivot) {
        svLeftPivot.setPower(-1 * gamepad.right_stick_y);
    }

    public void pivotRightArm(CRServo svRightPivot) {
        svRightPivot.setPower(gamepad.right_stick_y);
    }

    /**
     * Closes the grip on a given arm.
     * @param gripServo The servo on the arm that needs to be gripped.
     */
    public void gripArm(Servo gripServo) {
        double currentPos = gripServo.getPosition();

        if (currentPos < 0.5){
            gripServo.setPosition(currentPos + 0.38);
        } else {
            gripServo.setPosition(1);
        }
    }

    /**
     * Opens the grip on the elevator arm.
     * @param gripServo The servo on the arm that needs to be ungripped.
     */
    public void ungripArm(Servo gripServo) {
        double currentPos = gripServo.getPosition();

        if (currentPos > 0.5) {
            gripServo.setPosition(currentPos - 0.38);
        } else {
            gripServo.setPosition(0);
        }
    }

    /**
     * Moves the elevator arm up and down via the left stick.
     * Since this only moves the right arm, it doesn't need a servo parameter
     */
    /*
    private void elevateRightArm() {
        svElevator.setPower(gamepad.left_stick_y);
    }
     */

    // endregion

    // region Old Control Methods

    /*
     * Pivots both arms back inside the robot.
     */
    /*
    public void zeroArms(){
        // FIXME: svLeftPivot is a regular servo when it should be a CR servo.
        svLeftPivot.setPosition(0);
        svRightPivot.setPosition(0);
    }
     */

    /*
    public void moveDown(){
        LeftBlockArm.setPosition(0.4);
        //rightBlockArm.setPosition(1);
    }


    private void moveUp() {
        LeftBlockArm.setPosition(0);
        //rightBlockArm.setPosition(0);
    }


    private void grip() {
        LeftBlockGrip.setPosition(1);
        //rightBlockGrip.setPosition(0.9);
    }


    private void unGrip() {
        LeftBlockGrip.setPosition(0);
        //rightBlockGrip.setPosition(0.1);
    }
     */
    // endregion
}
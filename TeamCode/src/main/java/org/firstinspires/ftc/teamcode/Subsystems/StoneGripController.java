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
     * @param duration How long to wait. A good starting value is 1 second (1000ms).
     */
    public void autoPivot(GripArmPosition arm, MoveArmDirection direction, long duration) {
        // TODO: Verify power signs
        // Check which arm to move
        if (arm == GripArmPosition.LEFT) {
            // Check which direction to move the left arm
            if (direction == MoveArmDirection.UP) {
                svLeftPivot.setPower(0.5);
            } else if (direction == MoveArmDirection.DOWN) {
                svLeftPivot.setPower(-0.5);
            }

            sleep(duration);

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
            sleep(duration);

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
            /*
            case LEFT:
                pivotArm(svLeftPivot, 1);
                svLeftPivot.setPower(0.5);
                if (gamepad.a) {
                    gripArm(svLeftGrip);
                } else if (gamepad.y) {
                    ungripArm(svLeftGrip);
                }
                if (gamepad.start) {
                    armPos = GripArmPosition.RIGHT;
                }

               */

                //telemetry.addData("Current Arm: ", "Left");

                //break;

            default:
            case RIGHT:
                pivotArm(svRightPivot, 1);

                if (gamepad.a) {
                    gripArm(svRightGrip);
                } else if (gamepad.y) {
                    ungripArm(svRightGrip);
                }
                /*
                if (gamepad.back) {
                    armPos = GripArmPosition.LEFT;
                }
                */
                rightElevatorControl(gamepad.left_stick_y);

                telemetry.addData("Current Arm: ", "Right");

                break;

        }
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
     * @param pivotServo The servo to manipulate.
     * @param powerMultiplier What to multiply the power by in order to set a speed limit
     *                        or reverse the power, if necessary.
     */
    public void pivotArm(CRServo pivotServo, double powerMultiplier) {
        pivotServo.setPower(gamepad.right_stick_y * powerMultiplier);
    }

    /**
     * A convenience method for waiting for a certain amount of time.
     * @param duration How long to sleep for.
     */
    public void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Closes the grip on a given arm.
     * @param gripServo The servo on the arm that needs to be gripped.
     */
    public void gripArm(Servo gripServo) {
        gripServo.setPosition(1);
    }

    /**
     * An overload of gripArm for autonomous use.
     * @param arm The arm to grip.
     */
    public void gripArm(GripArmPosition arm) {
        switch (arm) {
            case LEFT:
                svLeftGrip.setPosition(1);
                break;
            case RIGHT:
                svRightGrip.setPosition(-1);
                break;
        }
    }

    /**
     * Opens the grip on the elevator arm.
     * @param gripServo The servo on the arm that needs to be ungripped.
     */
    public void ungripArm(Servo gripServo) {
        gripServo.setPosition(0);
    }

    /**
     * An overload of ungripArm for autonomous use.
     * @param arm The arm to ungrip.
     */
    public void ungripArm(GripArmPosition arm) {
        switch (arm) {
            case LEFT:
                svLeftGrip.setPosition(0);
                break;
            case RIGHT:
                svRightGrip.setPosition(0);
                break;
        }
    }

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
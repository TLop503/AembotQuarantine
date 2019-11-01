package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Swerve.Enums.ModulePosition;
import org.firstinspires.ftc.teamcode.Swerve.Enums.WheelDirection;
import org.firstinspires.ftc.teamcode.Utilities.Control.PID;

/**
 * Class used to control an individual swerve module
 * @author Will Richards
 */
public class SwerveModule {

    //Holds the assigned module position be it left or right
    private ModulePosition modPos;

    //Gets A reference to the hardwareMap so motors can be mapped, aswell as a reference to the gamepad
    private HardwareMap hardwareMap;
    private Gamepad gamepad1;

    //Gets references to the top and bottom motors
    private DcMotor TopSwerveMotor;
    private DcMotor BottomSwerveMotor;

    //Module control variables are created up here to not destroy the garbage collector
    private double currentRotation = 0;
    private double wantedRotation = 0;
    private WheelDirection wheelDirection = WheelDirection.STATIC;

    //P = 3 or 5, or 8
    private final double P = 5, I = 0, D = 0;

    //Reference to the PID class that allows us to use PID
    private PID PIDController;

    //Power given to motors
    private double power;

    /**
     * Constructs each variable as well as determining the module side so it can properly assign motors
     * @param modPos which side the module is located on
     * @param hardwareMap the mapping of names to ports
     * @param gamepad1 the controller input
     */
    public SwerveModule(ModulePosition modPos, HardwareMap hardwareMap, Gamepad gamepad1){

        /*
         * Assign parameters to class variables
         */
        this.modPos = modPos;
        this.hardwareMap = hardwareMap;
        this.gamepad1 = gamepad1;

        /*
         * This block of code will determine which side the module is on based on the enum that was set in the SwerveController class
         */

        switch(modPos) {
            case RIGHT:
                TopSwerveMotor = hardwareMap.get(DcMotor.class, "RightTopSwerveMotor");
                BottomSwerveMotor = hardwareMap.get(DcMotor.class, "RightBottomSwerveMotor");
                break;
            case LEFT:
                TopSwerveMotor = hardwareMap.get(DcMotor.class, "LeftTopSwerveMotor");
                BottomSwerveMotor = hardwareMap.get(DcMotor.class, "LeftBottomSwerveMotor");
                break;
        }

        /*
         * Creates a new PIDController and passes P, I and D into it.
         * After that it then sets the acceptable range which is the range +/- that it qualifies as at the right spot
         * And finally it sets the point the module is trying to reach, at this point we just set it to 0
         */
        PIDController = new PID(P,I,D);
        PIDController.setAcceptableRange(0.02);
        PIDController.setSetpoint(0);
    }


    /**
     * Method Uses The PID Controller / PID class to move the module to the right position and then once there will allow it to spin
     */
    public void PIDControl(){

        /*
         * Collect information to be used in module control / PID
         * currentRotation - The number of rotations he module has currently completed
         * wantedRotation - The normalized top hemisphere value of the joystick converted into rotations
         * wheelDirection - The direction the wheel should spin based off the Y axis of the joystick
         */
        currentRotation = SwerveMath.getModulePosition(TopSwerveMotor.getCurrentPosition(), BottomSwerveMotor.getCurrentPosition());
        wantedRotation = SwerveMath.normalizeJoystickAngle(gamepad1);
        wheelDirection = SwerveMath.getWheelDirection(gamepad1);

        /*
         * This small section simply updates the point that it wants to reach based off the new wantedRotation
         * And then the method calcOutput is called which you pass your current value into and it preforms the PID opperation and returns the output with your scalars
         */
        PIDController.setSetpoint(wantedRotation);
        power = PIDController.calcOutput(currentRotation);

        /*
         * This is a chunk of code specific to swerve, however, the PID class is universally accessible
         * It first asks the PID controller if it is in the acceptable range, If so it will stop the motors so they are not trying to get to a value that is impossible with the current scalars
         * Next if the driver wants to accelerate this can only be done once it has aligned  to the right angle
         * However if it is not in rage simply add the calculated motor power to the motors
         */
        if(PIDController.isInRange()){
            if(gamepad1.right_trigger > 0.1){
                if(modPos == ModulePosition.RIGHT) {
                    if (wheelDirection == WheelDirection.FORWARD) {
                        TopSwerveMotor.setPower(1);
                        BottomSwerveMotor.setPower(-1);
                    } else if (wheelDirection == WheelDirection.BACKWARD) {
                        TopSwerveMotor.setPower(-1);
                        BottomSwerveMotor.setPower(1);
                    } else {
                        TopSwerveMotor.setPower(1);
                        BottomSwerveMotor.setPower(-1);
                    }
                }
                else{
                    if (wheelDirection == WheelDirection.FORWARD) {
                        TopSwerveMotor.setPower(-1);
                        BottomSwerveMotor.setPower(1);
                    } else if (wheelDirection == WheelDirection.BACKWARD) {
                        TopSwerveMotor.setPower(1);
                        BottomSwerveMotor.setPower(-1);
                    } else {
                        TopSwerveMotor.setPower(-1);
                        BottomSwerveMotor.setPower(1);
                    }
                }

            }
            else {
                TopSwerveMotor.setPower(0);
                BottomSwerveMotor.setPower(0);
            }
        }
        else{
            TopSwerveMotor.setPower(power);
            BottomSwerveMotor.setPower(power);
        }


    }

    /**
     * Overloaded PIDControl used for autonomous control
     * This method works in such a way that since it may take multiple loops of the program to align each time through this method will return true or false
     * If the module is oriented right it will return true and vice versa, this means that you will have to check this every iteration through the program and ignore it if the action has completed
     * @return the status of the completeness of the control
     */
    public boolean PIDControl(double angle){

        /*
         * Collect information to be used in module control / PID
         * currentRotation - The number of rotations he module has currently completed
         * wantedRotation - The normalized values of the angle
         * wheelDirection - The direction the wheel should spin based off the Y axis of the joystick
         */
        currentRotation = SwerveMath.getModulePosition(TopSwerveMotor.getCurrentPosition(), BottomSwerveMotor.getCurrentPosition());
        wantedRotation = SwerveMath.normalizeAngle(angle);
        wheelDirection = SwerveMath.getWheelDirection(angle);

        /*
         * This small section simply updates the point that it wants to reach based off the new wantedRotation
         * And then the method calcOutput is called which you pass your current value into and it preforms the PID opperation and returns the output with your scalars
         */
        PIDController.setSetpoint(wantedRotation);
        power = PIDController.calcOutput(currentRotation);

        /*
         * This is what the module will do once it has reached the wanted position
         */
        if(PIDController.isInRange()){
            //Returns true to exit the method
            return true;
        }
        else{
            TopSwerveMotor.setPower(power);
            BottomSwerveMotor.setPower(power);
        }
        return false;
    }

    /**
     * This method works similarly to the last except it will just run the motors in a direction that is wanted at a certain speed
     * @param wheelDirection the direction that the wheel is meant to move
     * @param motorSpeed the speed you want the motors to run, run directon is determined from that
     * @return action completeness
     */
    public void runMotorsDumb(WheelDirection wheelDirection, double motorSpeed){
        if(modPos == ModulePosition.RIGHT) {
            if (wheelDirection == WheelDirection.FORWARD) {
                TopSwerveMotor.setPower(motorSpeed);
                BottomSwerveMotor.setPower(-motorSpeed);
            } else if (wheelDirection == WheelDirection.BACKWARD) {
                TopSwerveMotor.setPower(-motorSpeed);
                BottomSwerveMotor.setPower(motorSpeed);
            } else {
                TopSwerveMotor.setPower(motorSpeed);
                BottomSwerveMotor.setPower(-motorSpeed);
            }
        }
        else{
            if (wheelDirection == WheelDirection.FORWARD) {
                TopSwerveMotor.setPower(-motorSpeed);
                BottomSwerveMotor.setPower(motorSpeed);
            } else if (wheelDirection == WheelDirection.BACKWARD) {
                TopSwerveMotor.setPower(motorSpeed);
                BottomSwerveMotor.setPower(-motorSpeed);
            } else {
                TopSwerveMotor.setPower(-motorSpeed);
                BottomSwerveMotor.setPower(motorSpeed);
            }
        }
    }
}

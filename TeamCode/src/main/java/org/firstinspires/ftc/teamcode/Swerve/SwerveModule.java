package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Swerve.Enums.ModulePosition;
import org.firstinspires.ftc.teamcode.Swerve.Enums.WheelDirection;
import org.firstinspires.ftc.teamcode.Utilities.Control.PID;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.IMU;

/**
 * Class used to control an individual swerve module
 * @author Will Richards, Zane Othman-Gomez
 */
public class SwerveModule {

    //Holds the assigned module position be it left or right
    private ModulePosition modPos;

    //Gets A reference to the hardwareMap so motors can be mapped, aswell as a reference to the gamepad
    private HardwareMap hardwareMap;
    private Gamepad gamepad1;

    private Telemetry telemetry;

    //Gets references to the top and bottom motors
    private DcMotor TopSwerveMotor;
    private DcMotor BottomSwerveMotor;

    //The switch that helps to zero the module
    private DigitalChannel ZeroSwitch;

    //Determines the offset to apply to the tickCounts
    private int topMotorTickOffset = 0;
    private int bottomMotorTickOffset = 0;

    private boolean hasResetEncoders = false;

    //Module control variables are created up here to not destroy the garbage collector
    private double currentRotation = 0;
    private double wantedRotation = 0;
    private WheelDirection wheelDirection = WheelDirection.STATIC;

    private double currentDistanceRot = 0;
    private double wantedDistanceRot = 0;

    //P = 3 or 5, or 8
    private final double P = 5.2, I = 0, D = 0;

    //Reference to the PID class that allows us to use PID
    private PID TeleOpPID;

    //Both deal with fine control of the swerve modules in autonomous
    private PID turnPID;
    private PID drivePID;

    //Power given to motors, in TeleOP
    private double power;

    //Power that will be set from the turnPID and drivePID commands respectively
    private double turnPower;
    private double drivePower;

    /**
     * Constructs each variable as well as determining the module side so it can properly assign motors
     * @param modPos which side the module is located on
     * @param hardwareMap the mapping of names to ports
     * @param gamepad1 the controller input
     */
    public SwerveModule(ModulePosition modPos, HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry){

        this.telemetry = telemetry;

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
                ZeroSwitch = hardwareMap.get(DigitalChannel.class, "RightMagSwitch");
                break;
            case LEFT:
                TopSwerveMotor = hardwareMap.get(DcMotor.class, "LeftTopSwerveMotor");
                BottomSwerveMotor = hardwareMap.get(DcMotor.class, "LeftBottomSwerveMotor");
                ZeroSwitch = hardwareMap.get(DigitalChannel.class, "LeftMagSwitch");
                break;
        }

        /*
         * Creates a new TeleOpPID and passes P, I and D into it.
         * After that it then sets the acceptable range which is the range +/- that it qualifies as at the right spot
         * And finally it sets the point the module is trying to reach, at this point we just set it to 0
         */
        TeleOpPID = new PID(P,I,D);
        TeleOpPID.setAcceptableRange(0.02);
        TeleOpPID.setSetpoint(0);

        /*
         * Create a new turnPID to specifically be used for autonomous turning of the robot
         */
        turnPID = new PID(P,I,D);
        turnPID.setAcceptableRange(0.02);
        turnPID.setSetpoint(0);

        /*
         * Create a new drivePID to be used for autonomous driving of the modules
         * TODO: Tune values
         */
        drivePID = new PID(0,0,0);
        drivePID.setAcceptableRange(0.02);
        drivePID.setSetpoint(0);
    }

    //region Teleop Module Control
    /**
     * Method Uses The PID Controller / PID class to move the module to the right position and then once there will allow it to spin, robot centric
     */
    public void PIDControl() {

        //When the sensor is crossed reset the encoders
        //TODO: If that doesnt work, then use the more complex one
        //if(ZeroSwitch.getState()){
          //  resetTopEncoder();
           // resetBottomEncoder();
        //}


        double motorSpeed = 0.7;

        telemetry.addData(modPos + " Top Encoder: ", getTopMotorTicks());
        telemetry.addData(modPos + " Bottom Encoder: ", getBottomMotorTicks());



        /*
         * Collect information to be used in module control / PID
         * currentRotation - The number of rotations he module has currently completed
         * wantedRotation - The normalized top hemisphere value of the joystick converted into rotations
         * wheelDirection - The direction the wheel should spin based off the Y axis of the joystick
         */
        currentRotation = SwerveMath.getModulePosition(getTopMotorTicks(),getBottomMotorTicks());
        wantedRotation = SwerveMath.normalizeJoystickAngle(gamepad1);
        wheelDirection = SwerveMath.getWheelDirection(gamepad1);

        /*
         * This small section simply updates the point that it wants to reach based off the new wantedRotation
         * And then the method calcOutput is called which you pass your current value into and it preforms the PID opperation and returns the output with your scalars
         */
        TeleOpPID.setSetpoint(wantedRotation);
        power = TeleOpPID.calcOutput(currentRotation);

        /*
         * This is a chunk of code specific to swerve, however, the PID class is universally accessible
         * It first asks the PID controller if it is in the acceptable range, If so it will stop the motors so they are not trying to get to a value that is impossible with the current scalars
         * Next if the driver wants to accelerate this can only be done once it has aligned  to the right angle
         * However if it is not in rage simply add the calculated motor power to the motors
         */
        if(TeleOpPID.isInRange()){
            if(gamepad1.right_trigger > 0.1){
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
                else {
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

            /*
             * Allows the robot to turn when the bumpers are pressed
             */
            else if(gamepad1.right_bumper) {
                TopSwerveMotor.setPower(-motorSpeed);
                BottomSwerveMotor.setPower(motorSpeed);
            }
            else if(gamepad1.left_bumper) {
                TopSwerveMotor.setPower(motorSpeed);
                BottomSwerveMotor.setPower(-motorSpeed);
            }
            else {

                if(wantedRotation == 0){
                    if(ZeroSwitch.getState()) {
                        TopSwerveMotor.setPower(0.2);
                        BottomSwerveMotor.setPower(0.2);
                    }
                    else{
                        resetBottomEncoder();
                        resetTopEncoder();
                        TopSwerveMotor.setPower(0);
                        BottomSwerveMotor.setPower(0);
                    }
                }
            }
        }
        else{

                if(wantedRotation == 0){
                    if(ZeroSwitch.getState()) {
                        TopSwerveMotor.setPower(0.2);
                        BottomSwerveMotor.setPower(0.2);
                    }
                    else{
                        resetBottomEncoder();
                        resetTopEncoder();
                        TopSwerveMotor.setPower(0);
                        BottomSwerveMotor.setPower(0);
                    }
                }
                else{
                    TopSwerveMotor.setPower(power);
                    BottomSwerveMotor.setPower(power);
                }
        }


    }

    public boolean getSwitchStatus(){
        return ZeroSwitch.getState();
    }

    /**
     * Actively zero the modules
     * @return weather or not its zeroed
     */
    public boolean activeZeroModules(){
        if(ZeroSwitch.getState()) {
            TopSwerveMotor.setPower(0.2);
            BottomSwerveMotor.setPower(0.2);
        }
        else{
            resetBottomEncoder();
            resetTopEncoder();
            TopSwerveMotor.setPower(0);
            BottomSwerveMotor.setPower(0);
            return true;
        }
        return false;
    }

    /**
     * Method Uses The PID Controller / PID class to move the module to the right position and then once there will allow it to spin, field centric
     */
    public void PIDControl(IMU imu){


        double motorSpeed = 0.7;

        telemetry.addData(modPos + " Top Encoder: ", getTopMotorTicks());
        telemetry.addData(modPos + " Bottom Encoder: ", getBottomMotorTicks());



        /*
         * Collect information to be used in module control / PID
         * currentRotation - The number of rotations he module has currently completed
         * wantedRotation - The normalized top hemisphere value of the joystick converted into rotations
         * wheelDirection - The direction the wheel should spin based off the Y axis of the joystick
         */
        currentRotation = SwerveMath.getModulePosition(getTopMotorTicks(), getBottomMotorTicks());
        wantedRotation = SwerveMath.normalizeJoystickAngle(gamepad1, imu);
        wheelDirection = SwerveMath.getWheelDirection(gamepad1);

        /*
         * This small section simply updates the point that it wants to reach based off the new wantedRotation
         * And then the method calcOutput is called which you pass your current value into and it preforms the PID operation and returns the output with your scalars
         */
        TeleOpPID.setSetpoint(wantedRotation);
        power = TeleOpPID.calcOutput(currentRotation);

        /*
         * This is a chunk of code specific to swerve, however, the PID class is universally accessible
         * It first asks the PID controller if it is in the acceptable range, If so it will stop the motors so they are not trying to get to a value that is impossible with the current scalars
         * Next if the driver wants to accelerate this can only be done once it has aligned  to the right angle
         * However if it is not in rage simply add the calculated motor power to the motors
         */
        if(TeleOpPID.isInRange()){
            if(gamepad1.right_trigger > 0.1){
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

    //endregion

    //region Autonomous Module Control
    /**
     * Overloaded AutoPIDControl used for autonomous control
     * This method works in such a way that since it may take multiple loops of the program to align each time through this method will return true or false
     * If the module is oriented right it will return true and vice versa, this means that you will have to check this every iteration through the program and ignore it if the action has completed
     * @return the status of the completeness of the control
     */
    public boolean AutoPIDControl(double angle, double distance, double maxMotorSpeed){

        /*
         * Collect information to be used in module control / PID
         * currentRotation - The number of rotations he module has currently completed
         * wantedRotation - The normalized values of the angle
         * wheelDirection - The direction the wheel should spin based off the angle passed as a parameter
         */
        currentRotation = SwerveMath.getModulePosition(getTopMotorTicks(), getBottomMotorTicks());
        wantedRotation = SwerveMath.normalizeAngle(angle);
        wheelDirection = SwerveMath.getWheelDirection(angle);

        /*
         * Calculate the distances needed to rotate offset from the current
         * currentDistanceRot - The current # of rotations already completed
         * wantedDistanceRot - The new distance value we want to reach
         */
        currentDistanceRot = SwerveMath.getWheelPosition(getTopMotorTicks(), getBottomMotorTicks());
        wantedDistanceRot = SwerveMath.calculateWheelPosition(distance, currentDistanceRot);

        /*
         * This small section simply updates the point that it wants to reach based off the new wantedRotation
         * And then the method calcOutput is called which you pass your current value into and it preforms the PID opperation and returns the output with your scalars
         */
        turnPID.setSetpoint(wantedRotation);
        turnPower = turnPID.calcOutput(currentRotation);

        /*
         * This section sets the position we want to reach with the wheels and the maxMotorSpeed
         */
        drivePID.setSetpoint(wantedDistanceRot);
        drivePID.setMaxOutput(maxMotorSpeed);
        drivePower = drivePID.calcOutput(currentDistanceRot);


        /*
         * Runs the motors to a given position and then stops
         */
        if(turnPID.isInRange()){

            //Checks to see if the drive loop is not at the right spot and if not then keep trying if it is then return the command as true to stop this loop
            if(!drivePID.isInRange()) {
                if (modPos == ModulePosition.RIGHT) {
                    if (wheelDirection == WheelDirection.FORWARD) {
                        TopSwerveMotor.setPower(drivePower);
                        BottomSwerveMotor.setPower(-drivePower);
                    } else if (wheelDirection == WheelDirection.BACKWARD) {
                        TopSwerveMotor.setPower(-drivePower);
                        BottomSwerveMotor.setPower(drivePower);
                    } else {
                        TopSwerveMotor.setPower(drivePower);
                        BottomSwerveMotor.setPower(-drivePower);
                    }
                } else {
                    if (wheelDirection == WheelDirection.FORWARD) {
                        TopSwerveMotor.setPower(-drivePower);
                        BottomSwerveMotor.setPower(drivePower);
                    } else if (wheelDirection == WheelDirection.BACKWARD) {
                        TopSwerveMotor.setPower(drivePower);
                        BottomSwerveMotor.setPower(-drivePower);
                    } else {
                        TopSwerveMotor.setPower(-drivePower);
                        BottomSwerveMotor.setPower(drivePower);
                    }
                }
            }

            //If it is in range return true
            else {
                return true;
            }
        }

        //If it hasn't reached the turn set point yet keep turning
        else {
            TopSwerveMotor.setPower(turnPower);
            BottomSwerveMotor.setPower(turnPower);
        }

        //Hasn't reached the right point yet
        return false;
    }

    /**
     * Method for running module motors, albeit with scaled power.
     * This allows the power of the motors to be tuned according to external factors,
     * like other PID loops or sensor inputs.
     * @param angle The angle to run the module at.
     * @param scaleFactor How much to scale the power based on external factors.
     */
    public void PIDControlScaled(double angle, double scaleFactor){

        /*
         * Collect information to be used in module control / PID
         * currentRotation - The number of rotations he module has currently completed
         * wantedRotation - The normalized values of the angle
         * wheelDirection - The direction the wheel should spin based off the angle passed as a parameter
         */
        currentRotation = SwerveMath.getModulePosition(getTopMotorTicks(), getBottomMotorTicks());
        wantedRotation = SwerveMath.normalizeAngle(angle);
        wheelDirection = SwerveMath.getWheelDirection(angle);

        /*
         * This small section simply updates the point that it wants to reach based off the new wantedRotation
         * And then the method calcOutput is called which you pass your current value into and it preforms the PID operation and returns the output with your scalars
         */
        TeleOpPID.setSetpoint(wantedRotation);
        power = TeleOpPID.calcOutput(currentRotation);

        /*
         * This is what the module will do once it has reached the wanted position
         */

        if(TeleOpPID.isInRange()){
            if(modPos == ModulePosition.RIGHT) {
                if (wheelDirection == WheelDirection.FORWARD) {
                    TopSwerveMotor.setPower(1 * scaleFactor);
                    BottomSwerveMotor.setPower(-1 * scaleFactor);
                } else if (wheelDirection == WheelDirection.BACKWARD) {
                    TopSwerveMotor.setPower(-1 * scaleFactor);
                    BottomSwerveMotor.setPower(1 * scaleFactor);
                } else {
                    TopSwerveMotor.setPower(1 * scaleFactor);
                    BottomSwerveMotor.setPower(-1 * scaleFactor);
                }
            }
            else{
                if (wheelDirection == WheelDirection.FORWARD) {
                    TopSwerveMotor.setPower(-1 * scaleFactor);
                    BottomSwerveMotor.setPower(1 * scaleFactor);
                } else if (wheelDirection == WheelDirection.BACKWARD) {
                    TopSwerveMotor.setPower(1 * scaleFactor);
                    BottomSwerveMotor.setPower(-1 * scaleFactor);
                } else {
                    TopSwerveMotor.setPower(-1 * scaleFactor);
                    BottomSwerveMotor.setPower(1 * scaleFactor);
                }
            }
        }
        else{
            TopSwerveMotor.setPower(power);
            BottomSwerveMotor.setPower(power);
        }
    }

    /**
     * This method works similarly to the last except it will just run the motors in a direction that is wanted at a certain speed
     * @param wheelDirection the direction that the wheel is meant to move
     * @param motorSpeed the speed you want the motors to run, run directon is determined from that
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

    /**
     * This method works similarly to the last except it will just run the motors in a direction that is wanted at a certain speed
     * @param angle The angle at which you want the wheels to run
     * @param motorSpeed the speed you want the motors to run, run directon is determined from that
     */
    public void runMotorsDumbWithAngle(double angle, double motorSpeed){
        // Determine the wheel direction based on the angle parameter
        wheelDirection = SwerveMath.getWheelDirection(angle);

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

    /**
     * Method used to feed changes in angle and power to the modules
     * @param angle the angle the modules need to be facing
     * @param motorSpeed the speed the motors are set to drive at
     */
    public void activeDrive(double angle, double motorSpeed){

        /*
         * Collect information to be used in module control / PID
         * currentRotation - The number of rotations he module has currently completed
         * wantedRotation - The normalized values of the angle
         * wheelDirection - The direction the wheel should spin based off the angle passed as a parameter
         */
        currentRotation = SwerveMath.getModulePosition(getTopMotorTicks(), getBottomMotorTicks());
        wantedRotation = SwerveMath.normalizeAngle(angle);
        wheelDirection = SwerveMath.getWheelDirection(angle);

        turnPID.setSetpoint(wantedRotation);
        turnPower = turnPID.calcOutput(currentRotation);

        /*
         * Runs the motors to a given position and then stops
         */
        if(turnPID.isInRange()) {
            //Checks to see if the drive loop is not at the right spot and if not then keep trying if it is then return the command as true to stop this loop
            if (modPos == ModulePosition.RIGHT) {
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
            } else {
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

        //If it hasn't reached the turn set point yet keep turning
        else {
            TopSwerveMotor.setPower(turnPower);
            BottomSwerveMotor.setPower(turnPower);
        }
    }

    /**
     * Stops both motors in a given swerve module.
     */
    public void stopMotors() {
        TopSwerveMotor.setPower(0);
        BottomSwerveMotor.setPower(0);
    }

    //endregion

    /**
     * Gets the number of ticks including the zeroed value for the top motor
     * @return the zeroed number of ticks
     */
    private int getTopMotorTicks(){
        return TopSwerveMotor.getCurrentPosition()-topMotorTickOffset;
    }

    /**
     * Gets the zeroed number of ticks for the bottom motor
     * @return the zeroed number of ticks
     */
    private int getBottomMotorTicks(){
        return BottomSwerveMotor.getCurrentPosition()-bottomMotorTickOffset;
    }

    /**
     * Pseudo reset the top encoder value
     */
    public void resetTopEncoder(){
        topMotorTickOffset = TopSwerveMotor.getCurrentPosition();
    }

    /**
     * Pseudo reset the bottom encoder value
     */
    public void resetBottomEncoder(){
        bottomMotorTickOffset = BottomSwerveMotor.getCurrentPosition();
    }

}

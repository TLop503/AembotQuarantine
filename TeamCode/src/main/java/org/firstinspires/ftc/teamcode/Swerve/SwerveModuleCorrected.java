package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Swerve.Enums.ModulePosition;
import org.firstinspires.ftc.teamcode.Swerve.Enums.WheelDirection;
import org.firstinspires.ftc.teamcode.Utilities.Constants;
import org.firstinspires.ftc.teamcode.Utilities.Control.PID;

public class SwerveModuleCorrected extends SwerveModule {


    //Holds the assigned module position be it left or right
    private ModulePosition modPos;

    //Gets A reference to the hardwareMap so motors can be mapped, aswell as a reference to the gamepad
    private HardwareMap hardwareMap;
    private Gamepad gamepad1;

    private Telemetry telemetry;

    //Gets references to the top and bottom motors
    private DcMotor TopSwerveMotor;
    private DcMotor BottomSwerveMotor;

    //Magnetic limit switch that is used to zero modules
    private DigitalChannel MagSwitch;

    private double motorSpeedOffset = 0.1;

    //Determines the offset to apply to the tickCounts
    private int topMotorTickOffset = 0;
    private int bottomMotorTickOffset = 0;

    public boolean hasResetEncoders = false;

    //Module control variables are created up here to not destroy the garbage collector
    private double currentRotation = 0;
    private double wantedRotation = 0;
    private WheelDirection wheelDirection = WheelDirection.STATIC;

    //Represents the distances in wheel rotations
    private double currentDistanceRot = 0;
    private double wantedDistanceRot = 0;

    //P = 3 or 5, or 8
    private final double P = Constants.TURN_P, I = Constants.TURN_I, D = Constants.TURN_D;

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
     *
     * @param modPos      which side the module is located on
     * @param hardwareMap the mapping of names to ports
     * @param gamepad1    the controller input
     * @param telemetry
     */
    public SwerveModuleCorrected(ModulePosition modPos, HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry) {
        super(modPos, hardwareMap, gamepad1, telemetry);
    }

    /**
     * Overloaded autoPIDControl used for autonomous control
     * This method works in such a way that since it may take multiple loops of the program to align each time through this method will return true or false
     * If the module is oriented right it will return true and vice versa, this means that you will have to check this every iteration through the program and ignore it if the action has completed
     * @return the status of the completeness of the control
     */
    public boolean autoPIDControl(double angle, double distance, double maxMotorSpeed){
//        if(!hasResetEncoders) {
//            resetBottomEncoder();
//            resetTopEncoder();
//            hasResetEncoders = true;
//        }

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
        wantedDistanceRot = SwerveMath.calculateWheelPosition(distance);

        if (modPos == ModulePosition.LEFT){
            wantedDistanceRot *= -1;
        }


        //wantedDistanceRot = SwerveMath.calculateWheelPosition(distance);

        if(modPos == ModulePosition.RIGHT) {
            telemetry.addData("Right Module Wanted Distance: ", wantedDistanceRot);
            telemetry.addData("Right Module Current Distance: ", currentDistanceRot);
        }
        else{
            telemetry.addData("Left Module Wanted Distance: ", wantedDistanceRot);
            telemetry.addData("Left Module Current Distance: ", currentDistanceRot);
        }

        /*
         * This small section simply updates the point that it  wants to reach based off the new wantedRotation
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

        if(modPos == ModulePosition.RIGHT)
            drivePower *= -1;

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
            else {
                telemetry.addData(modPos == ModulePosition.RIGHT ? "Right Module Finished: " : "Left Module Finished: ", true);
                stopMotors();
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

    // TODO: This method is prone to breaking, so decreasing the other motor's speed in the left module might be something to look into
    /**
     * This method is used to compensate for the difficulties that the left bottom swerve motor
     * seems to have. Since the scale factor is 1.4, this method provides a safe way to multiply
     * by this without providing a power greater than 1.
     * @param oldPower The power to be scaled up.
     * @return The scaled up power.
     */
    private double scaleUpPower(double oldPower) {
        double scaledUpPower = 1.4 * oldPower;

        if (scaledUpPower > 1) {
            return 1;
        } else {
            return scaledUpPower;
        }
    }

    /**
     * This method does the opposite of scaleUpPower, and is only written for understandability,
     * not for safety.
     * @param oldPower The power to be scaled down.
     * @return The result of scaling down the power.
     */
    private double scaleDownPower(double oldPower) {
        return oldPower / 1.4;
    }
}

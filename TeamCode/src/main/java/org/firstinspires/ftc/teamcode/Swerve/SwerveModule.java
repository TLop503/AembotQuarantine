package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsAnalogOpticalDistanceSensor;
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
    private final double P = 10, I = 0, D = 0;

    private PID PIDController;

    private double lastError = 0;
    private double setPoint = 0;

    double p = 0;
    double i = 0;
    double d = 0;

    double output = 0;

    double power = 0;



    /**
     * Constructs each variable as well as determining the module side so it can properly assign motors
     * @param modPos which side the module is located on
     * @param hardwareMap the mapping of names to ports
     * @param gamepad1 the controller input
     */
    public SwerveModule(ModulePosition modPos, HardwareMap hardwareMap, Gamepad gamepad1){
        this.modPos = modPos;
        this.hardwareMap = hardwareMap;
        this.gamepad1 = gamepad1;

        //Determine which motors should be associated with the current module
        if(modPos == ModulePosition.RIGHT){
            TopSwerveMotor = hardwareMap.get(DcMotor.class, "RightTopSwerveMotor");
            BottomSwerveMotor = hardwareMap.get(DcMotor.class, "RightBottomSwerveMotor");
        }
        else{
            TopSwerveMotor = hardwareMap.get(DcMotor.class, "LeftTopSwerveMotor");
            BottomSwerveMotor = hardwareMap.get(DcMotor.class, "LeftBottomSwerveMotor");
        }

        PIDController = new PID(P,I,D);
        PIDController.setAcceptableRange(0.02);
        PIDController.setSetpoint(0);
    }

    /**
     * General method that allows for full control of the module
     */
    public void controlModule(){

        /*
         * Assigns active values to the previously created variables
         * currentRotation - The current "angle" of the entire module
         * wantedRotation - The "angle" we want to reach
         * wheelDirection - The direction we want the wheel to spin based off where the joystick is
         */
        currentRotation = SwerveMath.getModulePosition(TopSwerveMotor.getCurrentPosition(), BottomSwerveMotor.getCurrentPosition());
        wantedRotation = SwerveMath.normalizeJoystickAngle(gamepad1);
        wheelDirection = SwerveMath.getWheelDirection(gamepad1);


        /*
        * Basic PID using an if feed back loop seems to work reasonably well
        * The first if here checks if the module angle is within 0.02 rotations of the the wanted angle
        * If this is the case it is consider in range of being at the right angle
        */
        if(currentRotation > wantedRotation-0.02  && currentRotation < wantedRotation+0.02){

            /*
             * Next, now that we are aligned and in range if the driver wants to move forward or in the modules facing angle
             * However, if they don't the simply stop the motors
             */
            if(gamepad1.right_trigger > 0.1){
                if(wheelDirection == WheelDirection.FORWARD) {
                    TopSwerveMotor.setPower(0.7);
                    BottomSwerveMotor.setPower(-0.7);
                }
                else if(wheelDirection == WheelDirection.BACKWARD){
                    TopSwerveMotor.setPower(-0.7);
                    BottomSwerveMotor.setPower(0.7);
                }
                else{
                    TopSwerveMotor.setPower(0.7);
                    BottomSwerveMotor.setPower(-0.7);
                }
            }
            else {
                TopSwerveMotor.setPower(0);
                BottomSwerveMotor.setPower(0);
            }
            TopSwerveMotor.setPower(0);
            BottomSwerveMotor.setPower(0);
        }

        /*
         * However, if the current angle is less than wanted drive both wheels in a negative direction to get to the right angle
         */
        else if(currentRotation > wantedRotation){
            TopSwerveMotor.setPower(-0.7);
            BottomSwerveMotor.setPower(-0.7);
        }

        /*
         * Similarly, do the opposite here to get the angle
         */
        else{
            BottomSwerveMotor.setPower(0.7);
            TopSwerveMotor.setPower(0.7);
        }

    }

    /**
     * PID Controller inside method
     */
    public void pidControl(){

        currentRotation = SwerveMath.getModulePosition(TopSwerveMotor.getCurrentPosition(), BottomSwerveMotor.getCurrentPosition());
        wantedRotation = SwerveMath.normalizeJoystickAngle(gamepad1);
        wheelDirection = SwerveMath.getWheelDirection(gamepad1);

        setPoint = wantedRotation;

        p = setPoint - currentRotation;
        i += p;
        d = p - lastError;
        lastError = p;

        output = (P * p + I * i + D *d);

        if(currentRotation > wantedRotation-0.02 && currentRotation < wantedRotation+0.02) {
            if(gamepad1.right_trigger > 0.1){
                if(wheelDirection == WheelDirection.FORWARD) {
                    TopSwerveMotor.setPower(0.7);
                    BottomSwerveMotor.setPower(-0.7);
                }
                else if(wheelDirection == WheelDirection.BACKWARD){
                    TopSwerveMotor.setPower(-0.7);
                    BottomSwerveMotor.setPower(0.7);
                }
                else{
                    TopSwerveMotor.setPower(0.7);
                    BottomSwerveMotor.setPower(-0.7);
                }
            }
            else {
                TopSwerveMotor.setPower(0);
                BottomSwerveMotor.setPower(0);
            }
        }
        else{
            TopSwerveMotor.setPower(output);
            BottomSwerveMotor.setPower(output);
        }

    }

    /**
     * Method Uses The PID Controller / PID class
     */
    public void PIDControllerControl(){
        currentRotation = SwerveMath.getModulePosition(TopSwerveMotor.getCurrentPosition(), BottomSwerveMotor.getCurrentPosition());
        wantedRotation = SwerveMath.normalizeJoystickAngle(gamepad1);

        PIDController.setSetpoint(wantedRotation);
        power = PIDController.calcOutput(currentRotation);

        if(PIDController.isInRange()){
            if(gamepad1.right_trigger > 0.1){
                if(wheelDirection == WheelDirection.FORWARD) {
                    TopSwerveMotor.setPower(0.7);
                    BottomSwerveMotor.setPower(-0.7);
                }
                else if(wheelDirection == WheelDirection.BACKWARD){
                    TopSwerveMotor.setPower(-0.7);
                    BottomSwerveMotor.setPower(0.7);
                }
                else{
                    TopSwerveMotor.setPower(0.7);
                    BottomSwerveMotor.setPower(-0.7);
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
}

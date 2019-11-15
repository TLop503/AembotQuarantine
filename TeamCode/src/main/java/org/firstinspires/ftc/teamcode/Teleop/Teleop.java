
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Teleop",  group = "Teleop")
public class Teleop extends OpMode {

    private Servo svTop;
    private Servo svBottom;

    private DcMotor dcDriveL;
    private DcMotor dcDriveR;
    private DcMotor dcElevator;
    private double encoderStop;


    @Override
    public void init() {
        //Hardware Mapping + init phase

        svTop = hardwareMap.get(Servo.class, "svTop");
        svBottom = hardwareMap.get(Servo.class, "svBottom");
        dcDriveL = hardwareMap.get(DcMotor.class, "dcDriveL");
        dcDriveR = hardwareMap.get(DcMotor.class, "dcDriveR");
        dcElevator = hardwareMap.get(DcMotor.class, "dcElevator");

        //svTop.setPosition(.857) && svBottom.setPosition(.857);
    }

    @Override
    public void loop() {

        //Standard tank drive.
        //dcDriveL.setPower(gamepad1.left_stick_y);
        //dcDriveR.setPower(-gamepad1.right_stick_y);

        DifferentialDrive(gamepad1.left_stick_y, gamepad1.right_stick_x, true);

        //Might need to be reversed. Controls elevator
        if (gamepad1.dpad_down && dcElevator.getCurrentPosition() > 0){
            dcElevator.setPower(-.7);
        }
        else if (gamepad1.dpad_up ){
            dcElevator.setPower(.7);
        }
        else{
            dcElevator.setPower(0);
        }
        //Rack and pinion control
        //Out?
        if (gamepad2.dpad_up) {
            svTop.setPosition(.857);
            svBottom.setPosition(.857);
        }
        //In?
        else if (gamepad2.dpad_down) {
            svTop.setPosition(0.286);
            svBottom.setPosition(0.286);
        }

    }

    /**
     * Method used to dynamically allow for driving and turning using 2 sticks
     * @param Speed the input stick for forward and backwards
     * @param Rotation the input of the stick for left and right
     * @param squareInputs weather or not the inputs will be squared
     */
    private void DifferentialDrive(double Speed, double Rotation, boolean squareInputs){
        Speed = Speed*-1;

        Speed = clamp(Speed, -1, 1);
        Speed = applyDeadband(Speed, 0.1);

        Rotation = clamp(Rotation, -1, 1);
        Rotation = applyDeadband(Rotation, 0.1);

        if(squareInputs){
            Speed = Math.copySign(Speed*Speed,Speed);
            Rotation = Math.copySign(Rotation*Rotation, Rotation);
        }

        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(Speed), Math.abs(Rotation)), Speed);

        if(Speed>=0){
            if(Rotation>=0){
                leftMotorOutput = maxInput;
                rightMotorOutput = Speed - Rotation;
            }
            else{
                leftMotorOutput = Speed + Rotation;
                rightMotorOutput = maxInput;
            }
        } else{
            if (Rotation >= 0) {
                leftMotorOutput = Speed + Rotation;
                rightMotorOutput = maxInput;
            }
            else{
                leftMotorOutput = maxInput;
                rightMotorOutput = Speed - Rotation;
            }
        }

        //Set the Motors to the outputs clamped in the proper range
        dcDriveL.setPower(clamp(leftMotorOutput, -1, 1));
        dcDriveR.setPower(clamp(rightMotorOutput,-1,1)*-1);

    }

    /**
     * Locks the values within a deadband if less set value to zero
     * @param value the value to alter
     * @param deadband the range to consider deadband
     * @return the value conformed to the deadband
     */
    private double applyDeadband(double value, double deadband){
        if(Math.abs(value)<deadband)
            return 0;
        else
            return value;
    }

    /**
     * Locks the input value between two given values
     * @param value the value to lock
     * @param min the min value
     * @param max the max value
     * @return the clamped value
     */
    private double clamp(double value, double min, double max){
        return Math.max(min,Math.min(value,max));
    }

}

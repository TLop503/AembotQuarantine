package org.firstinspires.ftc.teamcode.NormalSwerve;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants;
import org.firstinspires.ftc.teamcode.Utilities.Control.PID;

public class NormalSwerveModule{

    private HardwareMap hardwareMap;
    private Gamepad gamepad1;

    private Telemetry telemetry;

    private double motorSpeedOffset = 0.1;

    //Determines the offset to apply to the tickCounts
    private int mainMotorOffest = 0;

    public boolean hasResetEncoders = false;

    //Module control variables are created up here to not destroy the garbage collector
    private double currentRotation = 0;
    private double wantedRotation = 0;

    //Represents the distances in wheel rotations
    private double currentDistanceRot = 0;
    private double wantedDistanceRot = 0;

    //Both deal with fine control of the swerve modules in autonomous
    private PID drivePID;

    //Power that will be set from the turnPID and drivePID commands respectively
    private double drivePower;


    private DcMotor mainMotor;
    private Servo mainServo;
    public NormalSwerveModule( HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry){

        this.telemetry = telemetry;

        this.hardwareMap = hardwareMap;
        this.gamepad1 = gamepad1;

        mainMotor = hardwareMap.get(DcMotor.class, "mainMotor");


        //Tells the motors to run at a constant velocity, not just based on the motor values
        mainMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        drivePID = new PID(Constants.DRIVE_P,Constants.DRIVE_I,Constants.DRIVE_D);
        drivePID.setAcceptableRange(0.1);
        drivePID.setSetpoint(0);

        resetMainEncoder();
    }

    public boolean autoPIDControl (double distance, double maxMotorSpeed) {

        currentDistanceRot = NormalSwerveMath.getWheelPosition(getMainMotorTicks());
        wantedDistanceRot = NormalSwerveMath.calculateWheelPosition(distance);

        drivePID.setSetpoint(wantedDistanceRot);
        drivePID.setMaxOutput(maxMotorSpeed);

        drivePower = drivePID.calcOutput(currentDistanceRot);
        if (!drivePID.isInRange()) {
            mainMotor.setPower(drivePower);
        } else {
            mainMotor.setPower(0);
            return true;
        }
        return false;
    }

    public int getMainMotorTicks(){
        return (int)mainMotor.getCurrentPosition()-mainMotorOffest;
    }
    public void resetMainEncoder(){

        mainMotorOffest = mainMotor.getCurrentPosition();
    }
}



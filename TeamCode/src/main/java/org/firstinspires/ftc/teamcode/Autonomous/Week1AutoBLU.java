
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name = "Week1AutoBLU", group = "Autonomous")

public class Week1AutoBLU extends OpMode {

    private boolean hasRun = false;
    // private SwerveController swerveController;

    private CRServo svTop;
    private CRServo svBottom;

    private DcMotor dcDriveL;
    private DcMotor dcDriveR;
    private DcMotor dcElevator;

    @Override
    public void init() {
        // swerveController = new SwerveController(hardwareMap);
    }

    @Override
    public void loop() {
        if(hasRun == false) {

            //1 is right -1 is left

            driveInches(24, .8);
            turnDegrees(45, .5,1);
            driveInches(8.485, .8);
            turnDegrees(45, .5, -1);
            driveInches(45, .8);

            //Aproaches build station
            turnDegrees(110, .5, 1);
            driveInches(45, .8);
            //Repositions
            driveInches(-12, .8);
            turnDegrees(20, .5, -1);
            driveInches(10, .8);
            turnDegrees(90, .5, 1);
            //pushes build station?
            driveInches(36, .8);
            //Parks?
            driveInches(-6, .8);
            turnDegrees(60, .5, 1);
            driveInches(75, 1);
            //Ends Program
            hasRun = true;
        }
    }

    private void turnDegrees(double degrees, double motorspeed, double directon){

        double turnTicks = (6.4) * degrees * directon;

        dcDriveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcDriveL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcDriveR.setTargetPosition(-(int)turnTicks);
        dcDriveL.setTargetPosition(-(int)turnTicks);

        dcDriveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcDriveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        dcDriveR.setPower(motorspeed);
        dcDriveL.setPower(motorspeed);
        while(dcDriveL.isBusy() && dcDriveR.isBusy()) {
            //Loop Can be, and should be, empty
        }


        //Stops Motors
        dcDriveL.setPower(0);
        dcDriveR.setPower(0);

    }

    private void driveInches(double distance, double motorspeed){

        double driveTicks = 288 * (distance / 12.566);

        dcDriveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcDriveL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Tells Encoders Where to go
        dcDriveR.setTargetPosition((int)driveTicks);
        dcDriveL.setTargetPosition(-(int)driveTicks);

        //Runs Motors to designated position
        dcDriveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcDriveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Sets speed to pre-determined value
        dcDriveR.setPower(motorspeed);
        dcDriveL.setPower(motorspeed);

        //Waits for motors to finish
        while(dcDriveL.isBusy() && dcDriveR.isBusy()) {
            //Loop Can be, and should be, empty
        }

        //Stops Motors
        dcDriveL.setPower(0);
        dcDriveR.setPower(0);

    }

}
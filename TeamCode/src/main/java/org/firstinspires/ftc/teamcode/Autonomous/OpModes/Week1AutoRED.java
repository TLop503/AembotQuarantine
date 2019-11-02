
package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name = "Week1AutoRED", group = "Autonomous")

public class Week1AutoRED extends OpMode {

    private boolean hasRun = false;
    private boolean isBlu = false;
    public enum Direction { LEFT, RIGHT }
       // private SwerveController swerveController;

    @Override
    public void init() {
       // swerveController = new SwerveController(hardwareMap);
    }

    @Override
    public void loop() {
        if(hasRun == false) {

            driveInches(24, .8);
            turnDegrees(45, .5 , Direction.RIGHT);
            driveInches(8.485, .8);
            turnDegrees(45, .5, Direction.LEFT);
            driveInches(45, .8);

            //Aproaches build station
            turnDegrees(110, .5);
            driveInches(45, .8);
            //Repositions
            driveInches(-12, .8);
            turnDegrees(20, .5, Direction.LEFT);
            driveInches(10, .8);
            turnDegrees(90, .5, Direction.RIGHT);
            //pushes build station?
            driveInches(36, .8);
            //Parks?
            driveInches(-6, .8);
            turnDegrees(60, Direction.RIGHT);
            driveInches(75, 1);
            //Ends Program
            hasRun = true;
        }
    }

    private void turnDegrees(double degrees, double motorspeed, Direction directon){
        double turnTicks = (6.4) * degrees

        dcDriveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcDriveL.setMode(DCMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (isBlu = true) {
            switch (directon) {
                default:
                case RIGHT:
                    dcDriveR.setTargetPositon((int) turnTicks);
                    dcDriveL.setTargetPositon((int) turnTicks);
                    break;

                case LEFT:
                    dcDriveR.setTargetPositon(-(int) turnTicks);
                    dcDriveL.setTargetPositon(-(int) turnTicks);
                    break;
            }
        }

        if isBlu = false {
            switch (turnDirection) {
                default:
                case RIGHT:
                    dcDriveR.setTargetPositon(-(int) turnTicks);
                    dcDriveL.setTargetPositon(-(int) turnTicks);
                    break;

                case LEFT:
                    dcDriveR.setTargetPositon((int) turnTicks);
                    dcDriveL.setTargetPositon((int) turnTicks);
                    break;
            }
        }

        dcDriveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcDriveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        dcDriveR.setPower(motorspeed);
        dcDriveL.setPower(moterspeed);

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
        dcDriveL.setMode(DCMotor.RunMode.STOP_AND_RESET_ENCODER);

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
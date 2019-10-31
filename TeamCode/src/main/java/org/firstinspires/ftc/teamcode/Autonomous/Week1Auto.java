
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name = "Week1Auto", group = "Autonomous")

public class Week1Auto extends OpMode {

    private boolean hasRun = false;

   // private SwerveController swerveController;

    @Override
    public void init() {
       // swerveController = new SwerveController(hardwareMap);
    }

    @Override
    public void loop() {
        if(hasRun == false) {

            driveInches(30, .5);

            //Ends Program
            hasRun = true;
        }
    }

    private void driveInches(double distance, double motorspeed){

        double ticks = 288 * (distance / 12.566);

        //Tells Encoders Where to go
        dcDriveR.setTargetPosition((int)ticks);
        dcDriveL.setTargetPosition(-(int)ticks);

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
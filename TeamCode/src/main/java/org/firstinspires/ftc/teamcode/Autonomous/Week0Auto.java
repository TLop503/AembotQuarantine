
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Week0Auto", group = "Autonomous")
@Disabled
public class Week0Auto extends OpMode {

    private boolean hasRun = false;
    private DcMotor dcDriveL;
    private DcMotor dcDriveR;

    @Override
    public void init() {
        dcDriveL = hardwareMap.get(DcMotor.class, "dcDriveL");
        dcDriveR = hardwareMap.get(DcMotor.class, "dcDriveR");

        //Clears Encoder Values
        dcDriveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcDriveL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        if(hasRun == false) {
            driveInches(27,.5);
            //Ends Program
            hasRun = true;
        }
    }

    //This Command will need to be removed after we switch to swerve
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
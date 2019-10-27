
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Automode", group = "Autonomous")

public class Automode extends OpMode {

    private boolean hasRun = false;
    private DcMotor dcDriveL;
    private DcMotor dcDriveR;

    @Override
    public void init() {
        dcDriveL = hardwareMap.get(DcMotor.class, "dcDriveL");
        dcDriveR = hardwareMap.get(DcMotor.class, "dcDriveR");
        dcDriveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcDriveL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        if(hasRun == false) {
            driveInches(27,.5);
            hasRun = true;
        }
    }

    //This Command will need to be removed after we switch to swerve
    private void driveInches(double distance, double motorspeed){

        double ticks = 288 * (distance / 12.566);

        dcDriveR.setTargetPosition((int)ticks);
        dcDriveL.setTargetPosition(-(int)ticks);

        dcDriveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcDriveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        dcDriveR.setPower(motorspeed);
        dcDriveL.setPower(motorspeed);

        while(dcDriveL.isBusy() && dcDriveR.isBusy()) {
            //Loop body can be empty
        }

        dcDriveL.setPower(0);
        dcDriveR.setPower(0);

    }
}
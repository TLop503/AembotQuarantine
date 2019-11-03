package org.firstinspires.ftc.teamcode.Utilities.Pathing.DriveStyles;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Utilities.Control.PID;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.IMU;
import org.firstinspires.ftc.teamcode.Utilities.Pathing.Utilities.Waypoint;

import java.util.List;

/**
 * Class created specifically for tank drive to allow it to follow a given path
 * @author Will Richards
 */
public class TankDriveFollow {

    private static int currentWaypoint = 0;

    //TODO: Tune PID
    static PID anglePID = new PID(0,0,0);

    /**
     * Tells the tank drive follow to go and start following the path
     * @param leftMotors the left side motors
     * @param rightMotors the right side motors
     * @param waypointList the list of waypoints to follow
     * @param encoderResoulution the number of ticks per one full rotation of the encoders output
     * @param wheelCircumfrance the circumfrance of the wheel
     * @param imu reference to the gyro scope to allow for changing angle
     */
    public static void follow(DcMotor[] leftMotors, DcMotor[] rightMotors, List<Waypoint> waypointList, double encoderResoulution, double wheelCircumfrance, IMU imu, double moveSpeed){

        //The range of the angle that is considered in range
        anglePID.setAcceptableRange(0.4);

        //The waypoint we are currently on
        Waypoint waypoint = waypointList.get(currentWaypoint);

        //Will keep trying to turn if it hasn't reached the angle yet
        if (!waypoint.getHasTurned()) {
            anglePID.setSetpoint(waypoint.getAngle());
            double power = anglePID.calcOutput(imu.getHeading());

            for (DcMotor motor : leftMotors) {
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                motor.setPower(power);
            }

            for (DcMotor motor : rightMotors) {
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                motor.setPower(-power);
            }
            if(anglePID.isInRange()){
                waypoint.setHasTurned(true);
            }
        }

        //If it has turned start driving the distance
        else{
            //Calculates the number of ticks needed to drive a given distance
            double ticksToDrive = encoderResoulution * (waypoint.getDistance() / wheelCircumfrance);
            double totalTicks = ticksToDrive + leftMotors[0].getCurrentPosition();

            for (DcMotor motor : leftMotors) {
                motor.setTargetPosition((int)totalTicks);
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setPower(moveSpeed);
            }

            for (DcMotor motor : rightMotors) {
                motor.setTargetPosition((int)-totalTicks);
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setPower(moveSpeed);
            }

            //Wait until wanted position is reached
            while (leftMotors[0].isBusy() && rightMotors[0].isBusy()){

            }
            currentWaypoint++;
        }



    }

}

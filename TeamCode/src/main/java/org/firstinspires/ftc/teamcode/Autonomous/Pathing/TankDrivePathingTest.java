package org.firstinspires.ftc.teamcode.Autonomous.Pathing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;
import org.firstinspires.ftc.teamcode.Utilities.Pathing.Path;
import org.firstinspires.ftc.teamcode.Utilities.Pathing.Utilities.DriveStyles;
import org.firstinspires.ftc.teamcode.Utilities.Pathing.Utilities.Waypoint;

import java.util.List;

/**
 * Created to test auto pathing
 * @author Will Richards
 */
@Autonomous(name = "Tank Drive Auto Path Test", group = "Test")
@Disabled
public class TankDrivePathingTest extends OpMode {

    Path path;

    DcMotor leftMotor;
    DcMotor rightMotor;

    DcMotor[] leftMotors = new DcMotor[1];
    DcMotor[] rightMotors = new DcMotor[1];

    List<Waypoint> waypointList;

    @Override
    public void init() {
        leftMotor = hardwareMap.get(DcMotor.class, "dcDriveL");
        rightMotor = hardwareMap.get(DcMotor.class, "dcDriveR");

        //REVERSE THE RIGHT SIDE
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Set the motors in the array
        leftMotors[0] = leftMotor;
        rightMotors[0] = rightMotor;

        path = new Path(DriveStyles.TANK, leftMotors,rightMotors,4,288,hardwareMap, IMUOrientation.HORIZONTAL);
        waypointList = path.load("TankTestPath");
    }

    @Override
    public void loop() {
        path.followPath(waypointList);
    }
}

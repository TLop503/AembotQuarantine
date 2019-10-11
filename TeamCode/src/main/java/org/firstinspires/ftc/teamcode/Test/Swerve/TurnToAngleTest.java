package org.firstinspires.ftc.teamcode.Test.Swerve;

import android.content.ContentUris;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Swerve.SwerveMath;

/**
 * Class created to test driving the swerve module to a wanted angle
 * @author Will Richards
 */
@TeleOp(name = "Swerve Turn Test", group = "Test")
public class TurnToAngleTest extends OpMode {

    private DcMotor TopSwerveMotor;
    private DcMotor BottomSwerveMotor;

    @Override
    public void init() {

        TopSwerveMotor = hardwareMap.get(DcMotor.class, "TopSwerveMotor");
        BottomSwerveMotor = hardwareMap.get(DcMotor.class, "BottomSwerveMotor");

    }

    @Override
    public void loop() {


        //double newTopTicks =  TopSwerveMotor.getCurrentPosition() + (int)((SwerveMath.normalizeJoystickAngle(gamepad1)*6.25)-((TopSwerveMotor.getCurrentPosition()+BottomSwerveMotor.getCurrentPosition())/4500)*2250);
        //double newBottomTicks =  BottomSwerveMotor.getCurrentPosition() + (int)((SwerveMath.normalizeJoystickAngle(gamepad1)*6.25)-((TopSwerveMotor.getCurrentPosition()+BottomSwerveMotor.getCurrentPosition())/4500)*2250);

        double currentRotation = (((double)TopSwerveMotor.getCurrentPosition()+(double)BottomSwerveMotor.getCurrentPosition())/2250);
        double wantedRotation = SwerveMath.normalizeJoystickAngle(gamepad1)/360;

        if(currentRotation > wantedRotation-0.02  && currentRotation < wantedRotation+0.02){
            TopSwerveMotor.setPower(0);
            BottomSwerveMotor.setPower(0);
        }
        else if(currentRotation > wantedRotation){
            TopSwerveMotor.setPower(-0.2);
            BottomSwerveMotor.setPower(-0.2);
        }
        else{
            BottomSwerveMotor.setPower(0.2);
            TopSwerveMotor.setPower(0.2);
        }

        //telemetry.addData("Current Position: ", ((topTicks+bottomTicks)/4500)*2250);
//        telemetry.addData("Current Top Tick", TopSwerveMotor.getCurrentPosition());
//        telemetry.addData("New Top Tick: ", newTopTicks);
        telemetry.addData("Current Module Rotation: ", (((double)TopSwerveMotor.getCurrentPosition()+(double)BottomSwerveMotor.getCurrentPosition())/4500));
        telemetry.addData("Wanted Module Rotation: ",SwerveMath.normalizeJoystickAngle(gamepad1)/360 );
        //telemetry.addData("X , Y", gamepad1.left_stick_x + "," + gamepad1.left_stick_y*-1);
    }
}

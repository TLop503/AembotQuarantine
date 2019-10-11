package org.firstinspires.ftc.teamcode.Test.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Swerve Auto Test", group = "Test")
public class SwerveModuleAutoTest extends OpMode {

    private DcMotor TopSwerveMotor;
    private DcMotor BottomSwerveMotor;

    @Override
    public void init() {
        TopSwerveMotor = hardwareMap.get(DcMotor.class, "TopSwerveMotor");
        BottomSwerveMotor = hardwareMap.get(DcMotor.class, "BottomSwerveMotor");
    }

    @Override
    public void loop() {
        double modulePosition = ((double)TopSwerveMotor.getCurrentPosition()+(double)BottomSwerveMotor.getCurrentPosition())/2250;

        turnModule(modulePosition,1);

        telemetry.addData("Position: ", modulePosition);
    }

    /**
     * Method to turn the module to a desired rotation
     * @param modulePosition
     * @param newRotation
     */
    private void turnModule(double modulePosition, double newRotation){

        // 1 rotation = 0.94 and 0.95
        if(modulePosition > newRotation-0.07 && modulePosition < newRotation-0.05){
            TopSwerveMotor.setPower(0);
            BottomSwerveMotor.setPower(0);
        }
        else if(modulePosition < newRotation-0.06){
            TopSwerveMotor.setPower(0.2);
            BottomSwerveMotor.setPower(0.2);
        }
        else if(modulePosition > newRotation-0.045){
            TopSwerveMotor.setPower(-0.2);
            BottomSwerveMotor.setPower(-0.2);
        }

    }
}

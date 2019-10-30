
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
            //Ends Program
            hasRun = true;
        }
    }

}
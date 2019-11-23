package org.firstinspires.ftc.teamcode.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Autonomous.Utilites.StoneApproach;
import org.firstinspires.ftc.teamcode.Subsystems.ServoArmController;
import org.firstinspires.ftc.teamcode.Subsystems.Utilities.ArmDirection;
import org.firstinspires.ftc.teamcode.Swerve.SwerveController;
import org.firstinspires.ftc.teamcode.Utilities.Vuforia.VuforiaWrapper;

@Autonomous(name = "Skystone to Foundation RED", group = "Week 2")
public class StoneToFoundationRed extends OpMode {
    // private boolean hasRun = false;
    private boolean[] actionsComplete = {false, false, false, false};

    private ServoArmController servos;
    private SwerveController swerveController;
    private VuforiaWrapper vuforia;
    private StoneApproach approach;

    /**
     * Initialization method for this OpMode.
     */
    @Override
    public void init() {
        // Initialize swerve
        swerveController = new SwerveController(hardwareMap);

        // Initialize the StoneApproach class
        approach = new StoneApproach(vuforia, swerveController, hardwareMap);

        servos = new ServoArmController(hardwareMap, null);
    }

    /**
     * Run continuously when init button is pressed to initialize Vuforia.
     */
    @Override
    public void init_loop() {
        vuforia.initVuforia();
    }

    /**
     * Normally, this method is run continuously (as it is a loop), but because of the variable
     * hasRun, the code within the if statement only runs once.
     */
    @Override
    public void loop() {
        if(!actionsComplete[0]) {
            actionsComplete[0] = swerveController.autoControlModules(0, 25, 1);
        } else if(!actionsComplete[1]) {
            actionsComplete[1] = approach.approachStone(10, 10);
        } else if(!actionsComplete[2]) {
            servos.controlArmsAutonomous(ArmDirection.DOWN);
            servos.controlArmsAutonomous(ArmDirection.GRIP);
            actionsComplete[2] = true;
        } else if(!actionsComplete[3]) {
            actionsComplete[3] = swerveController.autoControlModules(90, 48, 1);
        }
    }
}

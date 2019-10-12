package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Swerve.Enums.ModulePosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Class created to drive the 2 swerve modules
 * @author Will Richards
 */
public class SwerveController {

    //Local variations of variables from the OpMode
    private Gamepad gamepad1;
    private HardwareMap hardwareMap;

    //2 Swerve modules
    private SwerveModule leftModule;
    private SwerveModule rightModule;

    private List<SwerveModule> moduleList = new ArrayList<>();

    /**
     * Constructs the swerve modules and passes information only acssesible in the OpMode to this class to be used
     * @param gamepad1 a reference to the gamepad1
     * @param hardwareMap a reference to the robot's hardware map
     */
    public SwerveController(Gamepad gamepad1, HardwareMap hardwareMap){
        this.gamepad1 = gamepad1;
        this.hardwareMap = hardwareMap;

        //Creates 2 swerve modules
        //leftModule = new SwerveModule(ModulePosition.LEFT);
        rightModule = new SwerveModule(ModulePosition.RIGHT, hardwareMap, gamepad1);

        //Adds the modules to a list for easy iteration
        //moduleList.add(leftModule);
        moduleList.add(rightModule);
    }

    /**
     * General Method that allows control of all the modules inside the controller
     */
    public void controlModules(){
        for(SwerveModule module : moduleList){
            module.controlModule();
        }
    }

}

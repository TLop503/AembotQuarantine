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



    //Local variables from the OpMode
    private Gamepad gamepad1;
    private HardwareMap hardwareMap;

    //Create two new swerve module variables
    private SwerveModule leftModule;
    private SwerveModule rightModule;

    //Create a list to store the swerve modules this allows for easy control of multiple modules
    private List<SwerveModule> moduleList = new ArrayList<>();

    /**
     * Constructs the swerve modules and passes information only accsesible in the OpMode to this class to be used
     * @param gamepad1 a reference to the gamepad1
     * @param hardwareMap a reference to the robot's hardware map
     */
    public SwerveController(Gamepad gamepad1, HardwareMap hardwareMap){
        this.gamepad1 = gamepad1;
        this.hardwareMap = hardwareMap;

        //Instantiate 2 swerve modules
        leftModule = new SwerveModule(ModulePosition.LEFT, hardwareMap, gamepad1);
        rightModule = new SwerveModule(ModulePosition.RIGHT, hardwareMap, gamepad1);

        //Adds the modules to a list for easy iteration
        moduleList.add(leftModule);
        moduleList.add(rightModule);
    }

    /**
     * Constructs swerve modules for autonomous op modes, where there are no gamepads
     * @param hardwareMap a reference to the robot's hardware map
     */
    public SwerveController(HardwareMap hardwareMap){
        this.gamepad1 = null;
        this.hardwareMap = hardwareMap;

        //Instantiate 2 swerve modules
        leftModule = new SwerveModule(ModulePosition.LEFT, hardwareMap, null);
        rightModule = new SwerveModule(ModulePosition.RIGHT, hardwareMap, null);

        //Adds the modules to a list for easy iteration
        moduleList.add(leftModule);
        moduleList.add(rightModule);
    }

    /**
     * General Method that allows control of all the modules inside the controller
     */
    public void controlModules(){

        /*
         * Iterates through the list of modules and calls the control method on all of them
         */
        for(SwerveModule module : moduleList){
            module.PIDControl();
        }
    }

    /**
     * General Method that allows control of all the modules inside the controller
     */
    public void controlModules(double angle){

        /*
         * Iterates through the list of modules and calls the control method on all of them
         */
        for(SwerveModule module : moduleList){
            module.PIDControl(angle);
        }
    }

}

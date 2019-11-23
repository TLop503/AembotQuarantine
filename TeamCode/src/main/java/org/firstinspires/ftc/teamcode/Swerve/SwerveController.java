package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Swerve.Enums.ModulePosition;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.Enums.IMUOrientation;
import org.firstinspires.ftc.teamcode.Utilities.Hardware.IMU;

import java.util.ArrayList;
import java.util.List;

/**
 * Class created to drive the 2 swerve modules
 * @author Will Richards, Zane Othman-Gomez
 */
public class SwerveController {

    //Local variables from the OpMode
    private Gamepad gamepad1;
    private HardwareMap hardwareMap;

    private boolean isModuleOneZeroed = false;
    private boolean isModuleTwoZeroed = false;



    private boolean isFieldCentric;

    private IMU imu;

    private Telemetry telemetry;

    //Create two new swerve module variables
    private SwerveModule leftModule;
    private SwerveModule rightModule;

    //Create a list to store the swerve modules this allows for easy control of multiple modules
    private List<SwerveModule> moduleList = new ArrayList<>();

    boolean[] autoCompleteStatus;
    int i = 0;

    /**
     * Constructs the swerve modules and passes information only accessible in the OpMode to this class to be used
     * @param gamepad1 a reference to the gamepad1
     * @param hardwareMap a reference to the robot's hardware map
     */
    public SwerveController(Gamepad gamepad1, HardwareMap hardwareMap, Telemetry telemetry, IMUOrientation orientation, boolean isFieldCentric){
        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;

        this.isFieldCentric = isFieldCentric;

        this.imu = new IMU(hardwareMap, orientation);

        //Instantiate 2 swerve modules
        leftModule = new SwerveModule(ModulePosition.LEFT, hardwareMap, gamepad1, telemetry);
        rightModule = new SwerveModule(ModulePosition.RIGHT, hardwareMap, gamepad1, telemetry);

        //Adds the modules to a list for easy iteration
        moduleList.add(leftModule);
        moduleList.add(rightModule);

        autoCompleteStatus = new boolean[moduleList.size()];
    }

    /**
     * Constructs swerve modules for autonomous op modes, where there are no gamepads
     * @param hardwareMap a reference to the robot's hardware map
     */
    public SwerveController(HardwareMap hardwareMap){
        this.gamepad1 = null;
        this.hardwareMap = hardwareMap;

        //Instantiate 2 swerve modules
        leftModule = new SwerveModule(ModulePosition.LEFT, hardwareMap, null, telemetry);
        rightModule = new SwerveModule(ModulePosition.RIGHT, hardwareMap, null, telemetry);

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
            if(isFieldCentric)
                module.PIDControl(imu);
            else
                module.PIDControl();

        }
    }

    /**
     * Method that allows for autonomous control of modules
     */
    public boolean autoControlModules(double angle, double distance, double maxPower){

        /*
         * Iterates through the list of modules and calls the control method on all of them
         */
        for(SwerveModule module : moduleList){
            autoCompleteStatus[i] = module.AutoPIDControl(angle,distance,maxPower);
            i++;
        }

        //If both modules are complete
        if(autoCompleteStatus[0] && autoCompleteStatus[1]){

            //If it was return true
            return true;
        }

        //If the task wasn't completed return false
        return false;
    }

    /**
     * Method that allows for constant input to the modules
     */
    public void activeControl(double angle, double speed){
        for (SwerveModule module : moduleList){
            module.activeDrive(angle, speed);
        }
    }

    /**
     * A method to scale the running power of swerve modules based on outside factors.
     * @param angle The angle to run the swerve modules at.
     * @param scaleFactor The amount to scale the power up/down based on external conditions.
     */
    public void controlModulesScaled(double angle, double scaleFactor) {

        // Iterate through list of swerve modules & controls them
        for(SwerveModule module : moduleList) {
            module.PIDControlScaled(angle, scaleFactor);
        }
    }

    /**
     * For each module go through and reset the encoders
     */
    public void zeroModules(){
        for(SwerveModule module : moduleList){
            module.resetTopEncoder();
            module.resetBottomEncoder();
        }
    }

    /**
     * A method to stop the motors in every swerve module
     */
    public void stopModules() {
        for(SwerveModule module : moduleList) {
            module.stopMotors();
        }
    }

}

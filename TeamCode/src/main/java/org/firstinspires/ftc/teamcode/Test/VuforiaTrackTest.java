package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.Vuforia.VuforiaWrapper;

/**
 * This OpMode is used for testing skystone positions
 * @author Will Richards
 */
@TeleOp(name = "Vuforia Track Test", group = "Test")
@Disabled
public class VuforiaTrackTest extends OpMode {

    //Create a reference to the vuforia wrapper
    private VuforiaWrapper vuforia;


    /**
     * Called when the init button is pressed
     *
     * Create a new vuforia wrapper for the vuforia variable
     */
    @Override
    public void init() {
        vuforia = new VuforiaWrapper(hardwareMap);
    }

    /**
     * At the start of the loop set the transform equal to the value
     */
    @Override
    public void loop() {
        String transform = vuforia.getDetectedTransform();

        if(transform != null){
            telemetry.addData("Transform: ", transform);
        }
        else{
            telemetry.addData("Transform: ", "No Object Detected");
        }

        telemetry.update();
    }

    /**
     * Called once when the robot is first enabled
     * Activates the tracking
     */
    @Override
    public  void init_loop(){ vuforia.initVuforia(); }

}

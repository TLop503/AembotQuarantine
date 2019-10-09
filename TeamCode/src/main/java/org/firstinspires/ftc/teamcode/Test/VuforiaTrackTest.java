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
public class VuforiaTrackTest extends OpMode {

    String position = "";

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
        String X = vuforia.getX();

        if(X != null){
            if(Double.parseDouble(X)<-200)
                position = "Left";
            else if(Double.parseDouble(X)>200)
                position = "Right";
            else
                position = "Center";

            telemetry.addData("X: ", X);
            telemetry.addData("Position: ", position);
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

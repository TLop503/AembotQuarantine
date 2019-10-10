package org.firstinspires.ftc.teamcode.Test.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Utilities.Tensorflow.DetectedObject;
import org.firstinspires.ftc.teamcode.Utilities.Tensorflow.TensorflowWrapper;

import java.util.List;

/**
 * Class used to detect Skystones
 * !! Not Currently In Use Due To The Fact That It Qualifies Both As Skystones !!
 * @author Will Richards
 */
@TeleOp (name = "Tensorflow Angle Test", group = "Test")
@Disabled
public class TensorflowTrackTest extends OpMode {

    //Creates a new tensorflow wrapper object
    private TensorflowWrapper tf;

    @Override
    public void init() {

        //Initializes the Tensorflow wrapper class and gives it access to the hardware map
        tf = new TensorflowWrapper(hardwareMap);

        //Starts vuforia
        tf.initVuforia();

        //Starts Tensorflow
        tf.initTensorflow();

    }

    @Override
    public void loop() {
        //Gets and prints out all the tensor flow objects
        getObjects();
    }

    /**
     * When stop is pressed on the OpMode stop tracking
     */
    @Override
    public void stop(){
        tf.stop();
    }

    /**
     * Creates a list of DetectedObjects then iterates through the list and displays information about each visible object
     */
    private void getTensorflowObjects(){
        //Gets the list of detected objects
        List<DetectedObject> objects = tf.getDetectedObjects();

        if(objects != null){

            telemetry.addData("# Of Objects: ", objects.size());
            //For Every Object Output Some Information On it
            int i = 0;
            for(DetectedObject object : objects) {

                telemetry.addData(i + ": " + "Label: ", object.getLabel());
                telemetry.addData(i + ": " + "Top Left: ", object.getTopLeft());
                telemetry.addData(i + ": " + "Bottom Right: ", object.getBottomRight());
                telemetry.addData(i + ": " + "Center: ", object.getCenter());
                i++;
            }

        telemetry.update();
        }
    }

    /**
     * Uses a the DetectedObjects class to easily access data
     */
    private void getObjects(){
        List<Recognition> recognitions = tf.getRecog();

        if(recognitions != null){
            for(Recognition recognition : recognitions){
                telemetry.addData("Label: ", recognition.getLabel());
            }
        }
    }
}

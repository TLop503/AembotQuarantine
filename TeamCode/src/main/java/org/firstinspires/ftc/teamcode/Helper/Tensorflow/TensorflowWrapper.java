package org.firstinspires.ftc.teamcode.Helper.Tensorflow;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

/**
 * Class created to serve as an ease of access to the TensorFlow / Vuforia API
 */
public class TensorflowWrapper {

    //API Key used to access the vuforia API, TODO: May need updating
    private final String VUFORIA_KEY = "AXYyTwz/////AAAAGSZopKRDQkUWstJxKlGUqq0dBRoXhOdaJSvoDa+Wq00FKnLAUYSqP4OENgv1Q9BVMXFj9LG6L69Wc3fbJuVL7ZetjnVLIzjd9Cn9hvh5rp6HiSJ1rFrlfx0sgtkHda7a/B7HivbiVjfXq+Dta1L3IgQ+GSEmvdkXioXG6kA5ZDpQ8yG2o4cyzzvWTBzBQbrHumt1ek8qcGYAiv+552WCDMdTvrMC+NQf5R+CQdzub9pHC1rHEu6fCpQT9oq+zM6Vk3TMwxQ8KWhII0AXh5815A0yCvSyMqQFX++empRQj9o/hT6rSfz6hsHCeSg/RxptC2TpsfSos14e6rswW/Z/dh45fI3YTNfxxrkfzPjr2NAY";

    //Creates variables pertaining to the object detection model
    private final String TENSORFLOW_MODEL = "Skystone.tflite";
    private final String LABEL_FIRST_ELEMENT = "Stone";
    private final String LABEL_SECOND_ELEMENT = "Skystone";

    //Reference to the main vuforia object and the tensorflow object detection object
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private HardwareMap hardwareMap;

    /**
     * Constructor for the TensorflowWrapper Class
     */
    public TensorflowWrapper(HardwareMap hardwareMap){

        //Carries the hardware map across
        this.hardwareMap = hardwareMap;
    }

    /**
     * Starts the Vuforia engine for processing
     */
    public void initVuforia(){

        //Creates a new set of parameters to be used in the engine
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        //Specify the parameters for vuforia to use, the Key and which camera
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //Init the VuforiaLocalizer with the newly created parameters
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    /**
     * Initializes the Tensorflow object detection API
     */
    public void initTensorflow(){
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId","id", hardwareMap.appContext.getPackageName());

        //Creates a new parameter set based off the MonitorViewId
        TFObjectDetector.Parameters tfodParamaters = new TFObjectDetector.Parameters(tfodMonitorViewId);

        //This value is a percentage out of 1, it helps to avoid false positives.. if the value is less than 0.8 it is discarded
        tfodParamaters.minimumConfidence = 0.8;

        //Create a new tensor flow object
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParamaters, vuforia);

        //Load the trained tflite model with the Skystone and the regular stone
        tfod.loadModelFromAsset(TENSORFLOW_MODEL, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);

        //Enable object detection
        if(tfod != null){
            tfod.activate();
        }

    }

    /**
     * Gets all the detected objects and adds them to a list of a simplified class for using this objects
     * @return DetectedObjects list
     */
    public List<DetectedObject> getDetectedObjects(){

        //Create a list of recognitions
        List<Recognition> recognitions = tfod.getUpdatedRecognitions();
        List<DetectedObject> objects = new ArrayList<>();

        //If the list contains values, add them to a new object
        if(recognitions != null){

            for(Recognition recognition : recognitions){
                DetectedObject object = new DetectedObject();

                object.setLabel(recognition.getLabel());
                object.setTopLeft(recognition.getTop(), recognition.getLeft());
                object.setBottomRight(recognition.getBottom(), recognition.getRight());
                object.setHeight(recognition.getHeight());
                object.setWidth(recognition.getWidth());

                //After creating the object it adds it to the list
                objects.add(object);
            }

        }

        return objects;
    }



}

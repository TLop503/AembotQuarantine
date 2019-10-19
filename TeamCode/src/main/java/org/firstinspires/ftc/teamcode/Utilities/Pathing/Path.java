package org.firstinspires.ftc.teamcode.Utilities.Pathing;

import android.content.Context;
import android.content.res.AssetManager;

import com.qualcomm.ftccommon.FtcRobotControllerSettingsActivity;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that is used to control the actual pathing of the robot
 */
public class Path {

    //Stores the folder path where the paths are saved
    private final String FILE_PATH = "/Paths/";

    //Create a list of waypoints to hold individual waypoints
    List<Waypoint> waypoints = new ArrayList<>();

    /**
     * This method is used to load the file into a list of waypoints
     * TODO: Use assets if possible
     */
    public void load(String fileName){

        File file = new File(fileName);
        System.out.println(file.getPath());
        Scanner sc = null;
        try { sc = new Scanner(file); } catch (FileNotFoundException e) { }
        int i = 0;
        while (sc.hasNextLine()){
            String[] line = sc.nextLine().split(",");
            if(i>0)
                waypoints.add(new Waypoint(line[0],line[1],line[2],line[3],line[4]));
            i++;
        }
    }

}

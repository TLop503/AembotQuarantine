package org.firstinspires.ftc.teamcode.Utilities.Pathing;

import org.firstinspires.ftc.teamcode.Utilities.Pathing.Utilities.Waypoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that is used to load and parse paths
 * @author Will Richards
 */
public class Path {

    /**
     * This method is used to load the file into a list of waypoints
     */
    public static List<Waypoint> load(String className){
        List<Waypoint> waypointsList = new ArrayList<>();

        Class<?> cls = null;
        Object classReference = null;

        //Will attempt to load the class
        try { cls = Class.forName("org.firstinspires.ftc.teamcode.Utilities.Pathing.Paths." + className); } catch (ClassNotFoundException e) { }

        try { classReference = cls.newInstance(); } catch (Exception e) { }

        //Will get and store the overloaded to string method
        String initalOutput = classReference.toString();

        //Splits at the question marks to allow for split up sections
        String[] splitString = initalOutput.split("\\?");

        //Creates strings to store the waypoint position lists
        String X = "";
        String Y = "";
        String Angles = "";
        String Distances = "";
        String Actions = "";

        int i = 0;

        /*
         * Loops through the slightly parsed list
         */
        for (String list : splitString){
            String tempStr = list.substring(list.indexOf("["), list.indexOf("]"));
            tempStr = tempStr.replace("[","");
            tempStr = tempStr.replace(" ", "");

            //Depending on which set we are in store it as a different value
            switch (i){
                case 0:
                    X = tempStr;
                    break;
                case 1:
                    Y = tempStr;
                    break;
                case 2:
                    Angles = tempStr;
                    break;
                case 3:
                    Distances = tempStr;
                    break;
                case 4:
                    Actions = tempStr;
                    break;
            }
            i++;
        }

        //Make 5 different arrays to hold all the information that is output by the program
        String[] xArray = X.split(",");
        String[] yArray = Y.split(",");
        String[] AngleArray = Angles.split(",");
        String[] DistanceArray = Distances.split(",");
        String[] ActionArray = Actions.split(",");

        //Take all that information and create a new waypoint in a list and add that to the list
        for(int c=0;c<xArray.length;c++){
            waypointsList.add(new Waypoint(xArray[c],yArray[c],AngleArray[c], DistanceArray[c], ActionArray[c]));
        }

        return waypointsList;
    }

}

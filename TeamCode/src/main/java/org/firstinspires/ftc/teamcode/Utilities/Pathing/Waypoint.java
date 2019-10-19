package org.firstinspires.ftc.teamcode.Utilities.Pathing;

import java.security.acl.AclNotFoundException;

/**
 * Class used to derive waypoint options from the exported CSV files on the phones
 * @author Will Richards
 */
public class Waypoint {

    //In inches
    private double X;
    private double Y;

    //Both are for the next module
    private double Angle;
    private double Distance;

    //The action the robot should preform at the current waypoint
    private String Action;

    /**
     * Initlize all waypoint variables
     * @param X the X value of the current waypoint
     * @param Y the Y value of the current waypoint
     * @param Angle the angle of the next waypoint
     * @param Distance the distance to the next waypoint
     * @param Action the action to preform at the current waypoint
     */
    public Waypoint(String X, String Y, String Angle, String Distance, String Action){
        this.X = Double.parseDouble(X);
        this.Y = Double.parseDouble(Y);
        this.Angle = Double.parseDouble(Angle);
        this.Distance = Double.parseDouble(Distance);
        this.Action = Action;
    }

    /**
     * Returns the X value
     * @return X
     */
    public double getX(){
        return X;
    }

    /**
     * Returns the Y value
     * @return Y
     */
    public double getY(){
        return Y;
    }

    /**
     * Return the angle to the next waypoint
     * @return angle
     */
    public double getAngle(){
        return Angle;
    }

    /**
     * Return the angle to the next waypoint
     * @return distance
     */
    public double getDistance(){
        return Distance;
    }

    /**
     * Returns the action to run once the waypoint is reached
     * @return Action
     */
    public String getAction(){
        return Action;
    }
}

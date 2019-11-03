package org.firstinspires.ftc.teamcode.Utilities.Pathing.Utilities;

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

    private boolean hasTurnedToAngle;
    private boolean hasDriven;

    /**
     * Initlize all waypoint variables
     * @param X the X value of the current waypoint
     * @param Y the Y value of the current waypoint
     * @param Angle the angle of the next waypoint
     * @param Distance the distance to the next waypoint
     * @param Action the action to preform at the current waypoint
     */
    public Waypoint(String X, String Y, String Angle, String Distance, String Action, boolean hasTurnedToAngle, boolean hasDriven){
        this.X = Double.parseDouble(X);
        this.Y = Double.parseDouble(Y);
        this.Angle = Double.parseDouble(Angle);
        this.Distance = Double.parseDouble(Distance);
        this.Action = Action;

        this.hasTurnedToAngle = hasTurnedToAngle;
        this.hasDriven = hasDriven;
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

    /**
     * Returns whether or not the robot has already turned to face the angle
     * @return hasTurnedToAngle
     */
    public boolean getHasTurned(){return hasTurnedToAngle; }

    /**
     * Returns whether or not the robot has already driven to the next point
     * @return hasDriven
     */
    public boolean getHasDriven(){ return hasDriven; }

    /**
     * Set the value of the variable
     * @param hasTurned the new turn value
     */
    public void setHasTurned(boolean hasTurned){hasTurnedToAngle=hasTurned;}

    /**
     * Set the value of hasDrivne
     * @param hasDriven the new value to set
     */
    public void setHasDriven(boolean hasDriven){this.hasDriven = hasDriven;}
}

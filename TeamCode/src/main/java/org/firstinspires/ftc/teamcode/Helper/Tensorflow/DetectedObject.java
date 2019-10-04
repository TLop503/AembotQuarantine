package org.firstinspires.ftc.teamcode.Helper.Tensorflow;

/**
 * This is used to hold information about a detected object
 * @author Will Richards
 */
public class DetectedObject {

    //The object that was detected
    private String label;

    private String topLeft;
    private String bottomRight;

    private float width;
    private float height;

    /**
     * Gets the objects label
     * @return label
     */
    public String getLabel(){
        return label;
    }

    /**
     * Get the center of the object
     * @return the center point
     */
    public String getCenter(){
        return (width/2) + "," + (height/2);
    }

    /**
     * Sets a mew label to the object
     * @param label new label
     */
    void setLabel(String label){
        this.label = label;
    }

    //region Top Left
    //Get the top left corner
    public String getTopLeft(){
        return topLeft;
    }

    //Set the top left Corner
    void setTopLeft(float top, float left){
        this.topLeft = top + "," + left;
    }
    //endregion

    //region Bottom Right
    //Get the top left corner
    public String getBottomRight(){
        return bottomRight;
    }

    //Set the top left Corner
    void setBottomRight(float bottom, float right){
        this.bottomRight = bottom + "," + right;
    }
    //endregion

    //region Height
    public float getHeight(){
        return height;
    }

    void setHeight(float height){
        this.height = height;
    }
    //endregion

    //region Width
    public float getWidth(){
        return width;
    }

    void setWidth(float width){
        this.width = width;
    }
    //endregion
}

package org.firstinspires.ftc.teamcode.Swerve;

import org.firstinspires.ftc.teamcode.Swerve.Enums.ModulePosition;

/**
 * Class used to control an individual swerve module
 */
public class SwerveModule {

    //Holds the assigned module position be it left or right
    ModulePosition modPos;

    public SwerveModule(ModulePosition modPos){
        this.modPos = modPos;
    }
}

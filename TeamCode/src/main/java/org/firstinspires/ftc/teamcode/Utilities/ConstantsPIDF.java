package org.firstinspires.ftc.teamcode.Utilities;

public class ConstantsPIDF {
        // Turning PID constants
        private static final double MAX_VELOCITY = 3420;
        public static final double TURN_P = 5.2;
        public static final double TURN_I = 0;
        public static final double TURN_D = 0;
        public static final double TURN_F = 32767/MAX_VELOCITY;

        // Driving PID constants
        public static final double DRIVE_P = 0.75;
        public static final double DRIVE_I = 0;
        public static final double DRIVE_D = 0.8;
    }

package org.firstinspires.ftc.teamcode.Test.General;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;

import com.qualcomm.ftccommon.FtcRobotControllerService;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@TeleOp(name = "TestAssets", group = "Test")
public class AssetTester extends OpMode {

    Context context;
    AssetManager manager;

    String testOut = "";

    @Override
    public void init() {
        manager = context.getAssets();


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(manager.open("Skystone.xml")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                testOut += mLine;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        telemetry.addData("XML Test: ",testOut );
    }

    @Override
    public void loop() {

    }
}

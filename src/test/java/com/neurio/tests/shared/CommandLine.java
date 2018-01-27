package com.neurio.tests.shared;

import org.testng.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by Robert on 5/30/2016.
 * Calculator Class
 */
public class CommandLine {

    /**
     * Runs the Sensor Simulator Java Jar and returns its output as a string
     *
     * @param email    - email
     * @param sensorID - Sensor ID to use
     */
    public static void runSensorSimulator(String email, String sensorID, long seconds) {
        String command;

        command = "java -jar simulator-2.1.0-AlwaysRunning-jar-with-dependencies.jar --email " + email +
                " --sensorID " + sensorID;

        try {
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(command);

            p.waitFor(seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Exception happened - Here's what I know: ");
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}

package app.server.thread;

import java.io.File;
import java.io.FileWriter;

/**
 * This class is to record the log of the server to a file.
 * 
 * @author HaziqHapiz
 * 
 */

public class LogRecorder {

    /*
     * This method is to record the log of the server to a file.
     * 
     * @param log
     * 
     */
    public static void recordLog(String log) {
        try {

            // create a file
            File logFile = new File("log.txt");
            FileWriter myWriter = new FileWriter(logFile, true);

            // get current timestamp in DD/MM/YYYY HH:MM:SS format
            String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());

            logFile.createNewFile();

            // check if the log file exists or not and write the log to the file
            if (logFile.exists()) {
                myWriter.append(timestamp + " - " + log + "\n");
            } else {
                myWriter.write(timestamp + " - " + log + "\n");
            }
            myWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

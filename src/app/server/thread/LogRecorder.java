package app.server.thread;

import java.io.File;
import java.io.FileWriter;

/**
 * 
 * 
 * @author haziqhapiz
 *
 */

public class LogRecorder {
	
    public static void recordLog(String log) {
        try {
        	
            File myObj = new File("log.txt");
            FileWriter myWriter = new FileWriter(myObj, true);

            // get current timestamp in DD/MM/YYYY HH:MM:SS format
            String timestamp = new java.text.SimpleDateFormat
            		("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());

            if (myObj.createNewFile()) {
                myWriter.write(timestamp + " - " + log + "\n");
            } else {
                myWriter.append(timestamp + " - " + log + "\n");
            }
            
            myWriter.close();
            
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

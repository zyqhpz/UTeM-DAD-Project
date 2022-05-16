package app.client.thread;

/**
 * 
 * This class is used to remove all printed message in the console when needed.
 * It is to ensure the tidiness of the system.
 *
 */

public class ClearScreen {

    public static void ClearConsole() {
    	
        try {
        	
        	// Check the current operating system
            String operatingSystem = System.getProperty("os.name"); 
            
            if (operatingSystem.contains("Windows")) {
            	
                new ProcessBuilder("cmd", "/c", "cls").
                	inheritIO().start().waitFor();
                
            } else {
            	
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
            
        } catch (Exception e) {
        	
            System.out.println(e);
        }
    }
}

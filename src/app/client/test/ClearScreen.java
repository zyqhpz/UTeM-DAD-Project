package app.client.test;

public class ClearScreen {
    public static void main(String[] args) {
        System.out.println("Hello World");
        ClearConsole();
    }

    public static void ClearConsole(){
        try{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system
            System.out.println("Operating System: " + operatingSystem);
            if(operatingSystem.contains("Windows")){        
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                // Process startProcess = pb.inheritIO.start();
                Process startProcess = Runtime.getRuntime().exec("cls");
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                // Process startProcess = pb.inheritIO.start();
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            } 
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

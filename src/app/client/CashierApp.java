package app.client;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.*;

// TODOS
// 1. Display all menu
// 2. Select menu and quantity
// 3. Store in ArrayList
// 4. Add to Order object
// 5. Process Order when customer do payment (initialize all variables using
// setters)
// 6. Send Order object to server
// 7. Print receipt

/**
 * This class represent the client (Cashier) app for the system
 * 
 * Function: to start the client-side program for the Cashier
 * 
 * @author 
 */

public class CashierApp {
    public static void main(String args[]) {

        System.out.println("\n\nStarting CashierApp..\n");

        Order order = null;
        Scanner sc = new Scanner(System.in);

        try {

            // Server information
            int serverPortNo = 8087;
            InetAddress serverAddress = InetAddress.getLocalHost();

            do {

                // 1. Display all menu
                // 2. Select menu and quantity
                // 3. Store in ArrayList
                // 4. Add to Order object
                // 5. Process Order when customer do payment (initialize all variables using
                // setters)

                // 6. Send Order object to server
                Socket socket = new Socket(serverAddress, serverPortNo);

                OutputStream outStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outStream);
                oos.writeObject(order);
                socket.close();

                // 7. Print receipt

            } while (true);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

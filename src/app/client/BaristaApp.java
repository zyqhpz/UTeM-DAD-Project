package app.client;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import model.Order;
import model.OrderItem;

// Screens
// Main screen
/*
     --- Barista ---
     1. View pending orders

     Enter choice:
     >> 
*/

// Pending orders screen
/*
     --- Pending Orders ---
    Order Number        Quantity
    111                 2
    112                 3

     Select OrderID or 0 to go back to main menu:
     >> 
*/

// Order details screen
/*
    Print stickers here from A2 docs
    

     Select 1 to update order status OR 2 to go back to previous menu:
     >> 
*/

/*
    TODOS
    1. Display main menu
    2. Select to view pending orders
    3. Select Order Number to view order details
    4. Select to update order status
    5. Set order item status to "Ready"
    6. Send Order object to server
    7. Redirect to main menu
*/

/**
 * This class represent the client (Barista) app for the system
 * 
 * Function: to start the client-side program for the Barista
 * 
 * @author
 */

public class BaristaApp {
    public static void main(String[] args) {
        
        System.out.println("\n\nStarting BaristaApp..\n");

        Order order = null;
        OrderItem orderItem = null;

        Scanner sc = new Scanner(System.in);

        try {

            // Server information
            int serverPortNo = 8088;
            InetAddress serverAddress = InetAddress.getLocalHost();

            do {

                // 1. Display main menu
                // 2. Select to view pending orders

                // Create a multithread to always receive new list of pending orders from server
                // Display latest list of pending orders

                // 3. Select Order Number to view order details
                // 4. Select to update order status
                // 5. Set order item status to "Ready"
                orderItem.setOrderStatus("Ready");

                // 6. Send Order object to server
                Socket socket = new Socket(serverAddress, serverPortNo);
                OutputStream outStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outStream);
                oos.writeObject(order);

                socket.close();
                outStream.flush();
                oos.flush();
                outStream.close();
                oos.close();

                // 7. Redirect to main menu


            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

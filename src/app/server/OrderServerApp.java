package app.server;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import controller.OrderManager;
import controller.ItemProductManager;
import model.ItemProduct;
import model.Order;
import app.server.thread.*;

/**
 * This class represent the server app for the system
 * 
 * Function: to start the server-side program for the application
 * 
 * @author haziqhapiz
 */

public class OrderServerApp {

    public static void main(String args[]) 
    		throws ClassNotFoundException, Exception {

        System.out.println("\n\nStarting OrderServerApp..\n");

        ServerSocket cashierServerSocket = null;
        ServerSocket baristaServerSocket = null;
        ServerSocket baristaGetterServerSocket = null;

        Order order;
        OrderManager orderManager;
        ItemProductManager itemProductManager = new ItemProductManager();

        Socket baristaSocket = null;
        Socket baristaGetterSocket = null;

        List<ItemProduct> itemProducts = new ArrayList<ItemProduct>();
        itemProducts = itemProductManager.loadItemProducts();

        try {
            // bind to a port
            cashierServerSocket = new ServerSocket(8087);
            baristaServerSocket = new ServerSocket(8088);
            baristaGetterServerSocket = new ServerSocket(8089);

            System.out.println("\tWaiting for upcoming request\n");

            try {
            	// Accept request from client(cashier)
                Socket cashierSocket = cashierServerSocket.accept();

                OutputStream outStream = cashierSocket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outStream);

                // Send itemProduct object to cashier
                oos.writeObject(itemProducts);

                System.out.println("\tItemProducts list "
                		+ "sent to Order Counter\n");

            } catch (Exception e) {
                System.out.println("\tItemProducts failed to "
                		+ "send to Order Counter\n");
            }

            // Accept request from client(preparation counter)
            baristaSocket = baristaServerSocket.accept();
            baristaGetterSocket = baristaGetterServerSocket.accept();

            // Create Runnable and Thread object for preparation counter
            Runnable baristaController = 
            		new PreparationCounterController(baristaGetterSocket,
                    baristaGetterServerSocket);
            Thread baristaControllerThread = new Thread(baristaController);

            // Execute thread
            baristaControllerThread.start();

            // Create Runnable and Thread object for cashier
            Runnable cashierToServer = new OrderCounterController
            		(cashierServerSocket, baristaSocket);
            Thread cashierToServerThread = new Thread(cashierToServer);
            
            // Execute thread
            cashierToServerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

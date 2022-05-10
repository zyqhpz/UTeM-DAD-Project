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
 * This class represent the server app for OrderServerApp
 * 
 * Function: to start the server-side program for the application
 * 
 * @author haziqhapiz
 */

public class OrderServerApp {

    public static void main(String args[]) throws ClassNotFoundException, Exception {

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
            baristaGetterServerSocket = new ServerSocket(8085);

            System.out.println("\tWaiting for request upcoming request\n");

            try {
                Socket cashierSocket = cashierServerSocket.accept();

                OutputStream outStream = cashierSocket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outStream);

                oos.writeObject(itemProducts);

                System.out.println("\tItemProducts sent to Order Counter\n");

            } catch (Exception e) {
                System.out.println("\tItemProducts failed to send to Order Counter\n");
            }

            baristaSocket = baristaServerSocket.accept();

            baristaGetterSocket = baristaGetterServerSocket.accept();

            Runnable baristaController = new PreparationCounterController(baristaGetterSocket,
                    baristaGetterServerSocket);
            Thread baristaControllerThread = new Thread(baristaController);

            baristaControllerThread.start();

            Runnable cashierToServer = new OrderCounterController(cashierServerSocket, baristaSocket);
            Thread cashierToServerThread = new Thread(cashierToServer);
            cashierToServerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

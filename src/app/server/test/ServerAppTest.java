package app.server.test;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import app.server.thread.BaristaReceiver;
import controller.OrderManager;
import controller.database.Database;
import model.Order;

/**
 * This class represent the server app for HornettTeaOrderServerApp
 * 
 * Function: to start the server-side program for the application
 * 
 * @author haziqhapiz
 */

public class ServerAppTest {

    // private Socket baristaSocket = null;

    private static List<Order> orders = new ArrayList<Order>();

    public static void main(String args[]) throws ClassNotFoundException, Exception {

        System.out.println("\n\nStarting HornettTeaOrderServerApp..\n");

        ServerSocket serverSocket = null;
        ServerSocket baristaServerSocket = null;

        Order order;
        OrderManager orderManager;

        Socket baristaSocket = null;

        try {

            // create a connection to the database
            // Connection conn = Database.doConnection();
            // System.out.println("Connected to the database..\n");

            // bind to a port
            int portNo = 8087;
            serverSocket = new ServerSocket(portNo);
            InetAddress serverAddress = InetAddress.getLocalHost();
            // Socket baristaSocket = new Socket(serverAddress, 8088);

            // baristaServerSocket = new ServerSocket(8088);

            System.out.println("\tWaiting for request from Cashier\n");

            Runnable barista = null;
            Thread baristaThread = null;

            // 2. Listen to request
            while (!serverSocket.isClosed()) {

                // 3. Accept request from client
                Socket clientSocket = serverSocket.accept();

                InputStream is = clientSocket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);

                // read Order object from cashier
                order = (Order) ois.readObject();

                orderManager = new OrderManager(order);

                System.out.println("\n\tReceive an Order object from Cashier.\n");

                // display data from Order object
                // orderManager.displayData();

                order.setOrderId(2);

                // add order to orders
                orders.add(order);

                System.out.println("\n\tOrder added to the list. Now " + orders.size() + "\n");

                System.out.println("\n\tWaiting for next request\n");

                // start the barista as thread
                // Runnable barista = new BaristaReceiver();

                // try (Socket baristaSocket = new Socket(serverAddress, 8088);) {
                try {

                    baristaSocket = new Socket(serverAddress, 8088);

                    barista = new BaristaReceiver(baristaSocket);
                    baristaThread = new Thread(barista);

                    OutputStream baristaOS = baristaSocket.getOutputStream();
                    ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

                    // baristaOOS.writeObject(order);

                    // send orders to barista
                    baristaOOS.writeObject(orders);

                    System.out.println("\n\tOrder object sent to Barista.\n");

                } catch (Exception e) {
                    System.out.println("\n\tError: " + e.getMessage() + "\n");
                }

                // Runnable barista = new BaristaReceiver(baristaSocket);
                // Thread baristaThread = new Thread(barista);
                baristaThread.start();

            }

        } catch (Exception e) {

            if (serverSocket != null) {
                serverSocket.close();
            }

            e.printStackTrace();
        }
    }
}

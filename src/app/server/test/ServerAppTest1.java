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
import controller.ItemProductManager;
import controller.database.Database;
import model.ItemProduct;
import model.Order;

/**
 * This class represent the server app for HornettTeaOrderServerApp
 * 
 * Function: to start the server-side program for the application
 * 
 * @author haziqhapiz
 */

public class ServerAppTest1 {

    // private Socket baristaSocket = null;

    private static List<Order> orders = new ArrayList<Order>();

    public static void main(String args[]) throws ClassNotFoundException, Exception {

        System.out.println("\n\nStarting HornettTeaOrderServerApp..\n");

        ServerSocket cashierServerSocket = null;
        ServerSocket baristaServerSocket = null;

        Order order;
        OrderManager orderManager;
        ItemProductManager itemProductManager = new ItemProductManager();

        Socket baristaSocket = null;

        List<ItemProduct> itemProducts = new ArrayList<ItemProduct>();
        itemProducts = itemProductManager.loadItemProducts();

        try {

            // create a connection to the database
            // Connection conn = Database.doConnection();
            // System.out.println("Connected to the database..\n");

            // bind to a port
            int portNo = 8087;
            cashierServerSocket = new ServerSocket(portNo);

            // Socket clientSocket = cashierServerSocket.accept();

            // OutputStream outStream = clientSocket.getOutputStream();
            // ObjectOutputStream oos = new ObjectOutputStream(outStream);

            // oos.writeObject(itemProducts);

            InetAddress serverAddress = InetAddress.getLocalHost();
            // Socket baristaSocket = new Socket(serverAddress, 8088);

            // baristaServerSocket = new ServerSocket(8088);

            baristaServerSocket = new ServerSocket(8088);

            ServerSocket baristaGetterSocket = new ServerSocket(8085);

            // Socket baristaGetter = baristaGetterSocket.accept();
            Socket baristaGetter = null;

            System.out.println("\tWaiting for request from Cashier\n");

            Runnable barista = null;
            Thread baristaThread = null;
            Thread baristaSenderThread = null;

            try {
                Socket cashierSocket = cashierServerSocket.accept();

                OutputStream outStream = cashierSocket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outStream);

                oos.writeObject(itemProducts);

                System.out.println("\tItemProducts sent to Cashier\n");

            } catch (Exception e) {
                System.out.println("\tNo request from Cashier\n");
            }

            baristaSocket = baristaServerSocket.accept();

            baristaGetter = baristaGetterSocket.accept();

            Runnable baristaSender = new BaristaFromServerTest(
                    baristaGetter, baristaServerSocket,
                    baristaGetterSocket);
            baristaSenderThread = new Thread(baristaSender);

            baristaSenderThread.start();

            Runnable clientToServer = new ClientToServer(cashierServerSocket, baristaServerSocket, baristaSocket);
            Thread clientToServerThread = new Thread(clientToServer);
            clientToServerThread.start();

        } catch (Exception e) {

            // if (serverSocket != null) {
            // serverSocket.close();
            // }

            e.printStackTrace();
        }
    }
}

package app.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import app.server.thread.BaristaReceiver;
import app.server.thread.CashierReceiver;
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

public class OrderServerApp {
    public static void main(String args[]) throws ClassNotFoundException, Exception {

        System.out.println("\n\nStarting HornettTeaOrderServerApp..\n");

        ServerSocket serverSocket = null;
        ServerSocket baristaServerSocket = null;

        Socket baristaSocket = null;

        Order order;
        OrderManager orderManager;

        // Thread cashierListener = new Thread(new CashierReceiver());
        // cashierListener.start();

        // Thread baristaListener = new Thread(new BaristaReceiver());
        // baristaListener.start();

        // try {

        // create a connection to the database
        // Connection conn = Database.doConnection();
        // System.out.println("Connected to the database..\n");

        // bind to a port
        // int portNo = 8087;
        // serverSocket = new ServerSocket(portNo);
        // InetAddress serverAddress = InetAddress.getLocalHost();
        // Socket baristaSocket = new Socket(serverAddress, 8088);

        // baristaServerSocket = new ServerSocket(8088);

        System.out.println("\tWaiting for request from Cashier\n");

        while (true) {

            Runnable cashierReceiver = new CashierReceiver();
            Runnable baristaReceiver = new BaristaReceiver(baristaSocket);
            Thread cashierThread = new Thread(cashierReceiver);
            Thread baristaThread = new Thread(baristaReceiver);

            cashierThread.start();
            baristaThread.start();
        }

        // 2. Listen to request
        // while (true) {

        // Thread t = new Thread(new Runnable() {
        // @Override
        // public void run() {
        // try {
        // baristaSocket = serverSocket.accept();
        // System.out.println("\tConnected to Cashier\n");

        // // 3. Receive Order object from Cashier
        // InputStream is = baristaSocket.getInputStream();
        // ObjectInputStream ois = new ObjectInputStream(is);
        // order = (Order) ois.readObject();
        // System.out.println("\tReceived Order object from Cashier\n");

        // // 4. Process Order object
        // orderManager = new OrderManager();
        // orderManager.processOrder(order);
        // System.out.println("\tProcessed Order object\n");

        // // 5. Send Order object to Cashier
        // OutputStream os = baristaSocket.getOutputStream();
        // ObjectOutputStream oos = new ObjectOutputStream(os);
        // oos.writeObject(order);
        // System.out.println("\tSent Order object to Cashier\n");

        // // 6. Close connection
        // baristaSocket.close();
        // System.out.println("\tClosed connection to Cashier\n");

        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // });

        // // 3. Accept request from client
        // Socket clientSocket = serverSocket.accept();

        // InputStream is = clientSocket.getInputStream();
        // ObjectInputStream ois = new ObjectInputStream(is);

        // // read Order object from cashier
        // order = (Order) ois.readObject();

        // orderManager = new OrderManager(order);

        // System.out.println("\n\tReceive an Order object from Cashier.\n");

        // // display data from Order object
        // orderManager.displayData();

        // // Send Order object to Barista
        // // Socket baristaSocket = baristaServerSocket.accept();

        // // OutputStream baristaOS = baristaSocket.getOutputStream();
        // // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

        // // baristaOOS.writeObject(order);

        // // System.out.println("\n\tOrder object sent to Barista.\n");

        // // baristaSocket.close();

        // order.setOrderId(2);

        // System.out.println("\n\tWaiting for next request\n");

        // baristaSocket = new Socket(serverAddress, 8088);
        // // OutputStream baristaOS = baristaSocket.getOutputStream();
        // // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

        // try (OutputStream baristaOS = baristaSocket.getOutputStream();) {

        // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);
        // // Send Order object to Barista
        // // Socket baristaSocket = baristaServerSocket.accept();

        // // OutputStream baristaOS = baristaSocket.getOutputStream();
        // // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

        // baristaOOS.writeObject(order);

        // System.out.println("\n\tOrder object sent to Barista.\n");

        // // baristaSocket.close();
        // // clientSocket.close();
        // } catch (Exception e) {
        // System.out.println("\n\tError by OOS: " + e.getMessage() + "\n");
        // }

        // int orderId;
        // InputStream baristaIS = baristaSocket.getInputStream();
        // ObjectInputStream baristaOIS = new ObjectInputStream(baristaIS);

        // baristaSocket = new Socket(serverAddress, 8088);
        // try (InputStream baristaIS = baristaSocket.getInputStream();) {
        // // try (ObjectInputStream baristaOIS = new ObjectInputStream(baristaIS);) {

        // // Send Order object to Barista
        // // Socket baristaSocket = baristaServerSocket.accept();

        // // baristaSocket = baristaSocket.accept();

        // // InputStream baristaIS = baristaSocket.getInputStream();
        // ObjectInputStream baristaOIS = new ObjectInputStream(baristaIS);

        // // OutputStream baristaOS = baristaSocket.getOutputStream();
        // // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

        // // baristaOOS.writeObject(order);
        // // order = (Order) baristaOIS.readObject();
        // int orderId = baristaOIS.readInt();

        // // System.out.println("\n\tOrder object received from Barista.\n");
        // System.out.println("\n\tOrder " + orderId + " get from Barista.\n");

        // // baristaSocket.close();
        // // clientSocket.close();

        // } catch (Exception e) {
        // System.out.println("\n\tError by OIS: " + e.getMessage() + "\n");
        // }

    }

    // } catch (Exception e) {

    // if (serverSocket != null) {
    // // serverSocket.close();
    // }

    // e.printStackTrace();
    // }
    // }
}

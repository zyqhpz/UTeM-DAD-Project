package app.server.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import controller.OrderManager;
import model.Order;

public class CashierReceiver implements Runnable {

    private Socket cashierSocket = null;
    private ServerSocket serverSocket = null;
    private Socket baristaSocket = null;
    private InetAddress serverAddress = null;

    public CashierReceiver() {
        try {
            // bind to a port
            int portNo = 8088;
            serverAddress = InetAddress.getLocalHost();
            // serverSocket = new ServerSocket(portNo);
            // serverSocket = new ServerSocket(8087);
            // baristaSocket = new Socket(serverAddress, 8088);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        int portNo = 8087;
        Socket baristaSocket = null;
        Order order;
        OrderManager orderManager;

        // while (true) {
        try {
            serverSocket = new ServerSocket(portNo);
            // InetAddress serverAddress = InetAddress.getLocalHost();
            Socket clientSocket = serverSocket.accept();

            InputStream is = clientSocket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            // read Order object from cashier
            order = (Order) ois.readObject();

            orderManager = new OrderManager(order);

            System.out.println("\n\tReceived an Order object from Cashier.\n");

            // display data from Order object
            orderManager.displayData();

            // Send Order object to Barista
            // Socket baristaSocket = baristaServerSocket.accept();

            // OutputStream baristaOS = baristaSocket.getOutputStream();
            // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

            // baristaOOS.writeObject(order);

            // System.out.println("\n\tOrder object sent to Barista.\n");

            // baristaSocket.close();

            order.setOrderId(2);

            System.out.println("\n\tWaiting for next request\n");

            // baristaSocket = new Socket(serverAddress, 8088);
            // OutputStream baristaOS = baristaSocket.getOutputStream();
            // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

            try {
                baristaSocket = new Socket(serverAddress, 8088);

                // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);
                // Send Order object to Barista
                // Socket baristaSocket = baristaServerSocket.accept();

                OutputStream baristaOS = baristaSocket.getOutputStream();
                ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

                baristaOOS.writeObject(order);

                System.out.println("\n\tOrder object sent to Barista.\n");

                // baristaSocket.close();
                // clientSocket.close();
            } catch (Exception e) {
                System.out.println("\n\tError by OOS: " + e.getMessage() + "\n");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        // }
    }

}

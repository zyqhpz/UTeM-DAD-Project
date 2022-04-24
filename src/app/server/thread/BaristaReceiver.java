package app.server.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

import model.Order;

public class BaristaReceiver implements Runnable {

    private Socket baristaSocket = null;
    private InetAddress serverAddress = null;

    // public BaristaReceiver() {
    // try {
    // // bind to a port
    // int portNo = 8088;
    // serverAddress = InetAddress.getLocalHost();
    // // baristaSocket = new Socket(serverAddress, portNo);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    private Order order;

    public BaristaReceiver(Socket baristaSocket) {
        this.baristaSocket = baristaSocket;
    }

    @Override
    public void run() {
        // Socket baristaSocket = null;
        // while (true) {
        // try {
        // // InetAddress serverAddress = InetAddress.getLocalHost();
        // baristaSocket = new Socket(serverAddress, 8088);

        // InputStream baristaIS = baristaSocket.getInputStream();
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
        // // System.out.println("\n\tError by OIS: " + e.getMessage() + "\n");
        // }
        // // }

        // try (Socket baristaSocket = new Socket(serverAddress, 8088);) {

        while (true) {

            try {

                // Send Order object to Barista
                // Socket baristaSocket = baristaServerSocket.accept();

                // baristaSocket = baristaSocket.accept();

                // Listen to InputStream from Barista

                InputStream baristaIS = baristaSocket.getInputStream();
                ObjectInputStream baristaOIS = new ObjectInputStream(baristaIS);

                // OutputStream baristaOS = baristaSocket.getOutputStream();
                // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

                // baristaOOS.writeObject(order);
                // order = (Order) baristaOIS.readObject();

                // int orderId = baristaOIS.readInt();

                int orderId = 1;

                try {
                    // Thread.sleep(5000);
                    // orderId = (int) baristaOIS.readObject();
                    // orderId = baristaOIS.readInt();

                    order = (Order) baristaOIS.readObject();
                    orderId = order.getOrderId();

                    System.out.println("\n\tOrder " + orderId + " get from Barista.\n");

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // int orderId = (int) baristaOIS.readObject();

                // orderId = order.getOrderId();

                // System.out.println("\n\tOrder " + orderId + " get from Barista.\n");

                // System.out.println("\n\tOrder " + orderId + " get from Barista.\n");

                // System.out.println("\n\tOrder object received from Barista.\n");

                // baristaSocket.close();
                // clientSocket.close();

            } catch (Exception e) {
                System.out.println("\n\tError from BaristaReceiver: " + e.getMessage() + "\n");
            }
        }
    }
}

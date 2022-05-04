package app.server.test;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import model.*;

public class BaristaFromServerTest implements Runnable {
    private Socket baristaSocket = null;
    private Order order;
    private ServerSocket baristaServerSocket = null;
    private ServerSocket baristaGetterSocket = null;
    public BaristaFromServerTest(Socket baristaSocket, ServerSocket baristaServerSocket, ServerSocket baristaGetterSocket) {
        this.baristaSocket = baristaSocket;
        this.baristaServerSocket = baristaServerSocket;
        this.baristaGetterSocket = baristaGetterSocket;
        // try {
        //     // bind to a port
        //     int portNo = 8088;
        //     InetAddress serverAddress = InetAddress.getLocalHost();
        //     // Socket baristaSocket = new Socket(serverAddress, portNo);

        //     // Send Order object to Barista
        //     // Socket baristaSocket = baristaServerSocket.accept();

        //     InputStream baristaIS = baristaSocket.getInputStream();
        //     ObjectInputStream baristaOIS = new ObjectInputStream(baristaIS);

        //     // OutputStream baristaOS = baristaSocket.getOutputStream();
        //     // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

        //     // baristaOOS.writeObject(order);
        //     // order = (Order) baristaOIS.readObject();
        //     int orderId = baristaOIS.readInt();

        //     // System.out.println("\n\tOrder object received from Barista.\n");
        //     System.out.println("\n\tOrder " + orderId + " get from Barista.\n");

        //     baristaSocket.close();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    @Override
    public void run() {
        try {
            // bind to a port
            int portNo = 8085;
            // InetAddress serverAddress = InetAddress.getLocalHost();
            // Socket baristaSocket = new Socket(serverAddress, portNo);
            // ServerSocket baristaServerSocket = new ServerSocket(portNo);
            // Socket baristaSocket = baristaServerSocket.accept();
            // ServerSocket baristaServerSocket = new ServerSocket(portNo);
            // Socket baristaSocket = baristaServerSocket.accept();

            // Send Order object to Barista
            // Socket baristaSocket = baristaGetterSocket.accept();

            InputStream baristaIS = baristaSocket.getInputStream();
            ObjectInputStream baristaOIS = new ObjectInputStream(baristaIS);

            // OutputStream baristaOS = baristaSocket.getOutputStream();
            // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

            // baristaOOS.writeObject(order);
            order = (Order) baristaOIS.readObject();
            // int orderId = baristaOIS.readInt();

            int orderId = order.getOrderId();

            // System.out.println("\n\tOrder object received from Barista.\n");
            System.out.println("\n\tOrder " + orderId + " get from Barista.\n");

            // baristaSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

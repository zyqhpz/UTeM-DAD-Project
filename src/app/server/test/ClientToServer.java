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

public class ClientToServer implements Runnable {

    private ServerSocket cashierServerSocket = null;
    private ServerSocket baristaServerSocket = null;
    private Socket cashierSocket = null;
    private Socket baristaSocket = null;
    private Order order;
    private OrderManager orderManager;
    private List<Order> orders = new ArrayList<Order>();

    public ClientToServer(ServerSocket cashierServerSocket, ServerSocket baristaServerSocket, Socket baristaSocket) {
        this.cashierServerSocket = cashierServerSocket;
        this.baristaServerSocket = baristaServerSocket;
        this.baristaSocket = baristaSocket;
    }

    @Override
    public void run() {
        while (!cashierServerSocket.isClosed()) {

            try {
                // 3. Accept request from client
                Socket clientSocket = cashierServerSocket.accept();

                InputStream is = clientSocket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);

                // read Order object from cashier
                order = (Order) ois.readObject();

                orderManager = new OrderManager(order);

                // TODO: insert order into database

                System.out.println("\n\tReceive an Order object from Cashier.\n");

                // display data from Order object
                // orderManager.displayData();

                order.setOrderId(2);

                // add order to orders
                orders.add(order);

                orders = null;
                orders = orderManager.loadData();

                System.out.println("\n\tOrder added to the list. Now " + orders.size() + "\n");

                System.out.println("\n\tWaiting for next request\n");

                if (orders != null) {
                    try {

                        OutputStream baristaOS = baristaSocket.getOutputStream();
                        ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

                        // baristaOOS.writeObject(order);

                        // send orders to barista
                        baristaOOS.writeObject(orders);

                        System.out.println("\n\tOrder object sent to Barista.\n");

                    } catch (Exception e) {
                        System.out.println("\n\tError: " + e.getMessage() + "\n");
                    }
                } else {
                    System.out.println("\n\tOrders retrieve is blank.\n");
                }
            } catch (Exception e) {
                System.out.println("\n\tError: " + e.getMessage() + "\n");
            }
        }
    }
}

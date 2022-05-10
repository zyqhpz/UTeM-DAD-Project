package app.client.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.ItemProduct;
import model.Order;
import model.OrderItem;

public class PreparationCounterGetter implements Runnable {
    private Socket baristaSocket = null;
    private InetAddress serverAddress = null;
    private ServerSocket serverSocket = null;
    private Socket baristaToServerSocket = null;

    List<Order> orders = new ArrayList<Order>();

    private Order order;

    public PreparationCounterGetter(ServerSocket serverSocket, Socket baristaSocket, Socket baristaToServerSocket) {
        this.serverSocket = serverSocket;
        this.baristaSocket = baristaSocket;
        this.baristaToServerSocket = baristaToServerSocket;
    }

    @Override
    public void run() {
        Runnable preparationCounterMainMenu = null;
        Thread baristaMainMenuThread = null;

        while (!baristaSocket.isClosed()) {
            try {
                Socket socket = baristaSocket;
                InputStream inStream = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inStream);

                List<Order> orders = new ArrayList<Order>();

                try {

                    Object obj = ois.readObject();
                    // Check it's an ArrayList
                    if (obj instanceof ArrayList<?>) {
                        // Get the List.
                        ArrayList<?> al = (ArrayList<?>) obj;
                        if (al.size() > 0) {
                            // Iterate.
                            for (int i = 0; i < al.size(); i++) {
                                // Still not enough for a type.
                                Object o = al.get(i);
                                if (o instanceof Order) {
                                    // Here we go!
                                    Order v = (Order) o;
                                    orders.add(v);
                                    // use v.
                                }
                            }
                        }
                    }

                    preparationCounterMainMenu = new PreparationCounterMainMenu(serverSocket, order,
                            baristaToServerSocket,
                            orders);

                    baristaMainMenuThread = new Thread(preparationCounterMainMenu);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                baristaMainMenuThread.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

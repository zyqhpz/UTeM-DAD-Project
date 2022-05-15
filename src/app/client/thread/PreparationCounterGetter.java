package app.client.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.Order;

public class PreparationCounterGetter implements Runnable {
    private Socket baristaSocket = null;
    private Socket baristaToServerSocket = null;

    List<Order> orders = new ArrayList<Order>();

    public PreparationCounterGetter(Socket baristaSocket, Socket baristaToServerSocket) {
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

                    preparationCounterMainMenu = new PreparationCounterMainMenu(baristaToServerSocket, orders);

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

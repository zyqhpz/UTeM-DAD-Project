package app.server.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import controller.OrderItemManager;
import model.*;

public class PreparationCounterController implements Runnable {
    private Socket baristaSocket = null;
    private Order order;
    private ServerSocket baristaGetterSocket = null;

    public PreparationCounterController(Socket baristaSocket, ServerSocket baristaGetterSocket) {
        this.baristaSocket = baristaSocket;
        this.baristaGetterSocket = baristaGetterSocket;
    }

    @Override
    public void run() {
        try {
            while (baristaSocket.isConnected()) {

                baristaSocket = baristaGetterSocket.accept();

                InputStream baristaIS = baristaSocket.getInputStream();
                ObjectInputStream baristaOIS = new ObjectInputStream(baristaIS);

                order = (Order) baristaOIS.readObject();

                int orderId = order.getOrderId();

                try {
                    // update order status
                    OrderItemManager orderItemManager = new OrderItemManager();
                    orderItemManager.updateOrderStatus(order);

                    System.out.println("\n\tOrderId: " + orderId + ". Order status updated.\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

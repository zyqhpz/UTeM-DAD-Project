package app.server.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import controller.OrderManager;
import model.Order;

/**
 * This class is the controller class of server towards Order Counter.
 * 
 * @author haziqhapiz
 *
 */

public class OrderCounterController implements Runnable {

    private ServerSocket cashierServerSocket = null;
    private Socket baristaSocket = null;
    private Order order;
    private OrderManager orderManager = new OrderManager();
    private List<Order> orders = new ArrayList<Order>();

    public OrderCounterController(ServerSocket cashierServerSocket,
            Socket baristaSocket) {
        this.cashierServerSocket = cashierServerSocket;
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

                System.out.println("\n\tReceive new order from Order Counter\n");

                LogRecorder.recordLog("Order Number: " + order.getOrderNumber()
                        + " received from Order Counter");

                // do calculation in order
                order = orderManager.calculateChange(order);

                // return Order back to cashier
                OutputStream os = clientSocket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(order);

                System.out.println("\n\tNew order has been calculated. "
                		+ "Return the order to Order Counter\n");

                orderManager.insertDataOrder(order);

                System.out.println("\n\tNew order has been "
                		+ "inserted to database\n");
                LogRecorder.recordLog("Order Number: " + order.getOrderNumber()
                        + " inserted into database");

                orders = new ArrayList<Order>();
                orders = orderManager.loadData();

                System.out.println("\n\tCurrent order in Processing status: " 
                		+ orders.size() + "\n");

                if (orders != null) {
                    try {

                        OutputStream baristaOS = 
                        		baristaSocket.getOutputStream();
                        ObjectOutputStream baristaOOS = 
                        		new ObjectOutputStream(baristaOS);

                        // send orders to barista
                        baristaOOS.writeObject(orders);

                        System.out.println("\n\tOrder list has been sent to "
                        		+ "Preparation Counter\n");

                    } catch (Exception e) {
                        System.out.println("\n\tError: " + e.getMessage() 
                        	+ "\n");
                    }
                } else {
                    System.out.println("\n\tOrders retrieve is blank.\n");
                }
            } catch (Exception e) {
                System.out.println("\n\tError: " + e.getMessage() + "\n");
            }

            System.out.println("\n\tWaiting for next request\n");
        }
    }
}

package app.client.test;

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

public class BaristaGetterTest implements Runnable {
    private Socket baristaSocket = null;
    private InetAddress serverAddress = null;
    private ServerSocket serverSocket = null;
    private Socket baristaToServerSocket = null;

    List<Order> orders = new ArrayList<Order>();

    private Order order;

    public BaristaGetterTest(ServerSocket serverSocket, Socket baristaSocket, Socket baristaToServerSocket) {
        this.serverSocket = serverSocket;
        this.baristaSocket = baristaSocket;
        this.baristaToServerSocket = baristaToServerSocket;
    }

    @Override
    public void run() {
        Runnable BaristaMainMenu =  null;
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
                    //BaristaMainMenu = new DisplayListTest(serverSocket, order, baristaToServerSocket, orders);
                    BaristaMainMenu = new PreparationCounterMainMenu(serverSocket, order, baristaToServerSocket, orders);

                    baristaMainMenuThread = new Thread(BaristaMainMenu);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                

                // Runnable displayList = new DisplayListTest(serverSocket, order, baristaToServerSocket, orders);
                // Thread thread = new Thread(displayList);

                // new ArrayList<>(Arrays.asList(sos1.getValue()));
                System.out.println("Get orders object from server");
                // clearScreen();

                // Runnable displayList = new DisplayList(serverSocket, order);
                // Thread thread = new Thread(displayList);
                // thread.start();

                // order = loadOrder();

                // displayList();
                baristaMainMenuThread.start();
                

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void displayList() {

        System.out.println("\n\t1. Get Order");
        System.out.println("\t2. Exit");
        System.out.print("\n\tEnter your choice: ");
        // do {
        // // display data from Order object
        // // order.displayData();

        // // display menu
        // System.out.println("\n\t1. Get Order");
        // System.out.println("\t2. Exit");
        // System.out.print("\n\tEnter your choice: ");

        // // read user's choice
        // // int choice = Integer.parseInt(System.console().readLine());

        // Scanner sc = new Scanner(System.in);

        // int choice = sc.nextInt();

        // switch (choice) {
        // case 1:
        // // send Order object to barista
        // break;
        // case 2:
        // // exit
        // break;
        // default:
        // System.out.println("\n\tInvalid choice. Please try again.\n");
        // break;
        // }

        // } while (true);

    }

    public static void clearScreen() {
        try {
            // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Order loadOrder() {

        Order order = new Order();

        order.setOrderId(1);
        order.setOrderNumber(1001);
        order.setTransactionDate(new Date());

        ItemProduct itemProduct1 = new ItemProduct();
        itemProduct1.setItemProductId(1);
        itemProduct1.setName("Hornett Tea");
        itemProduct1.setPrice(5.00);

        ItemProduct itemProduct2 = new ItemProduct();
        itemProduct2.setItemProductId(2);
        itemProduct2.setName("Hornett Coffee");
        itemProduct2.setPrice(8.00);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrderItemId(1);
        orderItem1.setItemProduct(itemProduct1);
        orderItem1.setQuantity(2);
        orderItem1.setSubTotalAmount(10.00);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrderItemId(2);
        orderItem2.setItemProduct(itemProduct2);
        orderItem2.setQuantity(1);
        orderItem2.setSubTotalAmount(8.00);

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        order.setOrderItems(orderItems);
        // order.setTotalOrderItem(orderItems.size());
        order.setTotalOrderItem(3);
        order.setSubTotal(18.00);
        order.setServiceTax(1.00);
        order.setGrandTotal(19.00);

        return order;
    }
}

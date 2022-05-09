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
import view.PreparationCounterView;

public class PreparationCounterMainMenu implements Runnable {

    private ServerSocket serverSocket = null;
    private Socket socket = null;

    private Order order;

    private List<Order> orders = new ArrayList<Order>();

    public PreparationCounterMainMenu(ServerSocket serverSocket, Order order, Socket socket, List<Order> orders) {
        this.serverSocket = serverSocket;
        this.order = order;
        this.socket = socket;
        this.orders = orders;
    }

    @Override
    public void run() {
        PreparationCounterView preparationCounterView = new PreparationCounterView();

        Scanner sc = new Scanner(System.in);
        int optionToViewPending;
        int optionToUpdateStatus = -1;
        // TODO Auto-generated method stub
        Date date = new Date();
        System.out.println("\n\t" + date.toString() + "\n");

        try {

            // Server information
            int serverPortNo = 8088;
            InetAddress serverAddress = InetAddress.getLocalHost();

            do {

                // 1. Display main menu
                // 2. Select to view pending orders
                optionToViewPending = preparationCounterView.mainScreen();

                if (optionToViewPending == 1) {

                    // Create a multithread to always receive new list of
                    // pending orders from server
                    // Socket socket = new Socket(serverAddress, serverPortNo);
                    // InputStream is = socket.getInputStream();
                    // ObjectInputStream ois = new ObjectInputStream(is);
                    do {
                        // Display latest list of pending orders
                        // clearScreen();
                        ClearScreen.ClearConsole();
                        System.out.println("\n\t--- Pending Orders ---\n"
                                + "\tOrder Number\t\t\tQuantity");
                        // while (ois.available() > 0) {

                        // order = (Order) ois.readObject();
                        // preparationCounterView.displayOrders(order);
                        preparationCounterView.displayOrders(orders);

                        // System.out.println("\t" + order.getOrderNumber() +
                        // "\t\t\t" + order.getTotalOrderItem());
                        // }

                        // 3. Select Order Number to view order details
                        System.out.println("\n\tEnter Order Number or "
                                + "0 to return to main menu: ");
                        System.out.print("\t>> ");
                        int optionToViewDetails = sc.nextInt();

                        if (optionToViewDetails == 0)
                            break;
                        else {
                            // Print stickers
                            // clearScreen();
                            ClearScreen.ClearConsole();
                            Order updatedOrder = preparationCounterView.printSticker(optionToViewDetails);

                            if (updatedOrder == null)
                                continue;

                            // 4. Select to update order status
                            System.out.println("\tEnter 1 to update order status"
                                    + " or 0 to go back to previous menu: ");
                            System.out.print("\t>> ");
                            optionToUpdateStatus = sc.nextInt();

                            if (optionToUpdateStatus == 1) {

                                // 5. Set order item status to "Ready"
                                // 6. Send Order object to server
                                // Socket socket = new Socket(serverAddress, serverPortNo);
                                // OutputStream outStream = socket.getOutputStream();
                                // ObjectOutputStream oos = new ObjectOutputStream(outStream);
                                // oos.writeObject(updatedOrder);

                                // InetAddress serverAddress = InetAddress.getLocalHost();

                                // remove updated order from list
                                orders.remove(updatedOrder);

                                socket = new Socket(serverAddress, 8085);

                                // order.setOrderId(2);
                                OutputStream outStream = socket.getOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(outStream);
                                oos.writeObject(updatedOrder);
                                System.out.println("Order updated");

                                socket.close();
                                outStream.flush();
                                oos.flush();
                                outStream.close();
                                oos.close();

                                // 7. Redirect to main menu
                                // clearScreen();
                                ClearScreen.ClearConsole();
                                break;

                            } else if (optionToUpdateStatus == 0) {
                                continue;

                            } else
                                break;

                        }
                    } while (optionToUpdateStatus == 0);

                } else
                    break;

            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void display() {
        System.out.println("\n\t1. Get Order");
        System.out.println("\t2. Exit");
        System.out.print("\n\tEnter your choice: ");
        Scanner sc = new Scanner(System.in);
        System.out.print(">> ");
        int choice = sc.nextInt();
        sc.nextLine();
        sc.reset();
        switch (choice) {
            case 1:
                try {
                    InetAddress serverAddress = InetAddress.getLocalHost();
                    socket = new Socket(serverAddress, 8085);

                    order.setOrderId(2);
                    OutputStream outStream = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(outStream);
                    oos.writeObject(order);
                    System.out.println("Order updated");

                    oos.flush();
                    oos.reset();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case 2:
                break;
            default:
                System.out.println("\n\tInvalid choice. Please try again.\n");
                break;
        }
    }

    public static void clearScreen() {
        try {
            // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

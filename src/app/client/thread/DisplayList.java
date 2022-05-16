package app.client.thread;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.ItemProduct;
import model.Order;
import model.OrderItem;

public class DisplayList implements Runnable {

    private ServerSocket serverSocket = null;
    private Socket socket = null;

    private Order order;

    public DisplayList(ServerSocket serverSocket, Order order) {
        this.serverSocket = serverSocket;
        this.order = order;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Date date = new Date();
        System.out.println("\n\t" + date.toString() + "\n");

        display();

        // int send = 1;

        // do {
        // // display data from Order object
        // // order.displayData();

        // // display menu
        // try {
        // socket = serverSocket.accept();
        // // OutputStream outStream = socket.getOutputStream();
        // // ObjectOutputStream oos = new ObjectOutputStream(outStream);

        // // oos.writeObject(order);
        // // oos.flush();
        // // oos.close();
        // // outStream.close();
        // // socket.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // System.out.println(
        // "\n\t1. Get Order");
        // System.out.println("\t2. Exit");
        // System.out.print("\n\tEnter your choice: ");

        // // read user's choice
        // // int choice = Integer.parseInt(System.console().readLine());

        // Scanner sc = new Scanner(System.in);

        // int choice = sc.nextInt();

        // // try {
        // // socket = serverSocket.accept();
        // // } catch (Exception e) {
        // // // TODO: handle exception
        // // }

        // switch (choice) {
        // case 1:
        // try {
        // // socket = serverSocket.accept();
        // OutputStream outStream = socket.getOutputStream();
        // ObjectOutputStream oos = new ObjectOutputStream(outStream);
        // oos.writeObject(order);

        // oos.flush();
        // oos.close();
        // outStream.close();
        // // oos.flush();
        // System.out.println("Order updated");
        // } catch (Exception e) {
        // // TODO: handle exception
        // e.printStackTrace();
        // }
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

    public void display() {
        Scanner sc = new Scanner(System.in);
        System.out.print(">> ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                try {
                    socket = serverSocket.accept();
                    order = loadOrder();
                    order.setOrderId(2);
                    OutputStream outStream = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(outStream);
                    oos.writeObject(order);

                    oos.flush();
                    oos.close();
                    outStream.close();
                    // oos.flush();
                    System.out.println("Order updated");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                // send Order object to barista

                break;
            case 2:
                // exit
                break;
            default:
                System.out.println("\n\tInvalid choice. Please try again.\n");
                break;
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
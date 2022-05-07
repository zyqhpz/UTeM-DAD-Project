package app.client.test;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.*;

/**
 * This class represent the client (cashier) app for HornettTeaOrderServerApp
 * 
 * Function: to start the client-side program for the application
 * 
 * @author haziqhapiz
 */

public class CashierAppTest {
    public static void main(String args[]) {

        System.out.println("\n\nStarting HornettTeaOrderCashierApp..\n");

        Order order;
        Socket socket;

        List<ItemProduct> itemProducts = new ArrayList<ItemProduct>();

        try {

            // Server information
            int serverPortNo = 8087;
            InetAddress serverAddress = InetAddress.getLocalHost();

            socket = new Socket(serverAddress, serverPortNo);

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

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
                            if (o instanceof ItemProduct) {
                                // Here we go!
                                ItemProduct v = (ItemProduct) o;
                                itemProducts.add(v);
                                // use v.
                            }
                        }
                    }
                }

                ois.close();
                is.close();
                socket.close();

                System.out.println("\n\t ItemProducts received \n");

            } catch (Exception e) {
                e.printStackTrace();
            }

            int send = 0;

            do {

                Scanner sc = new Scanner(System.in);
                displayItemProducts(itemProducts);
                System.out.print("\tSend? >> ");
                send = sc.nextInt();

                if (send == 1) {
                    // Socket socket = new Socket(serverAddress, serverPortNo);
                    socket = new Socket(serverAddress, serverPortNo);

                    order = loadOrder();

                    OutputStream outStream = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(outStream);
                    oos.writeObject(order);

                    System.out.println("\n\tSending Order to server\n");
                    socket.close();

                    // sc.close();
                }

                else
                    break;

            } while (send == 1);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // method to create an Order object with dummy data
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

    public static void displayItemProducts(List<ItemProduct> itemProducts) {
        System.out.println("\n\nItem products are: \n");
        for (ItemProduct itemProduct : itemProducts) {
            System.out.println(itemProduct.getName() + " " + itemProduct.getPrice());
        }
    }
}

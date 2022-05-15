package app.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import app.client.thread.ClearScreen;
import model.*;

import view.OrderCounterView;

/**
 * This class represents the client (Cashier) app for the system
 * 
 * Function: to start the client-side program for the Cashier
 * 
 * @author DaysonTai
 */

public class OrderCounterApp {

    private static int orderNumber = 1;

    public static void main(String args[]) {

        System.out.println("\n\nStarting OrderCounterApp..\n");

        Order order;
        Socket socket;

        Scanner sc = new Scanner(System.in);

        List<ItemProduct> itemProducts = new ArrayList<ItemProduct>();
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        OrderCounterView orderCounterView = new OrderCounterView();

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

                System.out.println("\n\t ItemProducts fetched from server \n");

            } catch (Exception e) {
                System.out.println("\n\t Error fetching ItemProducts from server \n");
                e.printStackTrace();
            }

            // Page for making a new order
            do {
                order = new Order();
                orderItems.clear();
                int totalOrderItem = 0;
                double subTotal = 0;
                double subTotalAmount = 0;

                int choice;

                // Ordering specific items
                do {

                    ClearScreen.ClearConsole();

                    orderCounterView.displayItemProducts(itemProducts);

                    // 2. Select menu and quantity
                    System.out.println("\n\t Enter 0 to complete this order");
                    System.out.print("\n\t Choice: ");
                    choice = sc.nextInt();

                    // continue to add beverage
                    if (choice != 0) {

                        System.out.print("\t Quantity: ");
                        int quantity = sc.nextInt();
                        totalOrderItem = totalOrderItem + quantity;

                        ItemProduct itemProduct = null;

                        for (ItemProduct product : itemProducts) {
                            if (product.getItemProductId() == choice) {
                                itemProduct = product;
                                break;
                            }
                        }

                        double itemPrice = itemProduct.getPrice();
                        subTotalAmount = quantity * itemPrice;
                        subTotal += subTotalAmount;

                        // 3. Store in ArrayList
                        OrderItem orderItem = new OrderItem();
                        orderItem.setItemProduct(itemProduct);
                        orderItem.setOrderItemId(orderNumber);
                        orderItem.setOrderStatus("Processing");
                        orderItem.setQuantity(quantity);
                        orderItem.setSubTotalAmount(subTotalAmount);

                        // Add orderItem into order
                        orderItems.add(orderItem);
                    }

                    // Customer order finish
                    // 4. Add to Order object

                    int orderId = 2000 + orderNumber;

                    java.util.Date date = new java.util.Date();
                    Timestamp transactionDate = new Timestamp(date.getTime());

                    order.setSubTotal(subTotal);
                    order.setTotalOrderItem(totalOrderItem);
                    order.setOrderItems(orderItems);
                    order.setOrderId(orderId);
                    order.setTransactionDate(transactionDate);

                    order = calculateConfirmationOrder(order);

                    // // set Order
                    // order = setOrder(orderId, orderNumber, transactionDate,
                    // orderItems, totalOrderItem, subTotal, serviceTax,
                    // rounding, grandTotal, change);

                    // display the confirmation page
                    if (choice == 0) {
                        // clear screen
                        ClearScreen.ClearConsole();

                        // display confirmation page
                        orderCounterView.confirmationPage(order);

                        int payChoice = sc.nextInt();

                        // Confirm to pay
                        if (payChoice == 1) {
                            System.out.print("\n\tTendered cash: ");
                            double tenderedCash = sc.nextDouble();

                            order.setTenderedCash(tenderedCash);

                            // 6. Send Order object to server
                            socket = new Socket(serverAddress, serverPortNo);

                            OutputStream outStream = socket.getOutputStream();
                            ObjectOutputStream oos = new ObjectOutputStream(outStream);
                            oos.writeObject(order);
                            oos.flush();

                            // 7. Receive Order object from server
                            InputStream inStream = socket.getInputStream();
                            ObjectInputStream oiStream = new ObjectInputStream(inStream);

                            order = (Order) oiStream.readObject();
                            socket.close();

                            orderCounterView.displayReceipt(order);
                        }

                        // Go back to menu
                        else if (payChoice == 2) {
                            continue;
                        }
                    }

                    sc.nextLine();

                } while (choice != 0);

                // 5. Process Order when customer do payment (initialize all variables using
                // setters)

                // 7. Print receipt
                // orderCounterView.displayReceipt(order);
                sc.nextLine();

                // add 1 to orderNumber
                orderNumber++;

            } while (true);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public static Order calculateConfirmationOrder(Order order) {

        double subTotal = order.getSubTotal();
        double serviceTax = subTotal * 0.06;
        double rounded = Math.round(serviceTax * 20.0) / 20.0;
        double rounding = 0;
        if (rounded != 0) {
            rounding = rounded - serviceTax;
        }
        double grandTotal = subTotal + serviceTax + rounding;

        order.setServiceTax(serviceTax);
        order.setRounding(rounding);
        order.setGrandTotal(grandTotal);

        return order;
    }
}
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

public class OrderCounterAppTest {

    private static int orderNumber = 1;

    public static void main(String args[]) {

        System.out.println("\n\nStarting OrderCounterApp..\n");

        Order order;
        Socket socket;

        String continueOrder = "Yes";
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
                order = null;
                int totalOrderItem = 0;
                double subTotal = 0;

                int choice;

                // Ordering specific items
                do {
                    // clear screen
                    // new ProcessBuilder("cmd", "/c", "cls").inheritIO()
                    // .start().waitFor();

                    // 1. Display menu of all beverages
                    // orderCounterView.displayOrderList(menuList);
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
                        subTotal = subTotal + quantity * itemPrice;

                        // get time
                        Calendar currentTime = Calendar.getInstance();

                        // Calculate ready time by adding current time with 5
                        // minutes

                        Calendar readyTime = Calendar.getInstance();
                        readyTime.add(Calendar.MINUTE, 5);

                        // 3. Store in ArrayList
                        OrderItem orderItem = new OrderItem();
                        // orderItem.setItemProduct(menuList[choice - 1]);
                        orderItem.setItemProduct(itemProduct);
                        orderItem.setOrderItemId(orderNumber);
                        orderItem.setOrderStatus("Processing");
                        orderItem.setQuantity(quantity);
                        orderItem.setReadyTime(readyTime.getTime());
                        orderItem.setSequenceNumber(orderNumber);
                        orderItem.setSubTotalAmount(subTotal);

                        // Add orderItem into order
                        orderItems.add(orderItem);
                    }

                    // Customer order finish
                    // 4. Add to Order object
                    // which contains List<OrderItem> orderItems;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2022, 5, 7, 20, 1, 1);

                    int orderId = 2000 + orderNumber;
                    // Date transactionDate = calendar.getTime();

                    java.util.Date date = new java.util.Date();
                    Timestamp transactionDate = new Timestamp(date.getTime());
                    // List<OrderItem> orderItems;
                    // double serviceTax = 0.05;
                    // double rounding = 0;
                    // double grandTotal = 10.05;
                    // double tenderedCash = 20;
                    // double change = 9.95;

                    // FIX THIS !!
                    double serviceTax = 0.05;
                    double rounding = 0;
                    double grandTotal = 10.05;
                    double tenderedCash = 0;
                    double change = 9.95;

                    // order = new Order(orderId, orderNumber, transactionDate,
                    // orderItems, totalOrderItem, subTotal, serviceTax,
                    // rounding, grandTotal, tenderedCash, change);

                    // set Order
                    order = setOrder(orderId, orderNumber, transactionDate,
                            orderItems, totalOrderItem, subTotal, serviceTax,
                            rounding, grandTotal, tenderedCash, change);

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
                            tenderedCash = sc.nextDouble();

                            order.setTenderedCash(tenderedCash);

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

                // 6. Send Order object to server
                // Socket socket = new Socket(serverAddress, serverPortNo);
                socket = new Socket(serverAddress, serverPortNo);

                OutputStream outStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outStream);
                oos.writeObject(order);
                socket.close();

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

    public static Order setOrder(int orderId, int orderNumber, Date transactionDate, List<OrderItem> orderItems,
            int totalOrderItem, double subTotal, double serviceTax, double rounding, double grandTotal,
            double tenderedCash, double change) {

        Order order = new Order();

        order.setOrderId(orderId);
        order.setOrderNumber(orderNumber);
        order.setTransactionDate(transactionDate);
        order.setOrderItems(orderItems);
        order.setTotalOrderItem(totalOrderItem);
        order.setSubTotal(subTotal);
        order.setServiceTax(serviceTax);
        order.setRounding(rounding);
        order.setGrandTotal(grandTotal);
        order.setTenderedCash(tenderedCash);
        order.setChange(change);

        return order;
    }
}
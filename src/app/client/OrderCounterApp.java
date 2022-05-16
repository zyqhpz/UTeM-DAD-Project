package app.client;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
 * Function: to start the client-side program for the application
 * 
 * @author DaysonTai
 */

public class OrderCounterApp {

    public static void main(String args[]) {

        System.out.println("\n\nStarting OrderCounterApp..\n");

        Order order;
        Socket socket;

        Scanner sc = new Scanner(System.in);

        List<ItemProduct> itemProducts = new ArrayList<ItemProduct>();
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        OrderCounterView orderCounterView = new OrderCounterView();

        int orderNumberIndex = 1;

        try {
            // Server information
            int serverPortNo = 8087;
            InetAddress serverAddress = InetAddress.getLocalHost();

            socket = new Socket(serverAddress, serverPortNo);

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            try {
            	// Read object from the server
                Object obj = ois.readObject();

                // Add the object into itemProduct list
                if (obj instanceof ArrayList<?>) {
                    ArrayList<?> al = (ArrayList<?>) obj;
                    if (al.size() > 0) {
                        for (int i = 0; i < al.size(); i++) {
                            Object o = al.get(i);
                            if (o instanceof ItemProduct) {
                                ItemProduct v = (ItemProduct) o;
                                itemProducts.add(v);
                            }
                        }
                    }
                }

                ois.close();
                is.close();
                socket.close();

                System.out.println("\n\t ItemProducts fetched from server \n");

            } catch (Exception e) {
                System.out.println
                	("\n\t Error fetching ItemProducts from server \n");
                e.printStackTrace();
            }

            // Page for making a new order
            do {
                order = new Order();
                orderItems.clear();
                int totalOrderItem = 0;
                double subTotal = 0;
                double subTotalAmount = 0;
                int orderNumber = 0;

                int choice;

                // Ordering specific items
                do {

                    ClearScreen.ClearConsole();

                    orderCounterView.displayItemProducts(itemProducts);

                    // 2. Select menu and quantity
                    System.out.println("\n\t Enter 0 to complete this order");
                    System.out.print("\n\t Choice: ");
                    choice = sc.nextInt();

                    orderNumber = 2000 + orderNumberIndex;

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

                    java.util.Date date = new java.util.Date();
                    Timestamp transactionDate = new Timestamp(date.getTime());

                    order.setSubTotal(subTotal);
                    order.setTotalOrderItem(totalOrderItem);
                    order.setOrderItems(orderItems);
                    order.setTransactionDate(transactionDate);
                    order.setOrderNumber(orderNumber);

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
                            ObjectOutputStream oos = 
                            		new ObjectOutputStream(outStream);
                            oos.writeObject(order);
                            oos.flush();

                            // 7. Receive Order object from server
                            InputStream inStream = socket.getInputStream();
                            ObjectInputStream oiStream = 
                            		new ObjectInputStream(inStream);

                            order = (Order) oiStream.readObject();
                            socket.close();

                            generateReceiptInTextFile(order);
                            orderCounterView.displayReceipt(order);
                        }

                        // Go back to menu
                        else if (payChoice == 2) {
                            continue;
                        }
                    }

                    sc.nextLine();

                } while (choice != 0);

                // 5. Process Order when customer do payment 
                // (initialize all variables using setters)

                // 7. Print receipt
                // orderCounterView.displayReceipt(order);
                sc.nextLine();

                // add 1 to orderNumber
                orderNumberIndex++;

            } while (true);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * This method calculate grand total of the order
     * 
     * @param order
     * @return
     */
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

    // print receipt in text file and display on console screen
    public static void generateReceiptInTextFile(Order order) {

        try {

            Date date = order.getTransactionDate();
            String transactionDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);

            // get epoch time of order date
            long epochTime = order.getTransactionDate().getTime();

            FileWriter myWriter = new FileWriter("receipts/" + epochTime + "-" + order.getOrderNumber() + ".txt");
            // myWriter.write("Files in Java might be tricky, but it is fun enough!");
            // myWriter.close();

            myWriter.write("-----------------------------------------");
            myWriter.write("\n");
            myWriter.write("Your order number is: " + order.getOrderNumber());
            myWriter.write("\n");
            myWriter.write("-----------------------------------------");
            myWriter.write("\n");
            myWriter.write("HornetTea");
            myWriter.write("\n");
            myWriter.write("FICTS");
            myWriter.write("\n");
            myWriter.write("Fakulti Teknologi Maklumat dan Komunikasi");
            myWriter.write("\n");
            myWriter.write("Universiti Teknikal Malaysia Melaka");
            myWriter.write("\n");
            myWriter.write("Hang Tuah Jaya, 76100 Durian Tunggal");
            myWriter.write("\n");
            myWriter.write("Melaka, Malaysia");
            myWriter.write("\n");
            myWriter.write("-----------------------------------------");
            myWriter.write("\n");
            myWriter.write("Invoice");
            myWriter.write("\n\n");
            myWriter.write("Bill No: " + epochTime);
            myWriter.write("\n");
            myWriter.write("Date: " + transactionDate + "\n");
            myWriter.write("\n");
            myWriter.write("Details");
            myWriter.write("\n");
            myWriter.write("-----------------------------------------");
            myWriter.write("\n");
            myWriter.write(String.format("%-22s %5s %7s", "Item Name", "Qty", "Price(RM)"));
            myWriter.write("\n");
            myWriter.write("-----------------------------------------");
            myWriter.write("\n");

            for (int i = 0; i < order.getOrderItems().size(); i++) {
                String labelName = order.getOrderItems().get(i).getItemProduct()
                        .getLabelName();
                int quantity = order.getOrderItems().get(i).getQuantity();
                double price = order.getOrderItems().get(i).getItemProduct()
                        .getPrice();

                String itemTotalPrice = String.format("%.2f", quantity * price);
                myWriter.write(String.format("%-22s %5s %7s", labelName, quantity,
                        itemTotalPrice));
                myWriter.write("\n");
            }

            myWriter.write("-----------------------------------------");
            myWriter.write("\n");

            int totalItem = order.getTotalOrderItem();
            double subTotal = order.getSubTotal();
            double serviceTax = order.getServiceTax();
            double grandTotal = order.getGrandTotal();
            double rounding = order.getRounding();
            double tenderedCash = order.getTenderedCash();
            double change = order.getChange();

            myWriter.write(String.format("%-25s %-7s", "Total Item",
                    totalItem));
            myWriter.write("\n");
            myWriter.write(String.format("%35s %.2f", "Sub total", subTotal));
            myWriter.write("\n");
            myWriter.write(String.format("%35s %.2f", "Service Tax (6%)",
                    serviceTax));
            myWriter.write("\n");
            myWriter.write(String.format("%35s %.2f", "Rounding", rounding));
            myWriter.write("\n");
            myWriter.write("-----------------------------------------");

            myWriter.write("\n");
            myWriter.write(String.format("%35s %.2f", "Grand Total",
                    grandTotal));
            myWriter.write("\n");
            myWriter.write(String.format("%35s %.2f", "Tendered Cash",
                    tenderedCash));
            myWriter.write("\n");
            myWriter.write(String.format("%35s %.2f", "Change", change));
            myWriter.write("\n");
            myWriter.write("-----------------------------------------\n");
            myWriter.write("\n");
            myWriter.write("\tThank you and have a good day");
            myWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
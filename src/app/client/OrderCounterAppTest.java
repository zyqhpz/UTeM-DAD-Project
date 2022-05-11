package app.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

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

    private static OrderCounterView orderCounterView;
    private static int orderNumber = 1;

    public static void main(String args[]) {

        System.out.println("\n\nStarting OrderCounterApp..\n");

        Order order;
        Socket socket;

        String continueOrder = "Yes";
        Scanner sc = new Scanner(System.in);

        List<ItemProduct> itemProducts = new ArrayList<ItemProduct>();
        List<OrderItem> orderItems = new ArrayList<OrderItem>();

        // This array contains Objects of the item product
        ItemProduct[] menuList = new ItemProduct[30];

        Integer[] idList = {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21
        };

        String[] nameList = {
                "(Cold) Signature Brown Sugar Pearl Milk Tea",
                "(Hot) Signature Brown Sugar Pearl Milk Tea",
                "(Cold) Original Pearl Milk Tea",
                "(Hot) Original Pearl Milk Tea",
                "(Cold) Black Diamond Milk Tea",
                "(Cold) Red Bean Pearl Milk Tea",
                "(Hot) Red Bean Pearl Milk Tea",
                "(Cold) Earl Grey Milk Tea",
                "(Hot) Earl Grey Milk Tea",
                "(Cold) Signature Milk Tea",
                "(Hot) Signature Milk Tea",
                "(Cold) Original Milk Tea",
                "(Hot) Original Milk Tea",
                "(Cold) Signature Coffee",
                "(Hot) Signature Coffee",
                "(Cold) Coco Mocha",
                "(Hot) Coco Mocha",
                "(Cold) Hazelnut Latte",
                "(Hot) Hazelnut Latte",
                "(Cold) Americano",
                "(Hot) Americano"
        };

        String[] labelNameList = {
                "(Cold) Sig BrSg Prl",
                "(Hot) Sig BrSg Prl",
                "(Cold) Pearl Mlk",
                "(Hot) Pearl Mlk",
                "(Cold) Blk Dmd Mlk",
                "(Hot) Blk Dmd Mlk",
                "(Hot) Red Bn Prl Mlk",
                "(Cold) Erl Gry Mlk",
                "(Hot) Erl Gry Mlk",
                "(Cold) Sig Mlk",
                "(Hot) Sig Mlk",
                "(Cold) Org Mlk",
                "(Hot) Org Mlk",
                "(Cold) Sig Coff",
                "(Hot) Sig Coff",
                "(Cold) Coco Mocha",
                "(Hot) Coco Mocha",
                "(Cold) Hznut Latte",
                "(Hot) Hznut Latte",
                "(Cold) Americano",
                "(Hot) Americano"
        };

        Double[] priceList = {
                6.50,
                7.45,
                6.50,
                7.45,
                7.50,
                7.45,
                8.35,
                6.50,
                7.45,
                5.55,
                6.50,
                5.55,
                6.50,
                8.35,
                8.35,
                8.35,
                8.35,
                8.35,
                8.35,
                7.45,
                7.45
        };

        // Fill the array with item objects
        // for (int i = 0; i < 21; i++) {
        // menuList[i] = new ItemProduct(i + 1, nameList[i], labelNameList[i],
        // priceList[i]);

        // }

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
                int totalOrderItem = 0;
                double subTotal = 0;

                int choice;

                // Ordering specific items
                do {
                    // clear screen
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO()
                            .start().waitFor();

                    // 1. Display menu of all beverages
                    // orderCounterView.displayOrderList(menuList);
                    orderCounterView.displayItemProductLst(itemProducts);

                    // 2. Select menu and quantity
                    System.out.println("\n\t Enter 0 to complete this order");
                    System.out.print("\n\t Choice: ");
                    choice = sc.nextInt();

                    // continue to add beverage
                    if (choice != 0) {

                        System.out.print("\t Quantity: ");
                        int quantity = sc.nextInt();
                        totalOrderItem = totalOrderItem + quantity;
                        double itemPrice = priceList[choice - 1];
                        subTotal = subTotal + quantity * itemPrice;

                        // get time
                        Calendar currentTime = Calendar.getInstance();

                        // Calculate ready time by adding current time with 5
                        // minutes

                        Calendar readyTime = Calendar.getInstance();
                        readyTime.add(Calendar.MINUTE, 5);

                        // Get ItemProduct where itemProductId = choice

                        ItemProduct itemProduct = itemProducts.get(choice - 1);

                        // 3. Store in ArrayList
                        OrderItem orderItem = new OrderItem();
                        orderItem.setItemProduct(menuList[choice - 1]);
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
                    Date transactionDate = calendar.getTime();
                    // List<OrderItem> orderItems;
                    // double serviceTax = 0.05;
                    // double rounding = 0;
                    // double grandTotal = 10.05;
                    // double tenderedCash = 20;
                    // double change = 9.95;

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
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO()
                                .start().waitFor();

                        // display confirmation page
                        orderCounterView.confirmationPage(order);

                        int payChoice = sc.nextInt();

                        // Confirm to pay
                        if (payChoice == 1) {
                            System.out.print("\n\tTendered cash: ");
                            tenderedCash = sc.nextDouble();

                            orderCounterView.displayReceipt(order);
                        }

                        // Go back to menu
                        else if (payChoice == 2) {
                            orderCounterView.displayOrderList(menuList);
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
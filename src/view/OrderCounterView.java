package view;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// import app.client.CashierApp;
import model.ItemProduct;
import model.Order;
import model.OrderItem;

public class OrderCounterView {

    static Scanner scanner = new Scanner(System.in);

    public void displayItemProducts(List<ItemProduct> itemProducts) {
        System.out.println("\t\t-----Beverages-----\n");
        System.out.println("+----+---------------------------------------------+------------+");
        System.out.println("| ID | Product Name                                | Price (RM) |");
        System.out.println("+----+---------------------------------------------+------------+");

        int i = 0;
        for (ItemProduct itemProduct : itemProducts) {
            System.out.println(
                    String.format("%-1s %-2s %-1s %-43s %-1s %.2f %-2s", "|", itemProduct.getItemProductId(), "|",
                            itemProduct.getName(), "|", itemProduct.getPrice(), "      |"));
        }
    }

    public void displayReceipt(Order order) {
        // clear screen
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("-----------------------------------------");
        System.out.println("Your order number is: " + order.getOrderNumber());
        System.out.println("-----------------------------------------");
        System.out.println("HornetTea");
        System.out.println("FICTS");
        System.out.println("Fakulti Teknologi Maklumat dan Komunikasi");
        System.out.println("Universiti Teknikal Malaysia Melaka");
        System.out.println("Hang Tuah Jaya, 76100 Durian Tunggal");
        System.out.println("Melaka, Malaysia");
        System.out.println("-----------------------------------------");
        System.out.println("Invoice\n");
        System.out.println("Bill No: ");
        System.out.println("Date: \n");
        System.out.println("Details");
        System.out.println("-----------------------------------------");
        System.out.printf("%-22s %5s %7s", "Item Name", "Qty", "Price(RM)\n");
        System.out.println("-----------------------------------------");

        for (int i = 0; i < order.getOrderItems().size(); i++) {
            String labelName = order.getOrderItems().get(i).getItemProduct()
                    .getLabelName();
            int quantity = order.getOrderItems().get(i).getQuantity();
            double price = order.getOrderItems().get(i).getItemProduct()
                    .getPrice();

            String itemTotalPrice = String.format("%.2f", quantity * price);
            System.out.printf("%-22s %5s %7s", labelName, quantity,
                    itemTotalPrice);
            System.out.println("\n");
        }

        System.out.println("-----------------------------------------");
        int totalItem = order.getTotalOrderItem();
        double subTotal = order.getSubTotal();
        double serviceTax = order.getServiceTax();

        double grandTotal = subTotal + serviceTax;

        double grandTotalRounded = Math.round(grandTotal * 20) / 20.0;

        double rounding = grandTotalRounded - grandTotal;
        double tenderedCash = order.getTenderedCash();
        double change = tenderedCash - grandTotalRounded;

        System.out.println(String.format("%-25s %-7s", "Total Item",
                totalItem));
        System.out.println("\n");
        System.out.println(String.format("%35s %.2f", "Sub total", subTotal));
        System.out.println(String.format("%35s %.2f", "Service Tax (6%)",
                serviceTax));
        System.out.println(String.format("%35s %.2f", "Rounding", rounding));
        System.out.println("-----------------------------------------");

        System.out.println(String.format("%35s %.2f", "Grand Total",
                grandTotalRounded));
        System.out.println("\n");
        System.out.println(String.format("%35s %.2f", "Tendered Cash",
                tenderedCash));
        System.out.println(String.format("%35s %.2f", "Change", change));

        System.out.println("-----------------------------------------\n");
        System.out.println("\tThank you and have a good day");

        System.out.println("\n\tEnter 0 for next order");
    }

    public void confirmationPage(Order order) {
        System.out.println("\n\tConfirmation Page");
        System.out.println("-----------------------------------------");
        System.out.printf("%-22s %5s %-7s", "Item Name", "Qty", "Price (RM)\n");
        System.out.println("-----------------------------------------");

        // Print all items, and their quantity and price
        for (int i = 0; i < order.getOrderItems().size(); i++) {
            int itemID = order.getOrderItems().get(i).getOrderItemId();
            // String labelName =
            // order.getOrderItems().get(i).getItemProduct().getLabelName();
            // String labelName = labelNameList[itemID];
            String labelName = order.getOrderItems().get(i).getItemProduct().getLabelName();

            int quantity = order.getOrderItems().get(i).getQuantity();
            // double price = order.getOrderItems().get(i).getItemProduct().getPrice();
            double price = order.getOrderItems().get(i).getItemProduct().getPrice();
            System.out.println(String.format("%-25s %-7s %.2f", labelName, quantity, quantity * price));

        }

        System.out.println("-----------------------------------------");
        int totalItem = order.getTotalOrderItem();
        double subTotal = order.getSubTotal();
        double serviceTax = order.getServiceTax();

        double grandTotal = subTotal + serviceTax;
        double grandTotalRounded = Math.floor(grandTotal);
        double rounding = grandTotalRounded - grandTotal;
        double tenderedCash = order.getTenderedCash();
        double change = tenderedCash - grandTotalRounded;

        System.out.println(String.format("%-25s %-7s", "Total Item",
                totalItem));
        System.out.println(String.format("%35s %.2f", "Sub total", subTotal));
        System.out.println(String.format("%35s %.2f", "Service Tax (6%)", serviceTax));
        System.out.println(String.format("%35s %.2f", "Rounding", rounding));
        System.out.println("-----------------------------------------");

        System.out.println(String.format("%35s %.2f", "Grand Total", grandTotalRounded));
        System.out.println("-----------------------------------------\n");

        System.out.println("\t1. Pay Cash");
        System.out.println("\t2. Back to menu");
        System.out.print("\tChoice: ");

    }
}
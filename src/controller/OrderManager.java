package controller;

import java.util.Date;
import java.util.List;

import model.*;

public class OrderManager {

    private Order order;

    public OrderManager(Order order) {
        this.order = order;
    }

    // private void processItemProducts() {
    // // process item products to insert data in database

    // List<OrderItem> orderItems = order.getOrderItems();

    // for (OrderItem orderItem : orderItems) {
    // int itemProductId = orderItem.getItemProduct().getItemProductId();
    // int quantity = orderItem.getQuantity();
    // double price = orderItem.getItemProduct().getPrice();
    // double subTotalAmount = orderItem.getSubTotalAmount();
    // }
    // }

    public void displayData() {
        System.out.println("Order Id: " + order.getOrderId());
        System.out.println("Order Number: " + order.getOrderNumber());
        System.out.println("TransactionDate: " + order.getTransactionDate());
        System.out.println("Total Order Item: " + order.getTotalOrderItem());
        System.out.println("Sub Total: " + order.getSubTotal());
        System.out.println("Service Tax: " + order.getServiceTax());
        System.out.println("Rounding: " + order.getRounding());
        System.out.println("Grand Total: " + order.getGrandTotal());
        System.out.println("Tendered Cash: " + order.getTenderedCash());
        System.out.println("Change: " + order.getChange());

        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            System.out.println("Order Item Id: " + orderItem.getOrderItemId());
            System.out.println("Item Product Id: " + orderItem.getItemProduct().getItemProductId());
            System.out.println("Quantity: " + orderItem.getQuantity());
            System.out.println("Sub Total Amount: " + orderItem.getSubTotalAmount());
            System.out.println("Sequence Number: " + orderItem.getSequenceNumber());
            System.out.println("Order Status: " + orderItem.getOrderStatus());
            System.out.println("Ready Time: " + orderItem.getReadyTime());
        }
    }
}

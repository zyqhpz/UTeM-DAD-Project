package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
// import java.sql.Date;
import java.util.List;

import model.*;
import controller.database.Database;

public class OrderManager {

    private Order order;
    private Database db;
    private Connection conn;

    public OrderManager(Order order) {
        this.order = order;
    }

    // insert data into database
    public void orderProcessor() throws ClassNotFoundException, SQLException {

        // connect to database
        Database database = new Database();
        Connection conn = database.doConnection();

        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            int itemProductId = orderItem.getItemProduct().getItemProductId();
            int quantity = orderItem.getQuantity();
            double price = orderItem.getItemProduct().getPrice();
            double subTotalAmount = orderItem.getSubTotalAmount();
        }
    }

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

    /* Insert Order data into database
     * 
     */
    public void insertData() throws ClassNotFoundException, SQLException {
        db = new Database();
        conn = db.doConnection();

        OrderItem orderItem = new OrderItem();

        insertDataOrder();



        conn.close();
    }

    public void insertDataOrder() throws SQLException {

        // prepare statement
        int orderId;
        int orderNumber;
        Date transactionDate;
        int totalOrderItem;
        double subTotal;
        double serviceTax;
        double rounding;
        double grandTotal;
        double tenderedCash;
        double change;

        String sql = "INSERT INTO order (orderId, orderNumber, transactionDate, totalOrderItem, subTotal, serviceTax, rounding, grandTotal, tenderedCash, change) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        // set the values
        orderId = order.getOrderId();
        orderNumber = order.getOrderNumber();
        transactionDate = order.getTransactionDate();
        totalOrderItem = order.getTotalOrderItem();
        subTotal = order.getSubTotal();
        serviceTax = order.getServiceTax();
        rounding = order.getRounding();
        grandTotal = order.getGrandTotal();
        tenderedCash = order.getTenderedCash();
        change = order.getChange();

        java.sql.Date sqlTransactionDate = new java.sql.Date(transactionDate.getTime());

        pstmt.setInt(1, orderId);
        pstmt.setInt(2, orderNumber);
        // pstmt.setDate(3, transactionDate);
        pstmt.setDate(3, sqlTransactionDate);
        pstmt.setInt(4, totalOrderItem);
        pstmt.setDouble(5, subTotal);
        pstmt.setDouble(6, serviceTax);
        pstmt.setDouble(7, rounding);
        pstmt.setDouble(8, grandTotal);
        pstmt.setDouble(9, tenderedCash);
        pstmt.setDouble(10, change);

        // execute the statement
        pstmt.executeUpdate();

        // close the connection
        pstmt.close();
    }
    
    public void insertDataOrderItem() throws ClassNotFoundException, SQLException {
        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            int orderItemId;
            int itemProductId;
            int quantity;
            double subTotalAmount;
            int sequenceNumber;
            // String orderStatus;
            // Date readyTime;

            String sql = "INSERT INTO orderItem (orderItemId, orderId, itemProductId, quantity, subTotalAmount, sequenceNumber) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            // set the values
            orderItemId = orderItem.getOrderItemId();
            itemProductId = orderItem.getItemProduct().getItemProductId();
            quantity = orderItem.getQuantity();
            subTotalAmount = orderItem.getSubTotalAmount();
            sequenceNumber = orderItem.getSequenceNumber();
            // orderStatus = orderItem.getOrderStatus();
            // readyTime = orderItem.getReadyTime();

            pstmt.setInt(1, orderItemId);
            pstmt.setInt(2, order.getOrderId());
            pstmt.setInt(3, itemProductId);
            pstmt.setInt(4, quantity);
            pstmt.setDouble(5, subTotalAmount);
            pstmt.setInt(6, sequenceNumber);

            // execute the statement
            pstmt.executeUpdate();

            // close the connection
            pstmt.close();
        }
    }
    
    /*
     * update order status of each order items
     * 
     * @param order - Order object
     */

    public void updateOrderStatus(Order order) throws ClassNotFoundException, SQLException {
        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            int orderItemId = orderItem.getOrderItemId();
            String orderStatus = orderItem.getOrderStatus();
            Date readyTime = orderItem.getReadyTime();

            String sql = "UPDATE orderItem SET orderStatus = ?, readyTime = ? WHERE orderItemId = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, orderStatus);
            pstmt.setDate(2, new java.sql.Date(readyTime.getTime()));
            pstmt.setInt(3, orderItemId);

            // execute the statement
            pstmt.executeUpdate();

            // close the connection
            pstmt.close();
        }
    }
}

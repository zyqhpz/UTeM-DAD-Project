package controller;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
// import java.sql.Date;
import java.util.List;

import model.*;
import controller.database.Database;

public class OrderManager {

    private Connection conn;

    public OrderManager() {
    }

    public List<Order> loadData() throws ClassNotFoundException, SQLException {
        conn = Database.doConnection();

        String sql = "SELECT * FROM `order` JOIN orderitem ON orderitem.Order = `order`.OrderId WHERE orderitem.OrderStatus = 'Processing' GROUP BY `order`.OrderId";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        List<Order> orders = new ArrayList<Order>();

        ResultSet rs = pstmt.executeQuery();
        // if rs is not null, then there are some orders in the database
        while (rs != null && rs.next()) {
            Order order = new Order();
            System.out.println("Order Id: " + rs.getInt("OrderId"));
            order.setOrderId(rs.getInt("OrderId"));
            OrderItemManager orderItemManager = new OrderItemManager();
            List<OrderItem> orderItems = orderItemManager.loadOrderItem(order.getOrderId());
            order.setOrderItems(orderItems);
            order.setOrderNumber(rs.getInt("OrderNumber"));
            // get date from database
            java.sql.Timestamp ts = rs.getTimestamp("TransactionDate");
            java.util.Date date = new java.util.Date(ts.getTime());
            order.setTransactionDate(date);
            order.setTotalOrderItem(rs.getInt("TotalOrderItem"));
            order.setSubTotal(rs.getDouble("SubTotal"));
            order.setServiceTax(rs.getDouble("ServiceTax"));
            order.setRounding(rs.getDouble("Rounding"));
            order.setGrandTotal(rs.getDouble("GrandTotal"));
            order.setTenderedCash(rs.getDouble("TenderedCash"));
            order.setChange(rs.getDouble("Change"));

            orders.add(order);
        }

        // close the connection
        conn.close();

        return orders;
    }

    /*
     * Insert Order data into database
     * 
     */
    public void insertDataOrder(Order order) throws SQLException, ClassNotFoundException {

        conn = Database.doConnection();

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

        // String sql = "INSERT INTO `order` (OrderNumber, TransactionDate,
        // TotalOrderItem, SubTotal, ServiceTax, Rounding, GrandTotal, TenderedCash,
        // `Change`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sql = "INSERT INTO `order` (OrderNumber, TotalOrderItem, SubTotal, ServiceTax, Rounding, GrandTotal, TenderedCash, `Change`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        // set the values
        orderNumber = order.getOrderNumber();
        transactionDate = order.getTransactionDate();
        totalOrderItem = order.getTotalOrderItem();
        subTotal = order.getSubTotal();
        serviceTax = order.getServiceTax();
        rounding = order.getRounding();
        grandTotal = order.getGrandTotal();
        tenderedCash = order.getTenderedCash();
        change = order.getChange();

        // convert transactionDate to sql.Date
        java.sql.Date sqlDate = new java.sql.Date(transactionDate.getTime());

        pstmt.setInt(1, orderNumber);
        pstmt.setInt(2, totalOrderItem);
        pstmt.setDouble(3, subTotal);
        pstmt.setDouble(4, serviceTax);
        pstmt.setDouble(5, rounding);
        pstmt.setDouble(6, grandTotal);
        pstmt.setDouble(7, tenderedCash);
        pstmt.setDouble(8, change);

        // execute the statement
        pstmt.executeUpdate();

        // get the generated key
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            orderId = rs.getInt(1);
            order.setOrderId(orderId);
        }

        // close the connection
        pstmt.close();
        conn.close();

        // insert order item
        insertDataOrderItem(order);
    }

    public void insertDataOrderItem(Order order) throws ClassNotFoundException, SQLException {
        List<OrderItem> orderItems = order.getOrderItems();

        conn = Database.doConnection();

        for (OrderItem orderItem : orderItems) {
            int itemProductId;
            int quantity;
            double subTotalAmount;
            // String orderStatus;
            // Date readyTime;

            String sql = "INSERT INTO orderItem (ItemProduct, `Order`, Quantity, SubTotalAmount) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            // set the values
            itemProductId = orderItem.getItemProduct().getItemProductId();
            quantity = orderItem.getQuantity();
            subTotalAmount = orderItem.getSubTotalAmount();

            pstmt.setInt(1, itemProductId);
            pstmt.setInt(2, order.getOrderId());
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, subTotalAmount);

            // execute the statement
            pstmt.executeUpdate();

            // close the connection
            pstmt.close();
            conn.close();
        }
    }

    /*
     * update order status of each order items
     * 
     * @param order - Order object
     */

    public void updateOrderStatus(Order order) throws ClassNotFoundException, SQLException {
        List<OrderItem> orderItems = order.getOrderItems();

        conn = Database.doConnection();

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
        conn.close();
    }

    public Order calculateChange(Order order) {
        double change = order.getTenderedCash() - order.getGrandTotal();
        order.setChange(change);

        return order;
    }
}

package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.*;
import controller.database.Database;

/**
 * This is the controller class for Order.
 * 
 * @author HaziqHapiz
 *
 */
public class OrderManager {

    private Connection conn;

    public OrderManager() {
    }

    /**
     * This method retrieve orders that still in Processing status from the database
     * into an order list.
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<Order> loadProcessingOrders() throws ClassNotFoundException, SQLException {

        conn = Database.doConnection();

        String sql = "SELECT * FROM `order` JOIN orderitem ON "
                + "orderitem.Order = `order`.OrderId "
                + "WHERE orderitem.OrderStatus = 'Processing' "
                + "GROUP BY `order`.OrderId";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        List<Order> orders = new ArrayList<Order>();

        ResultSet rs = pstmt.executeQuery();

        // if rs is not null, then there are some orders in the database
        while (rs != null && rs.next()) {

            Order order = new Order();
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

        conn.close();

        return orders;
    }

    /**
     * This method add new order into database.
     * 
     * @param order
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void insertDataOrder(Order order)
            throws SQLException, ClassNotFoundException {

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

        String sql = "INSERT INTO `order` (OrderNumber, TotalOrderItem, "
                + "SubTotal, ServiceTax, Rounding, GrandTotal, TenderedCash, "
                + "`Change`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
        OrderItemManager orderItemManager = new OrderItemManager();
        orderItemManager.insertDataOrderItem(order);
    }

    /**
     * This method calculate the change of payment made by customer.
     * 
     * @param order
     * @return order
     */
    public Order calculateChange(Order order) {
        double change = order.getTenderedCash() - order.getGrandTotal();
        order.setChange(change);

        return order;
    }
}

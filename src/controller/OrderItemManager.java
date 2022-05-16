package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import controller.database.Database;
import model.Order;
import model.OrderItem;

/**
 * This is a controller class for OrderItem.
 * 
 * @author HaziqHapiz
 */

public class OrderItemManager {

	private Connection conn;
	private OrderItem orderItem;

	/**
	 * This method update the orderStatus and readyTime of the order
	 * in database.
	 * 
	 * @param order
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateOrderStatus(Order order)
			throws ClassNotFoundException, SQLException {

		// Update query OrderStatus = 'Ready' and ReadyTime = current time
		String sql = "UPDATE orderitem SET OrderStatus = ?, ReadyTime = ? "
				+ "WHERE `Order` = ?";

		List<OrderItem> orderItems = order.getOrderItems();
		orderItems.size();

		int orderId = order.getOrderId();

		// set ReadyTime to current time
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp currentTime = new java.sql.Timestamp(date.getTime());

		Connection conn = Database.doConnection();

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, "Ready");
		preparedStatement.setTimestamp(2, currentTime);
		preparedStatement.setInt(3, orderId);

		preparedStatement.executeUpdate();

		conn.close();
	}

	public void insertDataOrderItem(Order order)
			throws ClassNotFoundException, SQLException {
		List<OrderItem> orderItems = order.getOrderItems();

		conn = Database.doConnection();

		for (OrderItem orderItem : orderItems) {
			int itemProductId;
			int quantity;
			double subTotalAmount;

			String sql = "INSERT INTO orderItem (ItemProduct, `Order`, "
					+ "Quantity, SubTotalAmount) VALUES (?, ?, ?, ?)";

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
		}

		conn.close();
	}

	/**
	 * This method retrieve OrderItem from database when given orderId.
	 * 
	 * @param orderId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<OrderItem> loadOrderItem(int orderId)
			throws ClassNotFoundException, SQLException {

		List<OrderItem> orderItems = new ArrayList<OrderItem>();

		String sql = "SELECT * FROM orderitem WHERE `Order` = ?";

		Connection conn = Database.doConnection();

		ItemProductManager itemProductManager = new ItemProductManager();

		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, orderId);
			java.sql.ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				orderItem = new OrderItem();
				orderItem.setOrderItemId(resultSet.getInt("OrderItem"));
				int itemProductId = resultSet.getInt("ItemProduct");
				itemProductManager.getItemProduct(itemProductId);
				orderItem.setItemProduct(itemProductManager.getItemProduct(itemProductId));
				orderItem.setQuantity(resultSet.getInt("Quantity"));
				orderItem.setOrderStatus(resultSet.getString("OrderStatus"));

				orderItems.add(orderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		conn.close();

		return orderItems;
	}
}

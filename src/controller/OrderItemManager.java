package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import controller.database.Database;
import model.Order;
import model.OrderItem;

public class OrderItemManager {

	private OrderItem orderItem;

	public void updateOrderStatus(Order order) throws ClassNotFoundException, SQLException {

		// Update query OrderStatus = 'Ready' and ReadyTime = current time
		String sql = "UPDATE orderitem SET OrderStatus = ?, ReadyTime = ? WHERE `Order` = ?";

		List<OrderItem> orderItems = order.getOrderItems();
		orderItems.size();

		int orderId = order.getOrderId();

		// set ReadyTime to current time
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp currentTime = new java.sql.Timestamp(date.getTime());

		// connect to database
		Connection conn = Database.doConnection();

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, "Ready");
		preparedStatement.setTimestamp(2, currentTime);
		preparedStatement.setInt(3, orderId);

		preparedStatement.executeUpdate();

		conn.close();
	}

	public List<OrderItem> loadOrderItem(int orderId) throws ClassNotFoundException, SQLException {
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

		// close the connection
		conn.close();

		return orderItems;
  }
}

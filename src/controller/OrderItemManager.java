package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.database.Database;
import model.OrderItem;

public class OrderItemManager {

	private OrderItem orderItem;

	public void updateOrderStatus(int orderItemID)
			throws ClassNotFoundException, SQLException {
		String sql = "UPDATE orderitem SET orderstatus = ? WHERE orderitemid = ?";

		Connection conn = Database.doConnection();

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, "Ready");
		preparedStatement.setInt(2, orderItemID);

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

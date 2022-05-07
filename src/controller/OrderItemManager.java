package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}

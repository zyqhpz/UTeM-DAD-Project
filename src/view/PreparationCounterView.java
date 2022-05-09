package view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import controller.OrderItemManager;
import controller.OrderManager;
import controller.database.Database;
import model.Order;
import model.OrderItem;

public class PreparationCounterView {

	// private List<Order> orderList = new ArrayList<Order>();
	private List<Order> orders = new ArrayList<Order>();
	Scanner sc = new Scanner(System.in);

	// Main Screen
	public int mainScreen() {

		System.out.println("\t--- Preparation Counter ---\n");
		System.out.println("\t1. View pending orders\n");
		System.out.println("\tEnter choice: ");
		System.out.print("\t>> ");

		return sc.nextInt();
	}

	// This method displays the latest order details at the preparation counter
	public void displayOrders(Order order) {

		orders.add(order);

		System.out.println("\t" + order.getOrderNumber() + "\t\t\t\t" +
				order.getTotalOrderItem());

	}

	// This method displays the latest order details at the preparation counter
	public void displayOrders(List<Order> orders) {

		this.orders = orders;

		for (Order order : orders) {
			System.out.println("\t" + order.getOrderNumber() + "\t\t\t\t"
					+ order.getTotalOrderItem());
		}

		// orderList.add(order);

		// System.out.println("\t" + order.getOrderNumber() + "\t\t\t\t"
		// + order.getTotalOrderItem());

	}

	public Order printSticker(int orderNumberByUser) {

		int orderNumber;
		List<OrderItem> orderItem;
		boolean orderExist = false;

		for (Order order : orders) {
			if (order.getOrderNumber() == orderNumberByUser) {
				orderNumber = order.getOrderNumber();
				orderItem = order.getOrderItems();

				Date date = new Date();
				date = order.getTransactionDate();

				for (OrderItem orderItems : orderItem) {
					System.out.println("\n\t----------------------------------"
							+ "----------------");
					System.out.println("\tHornettTea FTMK UTeM");
					System.out.println("\tOrder Number: " + orderNumber);
					System.out.println("\tDate: " + date + "\n");
					System.out.println("\tName: \n\t" +
							orderItems.getItemProduct().getName() + "\n");
					System.out.println("\tSequence: " +
							orderItems.getSequenceNumber());
					System.out.println("\t----------------------------------"
							+ "----------------\n");

					// FIX : ssdont set order status to ready
					orderItems.setOrderStatus("Ready");

					/*
					 * OrderItemManager orderItemManager = new OrderItemManager();
					 * try {
					 * orderItemManager.updateOrderStatus
					 * (orderItems.getOrderItemId());
					 * } catch (ClassNotFoundException e) {
					 * e.printStackTrace();
					 * } catch (SQLException e) {
					 * e.printStackTrace();
					 * }
					 */
				}

				orderExist = true;
				return order;
			}
		}

		if (orderExist == false)
			System.out.println("\tInvalid Order Number.\n");

		return null;
	}
}

package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import app.client.thread.ClearScreen;
import model.Order;
import model.OrderItem;

public class PreparationCounterView {

	private List<Order> orders = new ArrayList<Order>();
	Scanner sc = new Scanner(System.in);

	// Main Screen
	public int mainScreen() {

		ClearScreen.ClearConsole();
		System.out.println("\t--- Preparation Counter ---\n");
		System.out.println("\t1. View pending orders\n");
		System.out.println("\tEnter choice: ");
		System.out.print("\t>> ");

		return sc.nextInt();
	}

	// This method displays the latest order details at the preparation counter
	public void displayOrders(List<Order> orders) {

		this.orders = orders;

		for (Order order : orders) {
			System.out.println("\t" + order.getOrderNumber() + "\t\t\t\t"
					+ order.getTotalOrderItem());
		}
	}

	public Order printSticker(int orderNumberByUser) {

		int orderNumber;
		List<OrderItem> orderItem;
		boolean orderExist = false;

		for (Order order : orders) {
			if (order.getOrderNumber() == orderNumberByUser) {
				orderNumber = order.getOrderNumber();
				orderItem = order.getOrderItems();

				Date date = order.getTransactionDate();
				String transactionDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);

				for (OrderItem orderItems : orderItem) {
					int itemQuantity = orderItems.getQuantity();

					for (int counter = 0; counter < itemQuantity; counter++) {
						orderItems.setSequenceNumber(counter + 1);
						System.out.println("\n\t------------------------------"
								+ "--------------------");
						System.out.println("\tHornettTea FTMK UTeM");
						System.out.println("\tOrder Number: " + orderNumber);
						System.out.println("\tDate: " + transactionDate + "\n");
						System.out.println("\tName: \n\t" +
								orderItems.getItemProduct().getName() + "\n");
						System.out.println("\tSequence: " + orderItems.getSequenceNumber() + " / " + itemQuantity);
						System.out.println("\t--------------------------------"
								+ "------------------\n");
					}

					orderItems.setOrderStatus("Ready");
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

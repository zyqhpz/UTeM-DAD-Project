package view;

import model.Order;
import model.OrderItem;

public class PreparationCounterView {
	
	//OrderItem orderItem = new OrderItem();

	// This method displays the latest order details at the preparation counter
	public void displayOrders(Order orders) {
		
		System.out.println("Order ID: " + orders.getOrderId() + "\n");
		System.out.println("Order Number: " + orders.getOrderNumber() + "\n\n");
		
	}
	
	public void displayOrdersDetails(OrderItem orderItem) {
		
		System.out.println(orderItem.getItemProduct().getName() + "\n");
		
	}
}

package view;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
	
	private List<Order> orderList = new ArrayList<Order>();
	Scanner sc = new Scanner(System.in);
	
	// Main Screen
    public int mainScreen(){
    	
    	System.out.println("\t--- Preparation Counter ---\n");
    	System.out.println("\t1. View pending orders\n");
    	System.out.println("\tEnter choice: ");
    	System.out.print("\t>> ");
    	
    	return sc.nextInt();
    }
	
    /*
	// This method displays the latest order details at the preparation counter
	public void displayOrders(Order order) {
		
		orderList.add(order);
		
		System.out.println("\t" + order.getOrderNumber() + "\t\t\t\t" 
				+ order.getTotalOrderItem());
		
	
	}
	*/
	
	public void displayOrders(List<Order> order) {
		
		for (int i = 0; i < order.size(); i++) 
			orderList.add(order.get(i));
		
		for(Order orders: order) 
		System.out.println("\t" + orders.getOrderNumber() + "\t\t\t\t" 
				+ orders.getTotalOrderItem());
		
		
	}
	
	
	public Order printSticker(int userRespond) throws ParseException {
		
		int orderNumber;
		List<OrderItem> orderItem;
		boolean orderExist = false;
		
		for(Order orderLists: orderList)
		{
			if(orderLists.getOrderNumber() == userRespond)
			{
				orderNumber = orderLists.getOrderNumber();
				orderItem = orderLists.getOrderItems();
				
				for(OrderItem orderItems : orderItem)
				{
			        long datetime =System.currentTimeMillis();  
			        Date now = new java.sql.Timestamp(datetime);  
			        orderItems.setReadyTime(now);
					
					System.out.println("\n\t----------------------------------" 
							+ "----------------");
					System.out.println("\tHornettTea FTMK UTeM");
					System.out.println("\tOrder Number: " + orderNumber);
					System.out.println("\tDate: " + orderItems.getReadyTime() 
							+ "\n");
					System.out.println("\tName: \n\t" + 
							orderItems.getItemProduct().getName() + "\n");
					System.out.println("\tSequence: " + 
							orderItems.getSequenceNumber());
					System.out.println("\t----------------------------------" 
							+ "----------------\n");
					
					orderItems.setOrderStatus("Ready");
				}
				
				orderExist = true;
				return orderLists;
			}
		}
		
		if(orderExist == false) 
			System.out.println("\tInvalid Order Number.\n");
		
		
		return null;
	}
}

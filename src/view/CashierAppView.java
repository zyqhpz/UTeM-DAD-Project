package view;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import app.client.CashierApp;
import model.ItemProduct;
import model.Order;
import model.OrderItem;

public class CashierAppView {
	
	static Scanner scanner = new Scanner(System.in);

	static String[] nameList = {
        	"(Cold) Signature Brown Sugar Pearl Milk Tea",
        	"(Hot) Signature Brown Sugar Pearl Milk Tea",
        	"(Cold) Original Pearl Milk Tea",
        	"(Hot) Original Pearl Milk Tea",
        	"(Cold) Black Diamond Milk Tea",
        	"(Cold) Red Bean Pearl Milk Tea",
        	"(Hot) Red Bean Pearl Milk Tea",
        	"(Cold) Earl Grey Milk Tea",
        	"(Hot) Earl Grey Milk Tea",
        	"(Cold) Signature Milk Tea",
        	"(Hot) Signature Milk Tea",
        	"(Cold) Original Milk Tea",
        	"(Hot) Original Milk Tea",
        	"(Cold) Signature Coffee",
        	"(Hot) Signature Coffee",
        	"(Cold) Coco Mocha",
        	"(Hot) Coco Mocha",
        	"(Cold) Hazelnut Latte",
        	"(Hot) Hazelnut Latte",
        	"(Cold) Americano",
        	"(Hot) Americano"
        };
        
	static String[] labelNameList = {
        	"(Cold) Sig BrSg Prl",
        	"(Hot) Sig BrSg Prl",
        	"(Cold) Pearl Mlk",
        	"(Hot) Pearl Mlk",
        	"(Cold) Blk Dmd Mlk",
        	"(Hot) Blk Dmd Mlk",
        	"(Hot) Red Bn Prl Mlk",
        	"(Cold) Erl Gry Mlk",
        	"(Hot) Erl Gry Mlk",
        	"(Cold) Sig Mlk",
        	"(Hot) Sig Mlk",
        	"(Cold) Org Mlk",
        	"(Hot) Org Mlk",
        	"(Cold) Sig Coff",
        	"(Hot) Sig Coff",
        	"(Cold) Coco Mocha",
        	"(Hot) Coco Mocha",
        	"(Cold) Hznut Latte",
        	"(Hot) Hznut Latte",
        	"(Cold) Americano",
        	"(Hot) Americano"
        };
        
        
	static double[] priceList = {
        	6.50,
        	7.45,
        	6.50,
        	7.45,
        	7.50,
        	7.45,
        	8.35,
        	6.50,
        	7.45,
        	5.55,
        	6.50,
        	5.55,
        	6.50,
        	8.35,
        	8.35,
        	8.35,
        	8.35,
        	8.35,
        	8.35,
        	7.45,
        	7.45
        };
	
	public static void displayOrderList(ItemProduct[] menuList) {
		
		// product mame length = 45
    	
    	System.out.println("\t\t-----Beverages-----\n");
    	System.out.println("+----+---------------------------------------------+------------+");
    	System.out.println("| ID | Product Name                                | Price (RM) |");
    	System.out.println("+----+---------------------------------------------+------------+");
    	
    	for(int i = 0; i< 21; i++) {
    		// System.out.printf("| " + (i+1) + "  | " + menuList[i].getName() + " | "+ menuList[i].getPrice() +" |\n");
    		System.out.println(String.format("%-1s %-2s %-1s %-43s %-1s %.2f %-2s", "|" , (i+1) , "|" , menuList[i].getName() , "|", menuList[i].getPrice() ,"      |"));
    		System.out.println("+----+---------------------------------------------+------------+");
    	}
    }
	
	public static void displayReceipt(Order order) {
		// clear screen
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("-----------------------------------------");
		System.out.println("Your order number is: " + order.getOrderNumber());
		System.out.println("-----------------------------------------");
		System.out.println("HornetTea");
		System.out.println("FICTS");
		System.out.println("Fakulti Teknologi Maklumat dan Komunikasi");
		System.out.println("Universiti Teknikal Malaysia Melaka");
		System.out.println("Hang Tuah Jaya, 76100 Durian Tunggal");
		System.out.println("Melaka, Malaysia");
		System.out.println("-----------------------------------------");
		System.out.println("Invoice\n");
		System.out.println("Bill No: ");
		System.out.println("Date: \n");
		System.out.println("Details");
		System.out.println("-----------------------------------------");
		System.out.printf("%-22s %5s %7s", "Item Name", "Qty", "Price(RM)");
		System.out.println("-----------------------------------------");
		
		for(int i = 0; i<order.getTotalOrderItem(); i++) {
			String labelName = order.getOrderItems().get(i).getItemProduct().getLabelName();
			int quantity = order.getOrderItems().get(i).getQuantity();
			double price = order.getOrderItems().get(i).getItemProduct().getPrice();
			System.out.printf("%-22s %5s %7f", labelName, quantity, quantity*price);
		}
		

		System.out.println("-----------------------------------------");
		int totalItem = order.getTotalOrderItem();
		double subTotal = order.getSubTotal();
		double serviceTax = order.getServiceTax();
		
		double grandTotal = subTotal + serviceTax;
		double grandTotalRounded = Math.floor(grandTotal);
		double rounding = grandTotalRounded - grandTotal;
		double tenderedCash = order.getTenderedCash();
		double change = tenderedCash - grandTotalRounded;
		
		System.out.println(String.format("%-25s %-7s", "Total Item", 
				totalItem));
		System.out.println(String.format("%35s %.2f", "Sub total", subTotal));
		System.out.println(String.format("%35s %.2f", "Service Tax (6%)", serviceTax));
		System.out.println(String.format("%35s %.2f", "Rounding", rounding));
		System.out.println("-----------------------------------------");
		
		System.out.println(String.format("%35s %.2f", "Grand Total", grandTotalRounded));
		System.out.println("\n");
		System.out.println(String.format("%35s %.2f", "Tendered Cash", tenderedCash));
		System.out.println(String.format("%35s %.2f", "Change", change));
		
		System.out.println("-----------------------------------------\n");
	}
	
	public static void confirmationPage(Order order) {
		System.out.println("\n\tConfirmation Page");
		System.out.println("-----------------------------------------");
		System.out.printf("%-22s %5s %-7s", "Item Name", "Qty", "Price (RM)\n");
		System.out.println("-----------------------------------------");
		
		// Print all items, and their quantity and price
		for(int i = 0; i<order.getOrderItems().size(); i++) {
			int itemID = order.getOrderItems().get(i).getOrderItemId();
			// String labelName = order.getOrderItems().get(i).getItemProduct().getLabelName();
			String labelName = labelNameList[itemID];
			
			int quantity = order.getOrderItems().get(i).getQuantity();
			// double price = order.getOrderItems().get(i).getItemProduct().getPrice();
			double price = priceList[itemID];
			System.out.println(String.format("%-25s %-7s %.2f", labelName, quantity, quantity*price));
			
			
			
		}
		
		System.out.println("-----------------------------------------");
		int totalItem = order.getTotalOrderItem();
		double subTotal = order.getSubTotal();
		double serviceTax = order.getServiceTax();
		
		double grandTotal = subTotal + serviceTax;
		double grandTotalRounded = Math.floor(grandTotal);
		double rounding = grandTotalRounded - grandTotal;
		double tenderedCash = order.getTenderedCash();
		double change = tenderedCash - grandTotalRounded;
		
		System.out.println(String.format("%-25s %-7s", "Total Item", 
				totalItem));
		System.out.println(String.format("%35s %.2f", "Sub total", subTotal));
		System.out.println(String.format("%35s %.2f", "Service Tax (6%)", serviceTax));
		System.out.println(String.format("%35s %.2f", "Rounding", rounding));
		System.out.println("-----------------------------------------");
		
		System.out.println(String.format("%35s %.2f", "Grand Total", grandTotalRounded));
		System.out.println("-----------------------------------------\n");
		
		System.out.println("\t1. Pay Cash");
		System.out.println("\t2. Back to menu");
		System.out.print("\tChoice: ");
		
	}
}

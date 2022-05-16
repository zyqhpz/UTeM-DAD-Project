package app.client;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import model.*;

import view.CashierAppView;


/**
 * This class represents the client (Cashier) app for the system
 * 
 * Function: to start the client-side program for the Cashier
 * 
 * @author DaysonTai 
 */

public class CashierApp {
	
	private static CashierAppView view;
	private static int orderNumber = 1;
	
	public static void main(String args[]) {
		
		System.out.println("\n\nStarting CashierApp..\n");

        String continueOrder = "Yes";
        Scanner sc = new Scanner(System.in);
        
        
        // This array contains Objects of the item product
        ItemProduct[] menuList = new ItemProduct[30];
        
        
        Integer[] idList = {
        	1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 
        	20, 21
        };
        
        String[] nameList = {
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
        
        String[] labelNameList = {
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
        
        Double[] priceList = {
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
        
        
        // Fill the array with item objects
        for(int i = 0; i < 21; i++) {
            menuList[i] = new ItemProduct(i+1, nameList[i], labelNameList[i], 
            		priceList[i]);
        	
        }
        
         
        try {
        	
            // Server information
            int serverPortNo = 8087;
            InetAddress serverAddress = InetAddress.getLocalHost();

            // Page for making a new order
            do {
            	List<OrderItem> orderItems = new ArrayList<OrderItem>();
//            	Order order = new Order();
            	Order order = null;
            	OrderItem orderItem = new OrderItem();
            	int totalOrderItem = 0;
            	double subTotal = 0;
            	
            	int choice;
            	
            	// Ordering specific items
            	do {
            		// clear screen
            		new ProcessBuilder("cmd", "/c", "cls").inheritIO()
            			.start().waitFor();
                    
            		// 1. Display menu of all beverages
                	view.displayOrderList(menuList);
                	
                	// 2. Select menu and quantity
                	System.out.println("\n\t Enter 0 to complete this order");
                	System.out.print("\n\t Choice: ");
                	choice = sc.nextInt();
                	
                		
                	// continue to add beverage 
                	if(choice != 0) {
                		
                		System.out.print("\t Quantity: ");
                    	int quantity = sc.nextInt();
                    	totalOrderItem += quantity;
                    	double itemPrice = priceList[choice-1];
                    	subTotal += quantity*itemPrice;
                    
                        
                        // get time
                        Calendar currentTime = Calendar.getInstance();
                 
                        
                    	// Calculate ready time by adding current time with 5 
                        // minutes
                        
                    	Calendar readyTime = Calendar.getInstance();
                    	readyTime.add(Calendar.MINUTE, 5);
                    		
                    	
                    	// 3. Store in ArrayList
                    	orderItem = null;
                    	orderItem = new OrderItem();
                    	orderItem.setItemProduct(menuList[choice-1]);
                    	orderItem.setOrderItemId(orderNumber);
                    	orderItem.setOrderStatus("Not Ready");
                    	orderItem.setQuantity(quantity);
                    	orderItem.setReadyTime(readyTime.getTime());
                    	orderItem.setSequenceNumber(orderNumber);
                    	orderItem.setSubTotalAmount(subTotal);
                    	
                    	// Add orderItem into order
                    	orderItems.add(orderItem);
                	}
                	
                	
                	
                	// Customer order finish
                    // 4. Add to Order object
                	// which contains List<OrderItem> order Items;
                	Calendar calendar = Calendar.getInstance();
                    calendar.set(2022, 5, 7, 20, 1, 1);
                	
                	int orderId = 1000 + orderNumber;
                	Date transactionDate = calendar.getTime();
                	
                	double serviceTax = subTotal * 0.06;
                	double rounding = 0;
                	
                	double grandTotal = subTotal + serviceTax;
                	double tenderedCash = 0;
                	double change = 0;
                	
                	order = new Order(orderId, orderNumber, transactionDate, 
                			orderItems, totalOrderItem, subTotal, serviceTax, 
                			rounding, grandTotal, tenderedCash, change);
                	
                	
                	// display the confirmation page
                	if(choice == 0) {
                		// clear screen
                		new ProcessBuilder("cmd", "/c", "cls").inheritIO()
                			.start().waitFor();
                		
                		order.setRounding(Math.round(grandTotal*20)/20);
                		
                	
                		// display confirmation page
                		view.confirmationPage(order);
                		
                		int payChoice = sc.nextInt();
                		
                		
                		
                		// Confirm to pay
                		if(payChoice == 1) {
                			System.out.print("\n\tTendered cash: ");
                			tenderedCash = sc.nextDouble();
                			order.setTenderedCash(tenderedCash);
                			
                			
                			
                			
                			view.displayReceipt(order);
                			sc.nextLine();
                		}
                		
                		

                	}
                		
                		
                	
                	
                	
              
                	
            	} while (choice != 0);

                
            	
            	
            	
                // 5. Process Order when customer do payment (initialize all 
                // variables using setters)

                // 6. Send Order object to server
                Socket socket = new Socket(serverAddress, serverPortNo);

                OutputStream outStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outStream);
                oos.writeObject(order);
                socket.close();

                // 7. Print receipt
                //view.displayReceipt(order);
                sc.nextLine();
                
                // add 1 to orderNumber
                orderNumber++;

            } while (true);

        } catch (Exception e) {

            e.printStackTrace();
        }
        
	}
	
	// method to create an Order object 
//    public static Order loadOrder() {
//    	
//    	Order order = new Order();
//    	
//    	
//    	return order;
//    }
	
}


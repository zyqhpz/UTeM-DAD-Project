package app.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Order;
import view.PreparationCounterView;

// Screens
// Main screen
/*
     --- Barista ---
     1. View pending orders

     Enter choice:
     >> 
*/

// Pending orders screen
/*
     --- Pending Orders ---
    Order Number        Quantity
    111                 2
    112                 3

     Select OrderID or 0 to go back to main menu:
     >> 
*/

// Order details screen
/*
    Print stickers here from A2 docs
    

     Select 1 to update order status OR 2 to go back to previous menu:
     >> 
*/

/*
    TODOS
    1. Display main menu
    2. Select to view pending orders
    3. Select Order Number to view order details
    4. Select to update order status
    5. Set order item status to "Ready"
    6. Send Order object to server
    7. Redirect to main menu
*/

/**
 * This class represent the client (Barista) app for the system
 * 
 * Function: to start the client-side program for the Barista
 * 
 * @author
 */

public class BaristaApp {
    public static void main(String[] args) {
        
        System.out.println("\n\nStarting BaristaApp..\n");

        PreparationCounterView preparationCounterView = 
				new PreparationCounterView();

        Scanner sc = new Scanner(System.in);
        int optionToViewPending;
        int optionToUpdateStatus = -1;
        
        try {

            // Server information
            int serverPortNo = 8088;
            InetAddress serverAddress = InetAddress.getLocalHost();

            do {

                // 1. Display main menu
                // 2. Select to view pending orders
            	optionToViewPending = preparationCounterView.mainScreen();
            	
            	if(optionToViewPending == 1)
            	{
					
						// Create a multithread to always receive new list of
						// pending orders from server
						Socket socket = new Socket(serverAddress, serverPortNo);
						InputStream is = socket.getInputStream();
						ObjectInputStream ois = new ObjectInputStream(is);
					do {
						// Display latest list of pending orders
						clearScreen();
						System.out.println("\n\t--- Pending Orders ---\n" 
						+ "\tOrder Number\t\t\tQuantity");
						// while (ois.available() > 0) {

						/*
						Object obj = ois.readObject();
	                    // Check it's an ArrayList
	                    if (obj instanceof ArrayList<?>) {
	                        // Get the List.
	                        ArrayList<?> al = (ArrayList<?>) obj;
	                        if (al.size() > 0) {
	                            // Iterate.
	                            for (int i = 0; i < al.size(); i++) {
	                                // Still not enough for a type.
	                                Object o = al.get(i);
	                                if (o instanceof Order) {
	                                    // Here we go!
	                                    Order v = (Order) o;
	                                    orders.add(v);
	                                    // use v.
						*/				
						List<Order> orders = new ArrayList<Order>();

						Object order = ois.readObject();
	                    if (order instanceof ArrayList<?>) {
	                        ArrayList<?> orderlist = (ArrayList<?>) order;
	                        if (orderlist.size() > 0) {
	                            for (int counter = 0; counter < orderlist.size(); counter++) {
	                                Object o = orderlist.get(counter);
	                               if (o instanceof Order) {
	                                    Order latestorder = (Order) o;
	                                    orders.add(latestorder);
	                                    preparationCounterView.displayOrders(orders);
	                               }
	                            }
	                        }
                 		}
			
						//Order order = (Order) ois.readObject();
	                    //orders.add(order);
						//preparationCounterView.displayOrders(orders);
						// System.out.println("\t" + order.getOrderNumber() +
						// "\t\t\t" + order.getTotalOrderItem());
						// }

						// 3. Select Order Number to view order details
						System.out.println("\n\tEnter Order Number or " 
								+ "0 to return to main menu: ");
						System.out.print("\t>> ");
						int optionToViewDetails = sc.nextInt();

						if (optionToViewDetails == 0) {
							clearScreen();
							break;
						}
						else {
							// Print stickers
							clearScreen();
							Order updatedOrder = 
							preparationCounterView.printSticker(optionToViewDetails);

							if(updatedOrder == null)
								continue;
							
							// 4. Select to update order status
							System.out.println("\tEnter 1 to update order status"
									+ " or 0 to go back to previous menu: ");
							System.out.print("\t>> ");
							optionToUpdateStatus = sc.nextInt();

							if (optionToUpdateStatus == 1) {

								// 5. Set order item status to "Ready"
								// 6. Send Order object to server
								// Socket socket = new Socket(serverAddress, serverPortNo);
								OutputStream outStream = 
										socket.getOutputStream();
								ObjectOutputStream oos = 
										new ObjectOutputStream(outStream);
								oos.writeObject(updatedOrder);

								socket.close();
								outStream.flush();
								oos.flush();
								outStream.close();
								oos.close();

								// 7. Redirect to main menu
								clearScreen();
								break;

							} else if (optionToUpdateStatus == 0) {
								continue;
								
							} else
								break;

						} 
					} while (optionToUpdateStatus == 0);
            		
            	}
            	else 
            		break;
            	
            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

package app.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import model.Order;
import model.OrderItem;
import view.PreparationCounterView;

/**
 * This class
 * 
 * @author WongKakLok
 *
 */

public class PreparationCounterApp {
	
	public static void main (String[] args) {
		
		try {
			
			System.out.println("\tExecuting PreparationCounterApp");
			
			// Server information
			int serverPortNo = 8088;
			InetAddress serverAddress = InetAddress.getLocalHost();
			
			// Connect to the remote machine
			Socket socket = new Socket(serverAddress, serverPortNo);
			
			// Create stream to send request
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			
			// Send request to the server
			String orderStatus = "Processing";
			dos.writeUTF(orderStatus);
			
			// Create stream to receive respond from server
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			// Create objects
			Order orders = new Order();
			OrderItem orderItem = new OrderItem();
			PreparationCounterView preparationCounterView = 
					new PreparationCounterView();
			
			// Receive latest order from the server
			while(ois.available() > 0) {
				
				orders = (Order)ois.readObject();
				preparationCounterView.displayOrders(orders);
				
				OutputStream OrderItemos = socket.getOutputStream();
				DataOutputStream OrderItemdos = 
						new DataOutputStream(OrderItemos);
				
				OrderItemdos.writeInt(orders.getOrderId());
				
				InputStream OrderItemis = socket.getInputStream();
				ObjectInputStream OrderItemois = 
						new ObjectInputStream(OrderItemis);
				
				System.out.println("Details: \n");
				while(OrderItemois.available() > 0) {
					orderItem = (OrderItem) OrderItemois.readObject();
				
					// Process the respond
					
			
				}
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
		
	}
}

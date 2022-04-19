package app.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import controller.OrderManager;
import controller.database.Database;
import model.Order;

/**
 * This class represent the server app for HornettTeaOrderServerApp
 * 
 * Function: to start the server-side program for the application
 * 
 * @author haziqhapiz
 */

public class HornettTeaOrderServerApp {
	public static void main(String args[]) throws ClassNotFoundException, Exception {

		System.out.println("\n\nStarting HornettTeaOrderServerApp..\n");

		ServerSocket serverSocket = null;

		Order order;
		OrderManager orderManager;

		try {

			// create a connection to the database
			Connection conn = Database.doConnection();
			System.out.println("Connected to the database..\n");

			// bind to a port
			int portNo = 8087;
			serverSocket = new ServerSocket(portNo);

			System.out.println("\tWaiting for request from Cashier\n");

			// 2. Listen to request
			while (true) {

				// 3. Accept request from client
				Socket clientSocket = serverSocket.accept();

				InputStream is = clientSocket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);

				// read Order object from cashier
				order = (Order) ois.readObject();

				orderManager = new OrderManager(order);

				System.out.println("\n\tReceive an Order object from Cashier.\n");

				// display data from Order object
				orderManager.displayData();

				System.out.println("\n\tWaiting for next request\n");

			}

		} catch (Exception e) {

			if (serverSocket != null) {
				serverSocket.close();
			}

			e.printStackTrace();
		}
	}
}

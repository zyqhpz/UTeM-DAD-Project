package app.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import app.server.thread.BaristaReceiver;
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
		ServerSocket baristaServerSocket = null;

		Order order;
		OrderManager orderManager;

		Socket baristaSocket = null;

		try {

			// create a connection to the database
			Connection conn = Database.doConnection();
			System.out.println("Connected to the database..\n");

			// bind to a port
			int portNo = 8087;
			serverSocket = new ServerSocket(portNo);
			InetAddress serverAddress = InetAddress.getLocalHost();

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

				// Send Order object to Barista
				// Socket baristaSocket = baristaServerSocket.accept();

				// OutputStream baristaOS = baristaSocket.getOutputStream();
				// ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

				// baristaOOS.writeObject(order);

				// System.out.println("\n\tOrder object sent to Barista.\n");

				// baristaSocket.close();

				order.setOrderId(2);

				System.out.println("\n\tWaiting for next request\n");

				// start the barista as thread
				// Runnable barista = new BaristaReceiver();

				// try (Socket baristaSocket = new Socket(serverAddress, 8088);) {
				try {

					baristaSocket = new Socket(serverAddress, 8088);

					// Send Order object to Barista
					// Socket baristaSocket = baristaServerSocket.accept();

					OutputStream baristaOS = baristaSocket.getOutputStream();
					ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

					baristaOOS.writeObject(order);

					System.out.println("\n\tOrder object sent to Barista.\n");

					// InputStream baristaIS = baristaSocket.getInputStream();
					// ObjectInputStream baristaOIS = new ObjectInputStream(baristaIS);

					// OutputStream baristaOS = baristaSocket.getOutputStream();
					// ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

					// baristaOOS.writeObject(order);
					// order = (Order) baristaOIS.readObject();

					// int orderId = baristaOIS.readInt();

					// read int from barista

					// int orderId = 1;

					// try {
					// // Thread.sleep(5000);
					// // orderId = (int) baristaOIS.readObject();
					// // orderId = baristaOIS.readInt();

					// order = (Order) baristaOIS.readObject();

					// } catch (Exception e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					// // int orderId = (int) baristaOIS.readObject();

					// orderId = order.getOrderId();

					// System.out.println("\n\tOrder " + orderId + " get from Barista.\n");

					// baristaSocket.close();
					// clientSocket.close();
				} catch (Exception e) {
					System.out.println("\n\tError: " + e.getMessage() + "\n");
				}

				Runnable barista = new BaristaReceiver(baristaSocket);
				Thread baristaThread = new Thread(barista);
				baristaThread.start();

				// try (Socket baristaSocket = new Socket(serverAddress, 8088);) {

				// // Send Order object to Barista
				// // Socket baristaSocket = baristaServerSocket.accept();

				// // baristaSocket = baristaSocket.accept();

				// // Listen to InputStream from Barista

				// InputStream baristaIS = baristaSocket.getInputStream();
				// ObjectInputStream baristaOIS = new ObjectInputStream(baristaIS);

				// // OutputStream baristaOS = baristaSocket.getOutputStream();
				// // ObjectOutputStream baristaOOS = new ObjectOutputStream(baristaOS);

				// // baristaOOS.writeObject(order);
				// // order = (Order) baristaOIS.readObject();

				// int orderId = baristaOIS.readInt();

				// System.out.println("\n\tOrder " + orderId + " get from Barista.\n");

				// // System.out.println("\n\tOrder object received from Barista.\n");

				// // baristaSocket.close();
				// // clientSocket.close();

				// } catch (Exception e) {
				// System.out.println("\n\tError: " + e.getMessage() + "\n");
				// }

			}

		} catch (Exception e) {

			if (serverSocket != null) {
				serverSocket.close();
			}

			e.printStackTrace();
		}
	}
}

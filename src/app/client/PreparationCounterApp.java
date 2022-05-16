package app.client;

import java.net.InetAddress;
import java.net.Socket;

import app.client.thread.PreparationCounterGetter;

/**
 * This class represent the client (Preparation Counter) app for
 * the system
 * 
 * This will start the client-side program for the application and start a
 * thread to get the order from the server asynchronously
 * 
 * @author WongKakLok
 */

public class PreparationCounterApp {
	public static void main(String args[]) 
			throws ClassNotFoundException, Exception {

		System.out.println("\n\tStarting PreparationCounterApp..\n");

		try {

			// Server information
			InetAddress serverAddress = InetAddress.getLocalHost();

			Socket baristaSocket = new Socket(serverAddress, 8088);
			Socket baristaToServerSocket = new Socket(serverAddress, 8089);

			// Use multithread to always receive new list of
			// pending orders from server
			Runnable preparationCounterGetter = null;
			Thread preparationCounterThread = null;

			preparationCounterGetter = new PreparationCounterGetter
					(baristaSocket, baristaToServerSocket);
			preparationCounterThread = new Thread(preparationCounterGetter);

			// Execute thread
			preparationCounterThread.start();

		} catch (

		Exception e) {

			e.printStackTrace();
		}
	}
}

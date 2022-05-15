package app.client;

import java.net.InetAddress;
import java.net.Socket;

import app.client.thread.PreparationCounterGetter;

/**
 * This class represent the client (Preparation Counter) app for
 * HornettTeaOrderServerApp
 * 
 * This will start the client-side program for the application and start a
 * thread to get the order from the server asyncronously
 * 
 * @author WongKakLok
 */

public class PreparationCounterApp {
	public static void main(String args[]) throws ClassNotFoundException, Exception {

		System.out.println("\n\tStarting PreparationCounterApp..\n");

		try {

			// Server information
			InetAddress serverAddress = InetAddress.getLocalHost();

			Socket baristaSocket = new Socket(serverAddress, 8088);
			Socket baristaToServerSocket = new Socket(serverAddress, 8089);

			Runnable preparationCounterGetter = null;
			Thread preparationCounterThread = null;

			preparationCounterGetter = new PreparationCounterGetter(baristaSocket, baristaToServerSocket);
			preparationCounterThread = new Thread(preparationCounterGetter);

			preparationCounterThread.start();

		} catch (

		Exception e) {

			e.printStackTrace();
		}
	}
}

package app.client;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import app.client.thread.PreparationCounterGetter;
import model.*;

/**
 * This class represent the client (Preparation Counter) app for
 * HornettTeaOrderServerApp
 * 
 * Function: to start the client-side program for the application
 * 
 * @author haziqhapiz
 */

public class PreparationCounterApp {
	public static void main(String args[]) throws ClassNotFoundException, Exception {

		System.out.println("\n\nStarting PreparationCounterApp..\n");

		Order order = null;

		ServerSocket serverSocket = null;

		Scanner sc = new Scanner(System.in);

		try {

			// Server information
			InetAddress serverAddress = InetAddress.getLocalHost();

			int portNo = 8088;
			Socket baristaSocket = new Socket(serverAddress, portNo);
			Socket baristaToServerSocket = new Socket(serverAddress, 8085);

			Runnable preparationCounterGetter = null;
			Thread preparationCounterThread = null;

			preparationCounterGetter = new PreparationCounterGetter(serverSocket, baristaSocket, baristaToServerSocket);
			preparationCounterThread = new Thread(preparationCounterGetter);

			preparationCounterThread.start();

		} catch (

		Exception e) {

			e.printStackTrace();
		}
	}
}

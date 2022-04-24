package app.client.thread;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import model.Order;

public class BaristaGetter implements Runnable {
    private Socket baristaSocket = null;
    private InetAddress serverAddress = null;
    private ServerSocket serverSocket = null;

    // public BaristaReceiver() {
    // try {
    // // bind to a port
    // int portNo = 8088;
    // serverAddress = InetAddress.getLocalHost();
    // // baristaSocket = new Socket(serverAddress, portNo);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    private Order order;

    // public BaristaGetter(Socket baristaSocket) {
    // this.baristaSocket = baristaSocket;
    // }
    public BaristaGetter(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        // Socket baristaSocket = null;
        while (!serverSocket.isClosed()) {
            // Runnable displayList = new DisplayList(serverSocket);
            // Thread thread = new Thread(displayList);

            Runnable displayList = new DisplayList(serverSocket, order);
            Thread thread = new Thread(displayList);
            try {
                Socket socket = serverSocket.accept();

                InputStream inStream = socket.getInputStream();
                // ObjectInputStream ois = new ObjectInputStream(inStream);
                ObjectInputStream ois = new ObjectInputStream(inStream);

                // read Order object from barista
                order = (Order) ois.readObject();
                clearScreen();

                // Runnable displayList = new DisplayList(serverSocket, order);
                // Thread thread = new Thread(displayList);
                // thread.start();
                displayList();
                thread.start();

                // display current date and time
                // Date date = new Date();
                // System.out.println("\n\t" + date.toString() + "\n");

                // int send = 1;

                // do {
                // // display data from Order object
                // // order.displayData();

                // // display menu
                // System.out.println("\n\t1. Get Order");
                // System.out.println("\t2. Exit");
                // System.out.print("\n\tEnter your choice: ");

                // // read user's choice
                // int choice = Integer.parseInt(System.console().readLine());

                // switch (choice) {
                // case 1:
                // // send Order object to barista
                // OutputStream outStream = socket.getOutputStream();
                // ObjectOutputStream oos = new ObjectOutputStream(outStream);
                // oos.writeObject(order);
                // // oos.flush();
                // System.out.println("Order updated");
                // break;
                // case 2:
                // // exit
                // break;
                // default:
                // System.out.println("\n\tInvalid choice. Please try again.\n");
                // break;
                // }
                // } while (true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void displayList() {

        System.out.println("\n\t1. Get Order");
        System.out.println("\t2. Exit");
        System.out.print("\n\tEnter your choice: ");
        // do {
        // // display data from Order object
        // // order.displayData();

        // // display menu
        // System.out.println("\n\t1. Get Order");
        // System.out.println("\t2. Exit");
        // System.out.print("\n\tEnter your choice: ");

        // // read user's choice
        // // int choice = Integer.parseInt(System.console().readLine());

        // Scanner sc = new Scanner(System.in);

        // int choice = sc.nextInt();

        // switch (choice) {
        // case 1:
        // // send Order object to barista
        // break;
        // case 2:
        // // exit
        // break;
        // default:
        // System.out.println("\n\tInvalid choice. Please try again.\n");
        // break;
        // }

        // } while (true);

    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

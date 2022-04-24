package app.client.thread;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import model.Order;

public class DisplayList implements Runnable {

    private ServerSocket serverSocket = null;
    private Socket socket = null;

    Order order;

    public DisplayList(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Date date = new Date();
        System.out.println("\n\t" + date.toString() + "\n");

        int send = 1;

        do {
            // display data from Order object
            // order.displayData();

            // display menu
            try {
                socket = serverSocket.accept();
                // OutputStream outStream = socket.getOutputStream();
                // ObjectOutputStream oos = new ObjectOutputStream(outStream);

                // oos.writeObject(order);
                // oos.flush();
                // oos.close();
                // outStream.close();
                // socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(
                    "\n\t1. Get Order");
            System.out.println("\t2. Exit");
            System.out.print("\n\tEnter your choice: ");

            // read user's choice
            // int choice = Integer.parseInt(System.console().readLine());

            Scanner sc = new Scanner(System.in);

            int choice = sc.nextInt();

            // try {
            // socket = serverSocket.accept();
            // } catch (Exception e) {
            // // TODO: handle exception
            // }

            switch (choice) {
                case 1:
                    try {
                        // socket = serverSocket.accept();
                        OutputStream outStream = socket.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(outStream);
                        oos.writeObject(order);

                        oos.flush();
                        oos.close();
                        outStream.close();
                        // oos.flush();
                        System.out.println("Order updated");
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    // send Order object to barista

                    break;
                case 2:
                    // exit
                    break;
                default:
                    System.out.println("\n\tInvalid choice. Please try again.\n");
                    break;
            }
        } while (true);
    }
}

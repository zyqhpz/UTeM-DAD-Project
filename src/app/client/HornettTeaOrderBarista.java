package app.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;

import controller.database.Database;
import model.*;

/**
 * This class represent the client (cashier) app for HornettTeaOrderServerApp
 * 
 * Function: to start the client-side program for the application
 * 
 * @author haziqhapiz
 */

public class HornettTeaOrderBarista {
    public static void main(String args[]) throws ClassNotFoundException, Exception {

        System.out.println("\n\nStarting HornettTeaOrderBaristaApp..\n");

        Order order;

        ServerSocket serverSocket = null;

        Scanner sc = new Scanner(System.in);

        try {

            // Server information
            int serverPortNo = 8088;
            InetAddress serverAddress = InetAddress.getLocalHost();

            int send = 0;

            int portNo = 8088;
            serverSocket = new ServerSocket(portNo);
            // InetAddress serverAddress = InetAddress.getLocalHost();

            while (true) {

                // System.out.print("\tSend? >> ");

                // // read Order object from server
                Socket socket = serverSocket.accept();

                InputStream inStream = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inStream);

                // trigger a function when the server sends an Order object

                // always try to listen to any requests from the server
                // if (ois.readObject() instanceof Order) {

                // try (ObjectInputStream ois = new ObjectInputStream(inStream);) {
                order = (Order) ois.readObject();

                clearScreen();

                System.out.println("\n\t-- HornettTeaOrderBaristaApp --\n");

                System.out.println("\n\nOrder received from server: ");

                System.out.println("\tOrder ID: " + order.getOrderId());

                // print current date
                Date date = new Date();
                System.out.println("\tDate: " + date);

                // System.out.print("Enter 1 to accept order, 2 to reject order, 3 to cancel
                // order\n>> ");

                // send = sc.nextInt();

                // if (send == 1) {
                // clearScreen();
                // }

                // if (order != null) {
                // clearScreen();
                // }

                // print Order object

                if (send == 1) {
                    Socket socketBarista = new Socket(serverAddress, 8089);

                    OutputStream outStream = socketBarista.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(outStream);
                    oos.writeObject(order);

                    System.out.println("\n\tSending Update Order to server\n");
                    socketBarista.close();
                }

                // } catch (Exception e) {
                // System.out.println("\n\tError: " + e.getMessage());
                // }

                // if (ois != null) {

                // }

            }

        } catch (

        Exception e) {

            e.printStackTrace();
        }
    }

    // public class CLS {
    // public static void main(String... arg) throws IOException,
    // InterruptedException {
    // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    // }
    // }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

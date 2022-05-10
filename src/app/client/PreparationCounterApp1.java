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
// import java.rmi.registry.LocateRegistry;
// import java.rmi.registry.Registry;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import app.client.thread.PreparationCounterGetter;

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

public class PreparationCounterApp1 {
    public static void main(String args[]) throws ClassNotFoundException, Exception {

        System.out.println("\n\nStarting PreparationCounterApp..\n");

        Order order = null;

        ServerSocket serverSocket = null;

        Scanner sc = new Scanner(System.in);

        try {

            // Server information
            int serverPortNo = 8088;
            InetAddress serverAddress = InetAddress.getLocalHost();

            int send = 0;

            int portNo = 8088;
            // serverSocket = new ServerSocket(portNo);
            Socket baristaSocket = new Socket(serverAddress, portNo);
            Socket baristaToServerSocket = new Socket(serverAddress, 8085);
            // Socket baristaSocket = serverSocket.accept();
            // InetAddress serverAddress = InetAddress.getLocalHost();

            Runnable preparationCounterGetter = null;
            Thread baristaThread = null;

            preparationCounterGetter = new PreparationCounterGetter(serverSocket, baristaSocket, baristaToServerSocket);
            baristaThread = new Thread(preparationCounterGetter);

            baristaThread.start();

            // Runnable displayList = new DisplayListTest(serverSocket, order,
            // baristaSocket);
            // Thread thread = new Thread(displayList);

            // thread.start();

            // ObjectInputStream ois = null;

            // while (!serverSocket.isClosed()) {

            // // System.out.print("\tSend? >> ");

            // // // read Order object from server
            // Socket socket = serverSocket.accept();

            // InputStream inStream = socket.getInputStream();
            // // ObjectInputStream ois = new ObjectInputStream(inStream);
            // ObjectInputStream ois = new ObjectInputStream(inStream);

            // // trigger a function when the server sends an Order object

            // // always try to listen to any requests from the server
            // // if (ois.readObject() instanceof Order) {

            // // try (ObjectInputStream ois = new ObjectInputStream(inStream);) {

            // // while () {

            // // }

            // order = (Order) ois.readObject();

            // clearScreen();

            // System.out.println("\n\t-- HornettTeaOrderBaristaApp --\n");

            // // System.out.println("\n\nOrder received from server: ");

            // // System.out.println("\tOrder ID: " + order.getOrderId());

            // // do {
            // Date date = new Date();
            // System.out.println("\tDate: " + date);

            // send = 1;

            // System.out.print("Enter 1 to accept order, 2 to reject order, 3 to cancel
            // order\n>> ");

            // send = sc.nextInt();

            // //

            // // add a sleep for 5 seconds to simulate the time taken
            // // to process the order
            // // Thread.sleep(3000);

            // if (send == 1) {
            // // Socket socket = new Socket(serverAddress, serverPortNo);

            // OutputStream outStream = socket.getOutputStream();
            // ObjectOutputStream oos = new ObjectOutputStream(outStream);
            // // oos.writeInt(order.getOrderId());

            // // for (int i = 0; i < order.getOrderItems().size(); i++) {
            // // oos.writeObject(order.getOrderItems().get(i));
            // // }

            // System.out.print("Send as what OrderID? \n>> ");

            // int orderId = sc.nextInt();
            // order.setOrderId(orderId);

            // // for (int i = 0; i < 3; i++) {
            // try {
            // // oos.writeInt(order.getOrderId());

            // oos.writeObject(order);
            // System.out.println("\n\tSending Order " + order.getOrderId() + " to
            // server\n");

            // } catch (Exception e) {
            // // TODO: handle exception
            // System.out.println("\n\tError from writeObject: " + e.getMessage());
            // }

            // continue;

            // // System.out.println("\n\tSending Order " + order.getOrderId() + " to
            // // server\n");
            // // }

            // // socket.close();

            // }
            // } while (true);

            // print current date

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
            // if (send == 1) {
            // et socketBarista = new Socket(serverAddress, 8089);

            // utStream outStream = socketBarista.getOutputStream();
            // ctOutputStream oos = new ObjectOutputStream(outStream);
            // writeObject(order);

            // em.out.println("\n\tSending Update Order to server\n");
            // etBarista.close();
            // }

            // send order.getOrderId() as int to server to update the order status
            // to "Accepted"

            // send = 1;

            // System.out.print("Enter 1 to accept order, 2 to reject order, 3 to cancel
            // order\n>> ");

            // send = sc.nextInt();

            // // add a sleep for 5 seconds to simulate the time taken
            // // to process the order
            // // Thread.sleep(3000);

            // if (send == 1) {
            // // Socket socket = new Socket(serverAddress, serverPortNo);

            // OutputStream outStream = socket.getOutputStream();
            // ObjectOutputStream oos = new ObjectOutputStream(outStream);
            // // oos.writeInt(order.getOrderId());

            // // for (int i = 0; i < order.getOrderItems().size(); i++) {
            // // oos.writeObject(order.getOrderItems().get(i));
            // // }

            // // for (int i = 0; i < 3; i++) {
            // try {
            // // oos.writeInt(order.getOrderId());

            // oos.writeObject(order);
            // } catch (Exception e) {
            // // TODO: handle exception
            // System.out.println("\n\tError from writeObject: " + e.getMessage());
            // }

            // System.out.println("\n\tSending Order " + order.getOrderId() + " to
            // server\n");
            // // }

            // // socket.close();

            // }

            // } catch (Exception e) {
            // System.out.println("\n\tError: " + e.getMessage());
            // }

            // if (ois != null) {

            // }

            // }

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

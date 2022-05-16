package app.client.thread;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.Order;
import view.PreparationCounterView;


/**
 * This is the class definition of the PreparationCounterGetter thread.
 * 
 * @author WongKakLok
 *
 */

public class PreparationCounterMainMenu implements Runnable {

    private Socket socket = null;

    private List<Order> orders = new ArrayList<Order>();

    public PreparationCounterMainMenu(Socket socket, List<Order> orders) {
        this.socket = socket;
        this.orders = orders;
    }

    @Override
    public void run() {
        PreparationCounterView preparationCounterView = 
        		new PreparationCounterView();

        Scanner sc = new Scanner(System.in);
        
        int optionToViewPending;
        int optionToUpdateStatus = -1;

        Date date = new Date();
        System.out.println("\n\t" + date.toString() + "\n");

        try {

            // Server information
            InetAddress serverAddress = InetAddress.getLocalHost();

            do {

                // Display main menu by invoking class form the view 
                optionToViewPending = preparationCounterView.mainScreen();

                if (optionToViewPending == 1) {

                    do {
                        // Display latest list of pending orders
                        ClearScreen.ClearConsole();
                        System.out.println("\n\t--- Pending Orders ---\n"
                                + "\tOrder Number\t\t\tQuantity");

                        preparationCounterView.displayOrders(orders);

                        // Select Order Number to view order details
                        System.out.println("\n\tEnter Order Number or "
                                + "0 to return to main menu: ");
                        System.out.print("\t>> ");
                        int optionToViewDetails = sc.nextInt();

                        if (optionToViewDetails == 0)
                            break;
                        else {
                            // Print stickers
                            ClearScreen.ClearConsole();
                            Order updatedOrder = preparationCounterView.
                            		printSticker(optionToViewDetails);

                            if (updatedOrder == null)
                                continue;

                            System.out.println("\tEnter 1 to update order status"
                            		+ " or 0 to go back to previous menu: ");
                            System.out.print("\t>> ");
                            optionToUpdateStatus = sc.nextInt();

                            if (optionToUpdateStatus == 1) {

                                // Remove updated order from list
                                orders.remove(updatedOrder);

                                socket = new Socket(serverAddress, 8089);

                                // Send Order object to server
                                OutputStream outStream = 
                                		socket.getOutputStream();
                                ObjectOutputStream oos = 
                                		new ObjectOutputStream(outStream);
                                oos.writeObject(updatedOrder);
                                System.out.println("Order updated");

                                socket.close();
                                outStream.flush();
                                oos.flush();
                                outStream.close();
                                oos.close();

                                // Redirect to main menu
                                ClearScreen.ClearConsole();
                                break;

                            } else if (optionToUpdateStatus == 0) {
                                continue;

                            } else
                                break;

                        }
                    } while (optionToUpdateStatus == 0);

                } else
                    break;

            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

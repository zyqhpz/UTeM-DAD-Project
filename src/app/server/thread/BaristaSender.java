package app.server.thread;

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

public class BaristaSender implements Runnable {

    @Override
    public void run() {
        // start a porcess that will send an Order object to the server
        while (true) {

        }

    }
}

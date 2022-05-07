package controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static Connection conn = null;

    public static Connection doConnection() throws ClassNotFoundException, SQLException {
        System.out.println("Loading driver...");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        System.out.println("Connecting database...");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ht_db", "root", "");

        return conn;
    }
}
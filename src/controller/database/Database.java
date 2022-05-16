package controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class establish JDBC connection.
 *
 */

public class Database {
	
    static Connection conn = null;

    public static Connection doConnection() 
    		throws ClassNotFoundException, SQLException {
        try {
        	
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch (ClassNotFoundException e) {
        	
            throw new IllegalStateException
            	("Cannot find the driver in the classpath!", e);
            
        }
        Connection conn = DriverManager.getConnection
        		("jdbc:mysql://localhost/ht_db", "root", "");

        return conn;
    }
}
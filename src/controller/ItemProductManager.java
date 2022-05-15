package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.database.Database;

import model.ItemProduct;

public class ItemProductManager {
    public ItemProductManager() {
    }

    public ItemProduct getItemProduct(int itemProductId) throws ClassNotFoundException, SQLException {
        ItemProduct itemProduct = new ItemProduct();

        String sql = "SELECT * FROM itemproduct WHERE ItemProductId = ?";

        Connection conn = Database.doConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, itemProductId);
            java.sql.ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                itemProduct.setItemProductId(rs.getInt("ItemProductId"));
                itemProduct.setName(rs.getString("Name"));
                itemProduct.setLabelName(rs.getString("LabelName"));
                itemProduct.setPrice(rs.getDouble("Price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // close the connection
        conn.close();

        return itemProduct;
    }

    public List<ItemProduct> loadItemProducts() throws ClassNotFoundException, SQLException {
        List<ItemProduct> itemProducts = new ArrayList<ItemProduct>();

        System.out.println("\n\nLoading ItemProducts..\n");

        // do connection to database
        Database db = new Database();
        Connection conn = Database.doConnection();

        // create a query to get all the item products
        String query = "SELECT * FROM itemproduct";

        // create a prepared statement
        PreparedStatement pstmt = conn.prepareStatement(query);

        // execute the query
        pstmt.executeQuery();

        // get the result set
        java.sql.ResultSet rs = pstmt.getResultSet();

        // loop through the result set
        while (rs.next()) {
            ItemProduct itemProduct = new ItemProduct();

            itemProduct.setItemProductId(rs.getInt("ItemProductId"));
            itemProduct.setName(rs.getString("Name"));
            itemProduct.setLabelName(rs.getString("LabelName"));
            itemProduct.setPrice(rs.getDouble("Price"));

            itemProducts.add(itemProduct);
        }

        // close the result set
        rs.close();

        // close the prepared statement
        pstmt.close();

        // close the connection
        conn.close();

        return itemProducts;
    }
}
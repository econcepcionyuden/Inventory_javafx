package util;

/**
 * Created by jaliya on 11/10/17.
 */

import models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {

    public static Product searchProduct(String id) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM product WHERE product.id = '" + id + "'";

        try {
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);
            Product product = getProductFromResultSet(rsEmp);
            return product;
        } catch (SQLException e) {
            System.out.println("While searching an product with '" + id + "' id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    public static Product searchProductByType(String type) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM product WHERE type='" + type + "'";

        try {

            ResultSet rsProduct = DBUtil.dbExecuteQuery(selectStmt);
            Product product = getProductFromResultSet(rsProduct);
            return product;
        } catch (SQLException e) {
            System.out.println("While searching an product with '" + type + "' id, an error occurred: " + e);

            throw e;
        }
    }

    private static Product getProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = null;
        if (rs.next()) {
            product = new Product();
            product.setProductId(rs.getString("id"));
            product.setTitle(rs.getString("title"));
            product.setType(rs.getString("type"));
            product.setDescription(rs.getString("description"));
            product.setUnitPrice(rs.getString("unit_price"));
            product.setQuantity(rs.getString("quantity"));
            product.setProductStatus(rs.getString("status"));
        }
        return product;
    }


    public static ObservableList<Product> searchProducts() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM product";

        try {

            ResultSet rsPrdcts = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Product> productList = getProductList(rsPrdcts);
            return productList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }



    public static ObservableList<Product> searchProductsByTitle(String title) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM product WHERE title='" + title + "'";

        try {

            ResultSet rsPrdcts = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Product> productList = getProductList(rsPrdcts);
            return productList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }


    public static ObservableList<Product> searchProductsByType(String type) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM product WHERE type='" + type + "'";

        try {

            ResultSet rsPrdcts = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Product> productList = getProductList(rsPrdcts);
            return productList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }


    private static ObservableList<Product> getProductList(ResultSet rs) throws SQLException, ClassNotFoundException {

        ObservableList<Product> productList = FXCollections.observableArrayList();
        while (rs.next()) {
            Product product = new Product();
            product.setProductId(rs.getString("id"));
            product.setTitle(rs.getString("title"));
            product.setType(rs.getString("type"));
            product.setDescription(rs.getString("description"));
            product.setUnitPrice(rs.getString("unit_price"));
            product.setQuantity(rs.getString("quantity"));
            product.setProductStatus(rs.getString("status"));
            productList.add(product);
        }

        return productList;
    }


    public static void updateProduct(String productId, String unitPrice, String quantity, String status) throws SQLException, ClassNotFoundException {


        if (unitPrice.isEmpty() && status.isEmpty()) {
            String updateStmt = "UPDATE product SET quantity = '" + quantity + "'  WHERE id = '" + productId + "'";
            try {
                DBUtil.dbExecuteUpdate(updateStmt);
            } catch (SQLException e) {
                System.out.print("Error occurred while UPDATE Operation: " + e);
                throw e;
            }

        } else if (quantity.isEmpty() && status.isEmpty()) {
            String updateStmt = "UPDATE product SET unit_price='" + unitPrice + "' WHERE id = '" + productId + "'";
            try {
                DBUtil.dbExecuteUpdate(updateStmt);
            } catch (SQLException e) {
                System.out.print("Error occurred while UPDATE Operation: " + e);
                throw e;
            }

        } else if (quantity.isEmpty() && unitPrice.isEmpty()) {
            String updateStmt = "UPDATE product SET status='" + status + "' WHERE id = '" + productId + "'";
            try {
                DBUtil.dbExecuteUpdate(updateStmt);
            } catch (SQLException e) {
                System.out.print("Error occurred while UPDATE Operation: " + e);
                throw e;
            }

        } else {

            String updateStmt = "UPDATE product SET unit_price='" + unitPrice + "', quantity = '" + quantity + "'  WHERE id = '" + productId + "'";
            try {
                DBUtil.dbExecuteUpdate(updateStmt);
            } catch (SQLException e) {
                System.out.print("Error occurred while UPDATE Operation: " + e);
                throw e;
            }
        }
    }


    public static void updateProductAfterSelling(String productId, String quantity) throws SQLException, ClassNotFoundException {
        String updateStmt = "UPDATE product SET quantity = '" + quantity + "'  WHERE id = '" + productId + "'";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }


    public static int productCount() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT COUNT(id) AS noOfProducts FROM product";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            int count = 0;
            if (rsCount.next()) {
                count = rsCount.getInt(1);
            }
            return count;

        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }


    public static void updateProductAfterPurchasing(String productId, String quantity) throws SQLException, ClassNotFoundException {
        String updateStmt = "UPDATE product SET quantity = '" + quantity + "'  WHERE id = '" + productId + "'";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }


    public static void deleteProductWithId(String productId) throws SQLException, ClassNotFoundException {
        String updateStmt = " DELETE FROM product WHERE id = '" + productId + "'";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }


    public static void insertProduct(String id, String title, String type, String description, String unit_price, String quantity, String status) throws SQLException, ClassNotFoundException {

        String updateStmt = "INSERT INTO product(id,title, type, description, unit_price, quantity, status) VALUES('" + id + "','" + title + "', '" + type + "','" + description + "', '" + unit_price + "', '" + quantity + "', '" + status + "')";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while insert Operation: " + e);
            throw e;
        }
    }
}
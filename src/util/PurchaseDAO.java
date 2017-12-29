package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Product;
import models.Purchase;
import models.Sale;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jaliya on 11/22/17.
 */
public class PurchaseDAO {


    public static Purchase searchPurchase(String purchaseId) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM purchase WHERE id='"+purchaseId+"'";


        try {

            ResultSet rsPurchase = DBUtil.dbExecuteQuery(selectStmt);


            Purchase purchase = getSaleFromResultSet(rsPurchase);


            return purchase;
        } catch (SQLException e) {
            System.out.println("While searching an purchase with '" + purchaseId + "' id, an error occurred: " + e);

            throw e;
        }
    }


    public static Purchase searchPurchaseByDate(String purchaseDate) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM purchase WHERE transaction_date='"+purchaseDate+"'";
        try {

            ResultSet rsPurchase = DBUtil.dbExecuteQuery(selectStmt);
            Purchase purchase = getSaleFromResultSet(rsPurchase);
            return purchase;
        } catch (SQLException e) {
            System.out.println("While searching an purchase with '" + purchaseDate + "' id, an error occurred: " + e);

            throw e;
        }
    }


    private static Purchase getSaleFromResultSet(ResultSet rs) throws SQLException
    {
        Purchase purchase = null;
        if (rs.next()) {
            purchase = new Purchase();
            purchase.setPurchaseId(rs.getString("id"));
            purchase.setTransaction_date(rs.getString("transaction_date"));
            purchase.setProductId(rs.getString("product_id"));
            purchase.setQuantity(rs.getString("quantity"));
            purchase.setAmount(rs.getString("amount"));
            purchase.setVendorId(rs.getString("vendor_id"));

        }
        return purchase;
    }


    public static ObservableList<Purchase> searchPurchases() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM purchase";
        try {
            ResultSet rsPurchase = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Purchase> purchaseList = getPurchaseList(rsPurchase);
            return purchaseList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }




    private static ObservableList<Purchase> getPurchaseList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Purchase> purchaseList = FXCollections.observableArrayList();
        while (rs.next()) {
            Purchase purchase = new Purchase();
            purchase.setPurchaseId(rs.getString("id"));
            purchase.setTransaction_date(rs.getString("transaction_date"));
            purchase.setAmount(rs.getString("amount"));
            purchase.setQuantity(rs.getString("quantity"));
            purchase.setProductId(rs.getString("product_id"));
            purchase.setVendorId(rs.getString("vendor_id"));
            purchaseList.add(purchase);
        }
        return purchaseList;
    }


    public static int purchaseCount(String date) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT COUNT(id) AS noOfPurchases FROM purchase where transaction_date > '"+ date +"'";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            int count = 0;
            if (rsCount.next()) {
                count = rsCount.getInt(1);
            }
            return count;

        } catch (SQLException e) {
            System.out.print("Error occurred while SELECT Operation: " + e);
            throw e;
        }
    }


    public static ObservableList<Purchase> totalPurchaseAmounts(String date) throws SQLException, ClassNotFoundException {

        String selectStmt = "select * from purchase where transaction_date > '"+ date +"'";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Purchase> purchaseList = getPurchasesList(rsCount);
            return purchaseList;

        } catch (SQLException e) {
            System.out.print("Error occurred while SELECT Operation: " + e);
            throw e;
        }
    }


    public static ObservableList<Purchase> previousTotalPurchaseAmounts(String previousDate, String date) throws SQLException, ClassNotFoundException {

        String selectStmt = "select * from purchase where transaction_date > '"+ previousDate +"' AND '"+ date +"' > transaction_date";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Purchase> purchaseList = getPurchasesList(rsCount);
            return purchaseList;

        } catch (SQLException e) {
            System.out.print("Error occurred while SELECT Operation: " + e);
            throw e;
        }
    }

    private static ObservableList<Purchase> getPurchasesList(ResultSet rs) throws SQLException, ClassNotFoundException {

        ObservableList<Purchase> purchaseList = FXCollections.observableArrayList();
        while (rs.next()) {
            Purchase purchase = new Purchase();
            purchase.setPurchaseId(rs.getString("id"));
            purchase.setTransaction_date(rs.getString("transaction_date"));
            purchase.setProductId(rs.getString("product_id"));
            purchase.setQuantity(rs.getString("quantity"));
            purchase.setAmount(rs.getString("amount"));
            purchase.setVendorId(rs.getString("vendor_id"));
            purchaseList.add(purchase);
        }
        return purchaseList;
    }



    public static int getTotalPurchaseByMonth() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT COUNT(id) AS noOfPurchases FROM purchase";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            int count = 0;
            if (rsCount.next()) {
                count = rsCount.getInt(1);
            }
            return count;

        } catch (SQLException e) {
            System.out.print("Error occurred while SELECT Operation: " + e);
            throw e;
        }
    }


    public static void purchase(String productId,String date, String amount, String quantity,String vendorId) throws SQLException, ClassNotFoundException {

        String updateStmt = "INSERT INTO purchase(transaction_date,amount,quantity,product_id,vendor_id) VALUES('"+date+"','"+amount+"','"+quantity+"','"+productId+"','"+vendorId+"')";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);

        } catch (SQLException e) {
            System.out.print("Error occurred while inserting purchase data: " + e);
            throw e;
        }
    }
}

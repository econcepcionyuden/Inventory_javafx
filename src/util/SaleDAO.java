package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Client;
import models.Sale;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jaliya on 11/21/17.
 */
public class SaleDAO {

    public static Sale searchSale(String saleId) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM sale WHERE id='"+saleId+"'";

        try {
            ResultSet rsSale = DBUtil.dbExecuteQuery(selectStmt);
            Sale sale = getSaleFromResultSet(rsSale);
            return sale;
        } catch (SQLException e) {
            System.out.println("While searching an sale  with '" + saleId + "' id, an error occurred: " + e);

            throw e;
        }
    }


    public static Sale searchSaleByDate(String saleDate) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM sale WHERE transaction_date='"+saleDate+"'";

        try {

            ResultSet rsSale = DBUtil.dbExecuteQuery(selectStmt);
            Sale sale = getSaleFromResultSet(rsSale);
            return sale;
        } catch (SQLException e) {
            System.out.println("While searching an sale with '" + saleDate + "' id, an error occurred: " + e);

            throw e;
        }
    }


    private static Sale getSaleFromResultSet(ResultSet rs) throws SQLException
    {
        Sale sale = null;
        if (rs.next()) {
            sale = new Sale();
            sale.setSaleId(rs.getString("id"));
            sale.setTransaction_date(rs.getString("transaction_date"));
            sale.setAmount(rs.getString("product_id"));
            sale.setQuantity(rs.getString("quantity"));
            sale.setQuantity(rs.getString("amount"));
            sale.setQuantity(rs.getString("client_id"));
        }
        return sale;
    }


    public static ObservableList<Client> getClients() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM client";

        try {

            ResultSet rsClients = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Client> clientList = getClientList(rsClients);
            return clientList;
        } catch (SQLException e) {
            System.out.println("SQL SELECT operation has been failed: " + e);
            throw e;
        }
    }
    public static ObservableList<Sale> searchSalesByDate(String saleDate) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM sale WHERE transaction_date='"+saleDate+"'";

        try {

            ResultSet rsSales = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Sale> saleList = getSaleList(rsSales);
            return saleList;
        } catch (SQLException e) {
            System.out.println("SQL SELECT operation has been failed: " + e);
            throw e;
        }
    }


    public static ObservableList<Sale> searchSales() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM sale";
        try {

            ResultSet rsSales = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Sale> saleList = getSaleList(rsSales);
            return saleList;
        } catch (SQLException e) {
            System.out.println("SQL SELECT operation has been failed: " + e);

            throw e;
        }
    }


    private static ObservableList<Client> getClientList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Client> clientList = FXCollections.observableArrayList();

        while (rs.next()) {
            Client client = new Client();
            client.setClientId(rs.getString("id"));
            client.setName(rs.getString("name"));
            client.setCompany(rs.getString("company"));
            clientList.add(client);
        }
        return clientList;
    }


    private static ObservableList<Sale> getSaleList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Sale> saleList = FXCollections.observableArrayList();

        while (rs.next()) {
            Sale sale = new Sale();
            sale.setSaleId(rs.getString("id"));
            sale.setTransaction_date(rs.getString("transaction_date"));
            sale.setQuantity(rs.getString("product_id"));
            sale.setAmount(rs.getString("amount"));
            sale.setQuantity(rs.getString("quantity"));
            sale.setProductId(rs.getString("product_id"));
            sale.setClientId(rs.getString("client_id"));
            saleList.add(sale);
        }

        return saleList;
    }


    public static int saleCount(String date) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT COUNT(id) AS noOfSales FROM sale where transaction_date > '"+ date +"'";
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

    public static void sell(int paymentId,String productId,String date, String amount, String quantity,String clientId) throws SQLException, ClassNotFoundException {

        String updateStmt = "INSERT INTO sale(payment_id,transaction_date,amount,quantity,product_id,client_id) VALUES('"+paymentId+"','"+date+"','"+amount+"','"+quantity+"','"+productId+"','"+clientId+"')";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);

        } catch (SQLException e) {
            System.out.print("Error occurred while inserting sales data: " + e);
            throw e;
        }
    }


    public static ObservableList<Sale> totalSaleAmounts(String date) throws SQLException, ClassNotFoundException {

        String selectStmt = "select * from sale where transaction_date > '"+ date +"'";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Sale> saleList = getSalesList(rsCount);
            return saleList;

        } catch (SQLException e) {
            System.out.print("Error occurred while SELECT Operation: " + e);
            throw e;
        }
    }


    public static ObservableList<Sale> previousTotalSaleAmounts(String previousDate, String date) throws SQLException, ClassNotFoundException {

        String selectStmt = "select * from sale where transaction_date > '"+ previousDate +"' AND '"+ date +"' > transaction_date";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Sale> saleList = getSalesList(rsCount);
            return saleList;

        } catch (SQLException e) {
            System.out.print("Error occurred while SELECT Operation: " + e);
            throw e;
        }
    }

    private static ObservableList<Sale> getSalesList(ResultSet rs) throws SQLException, ClassNotFoundException {

        ObservableList<Sale> saleList = FXCollections.observableArrayList();
        while (rs.next()) {
            Sale sale = new Sale();
            sale.setSaleId(rs.getString("id"));
            sale.setTransaction_date(rs.getString("transaction_date"));
            sale.setProductId(rs.getString("product_id"));
            sale.setQuantity(rs.getString("quantity"));
            sale.setAmount(rs.getString("amount"));
            sale.setClientId(rs.getString("client_id"));
            saleList.add(sale);
        }
        return saleList;
    }

}

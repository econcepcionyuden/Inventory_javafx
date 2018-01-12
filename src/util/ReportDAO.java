package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.MonthlyRecord;
import models.Purchase;
import models.Sale;
import models.SalesRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jaliya on 12/19/17.
 */
public class ReportDAO {

    public static void insertMonthlyRecord(String year, String month, String expense, String income, String profit, String turnover, String statusRatio, String goodProductCount,String expiredProductCount, String damagedProductCount ) throws SQLException, ClassNotFoundException {

        String updateStmt = "INSERT INTO monthly_records(year,month, expense, income, profit, inventory_turnover, inventory_status_ratio,good_product_count,expired_product_count,damaged_product_count) VALUES('" + year + "','" + month + "', '" + expense + "','" + income + "', '" + profit + "', '" + turnover + "', '" + statusRatio + "', '" + goodProductCount + "', '" + expiredProductCount + "', '" + damagedProductCount + "')";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while insert Operation: " + e);
            throw e;
        }
    }

    public static ObservableList<SalesRecord> getMonthlySales(String fromDate, String toDate) throws SQLException, ClassNotFoundException {

        String selectStmt = "select transaction_date,product_id,amount,quantity,sub_total,vat,discount,payeble from sale,payment where payment.id = sale.payment_id and sale.transaction_date >= '"+fromDate+"' and sale.transaction_date <= '"+toDate+"'";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<SalesRecord> recordList = getSalesRecordsList(rsCount);
            return recordList;
        } catch (SQLException e) {
            System.out.print("Error occurred while selecting monthly sales records" + e);
            throw e;
        }
    }

    public static ObservableList<Purchase> getMonthlyPurchase(String fromDate, String toDate) throws SQLException, ClassNotFoundException {

        String selectStmt = "select transaction_date,product_id,amount,quantity,vendor_id from purchase where purchase.transaction_date >= '"+fromDate+"' and purchase.transaction_date <= '"+toDate+"'";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Purchase> recordList = getPurchaseRecordsList(rsCount);
            return recordList;
        } catch (SQLException e) {
            System.out.print("Error occurred while selecting monthly purchase records" + e);
            throw e;
        }
    }


    public static ObservableList<MonthlyRecord> monthlyRecords() throws SQLException, ClassNotFoundException {

        String selectStmt = "select * from monthly_records ORDER BY ID asc LIMIT 0,6";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<MonthlyRecord> recordList = getRecordsList(rsCount);
            return recordList;

        } catch (SQLException e) {
            System.out.print("Error occurred while SELECT Operation: " + e);
            throw e;
        }
    }


    public static ObservableList<MonthlyRecord> monthlyRecordsBYRange(String fromDate, String toDate) throws SQLException, ClassNotFoundException {

        String selectStmt = "select id,year,month,expense,income,profit,inventory_turnover,inventory_status_ratio, STR_TO_DATE(CONCAT('01',',',month,',',year),'%d,%m,%Y') as fmt_date from monthly_records group by id having fmt_date between '"+fromDate+"' and '"+toDate+"'";

        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<MonthlyRecord> recordList = getRecords(rsCount);
            return recordList;

        } catch (SQLException e) {
            System.out.print("Error occurred while SELECT Operation: " + e);
            throw e;
        }
    }



    private static ObservableList<Purchase> getPurchaseRecordsList(ResultSet rs) throws SQLException, ClassNotFoundException {

        ObservableList<Purchase> recordsList = FXCollections.observableArrayList();
        while (rs.next()) {
            Purchase purchaseRecord = new Purchase();
            purchaseRecord.setTransaction_date(rs.getString("transaction_date"));
            purchaseRecord.setProductId(rs.getString("product_id"));
            purchaseRecord.setAmount(rs.getString("amount"));
            purchaseRecord.setQuantity(rs.getString("quantity"));
            purchaseRecord.setVendorId(rs.getString("vendor_id"));
            recordsList.add(purchaseRecord);
        }
        return recordsList;
    }


    private static ObservableList<SalesRecord> getSalesRecordsList(ResultSet rs) throws SQLException, ClassNotFoundException {

        ObservableList<SalesRecord> recordsList = FXCollections.observableArrayList();
        while (rs.next()) {
            SalesRecord salesRecord = new SalesRecord();
            salesRecord.setTransactionDate(rs.getString("transaction_date"));
            salesRecord.setProductId(rs.getString("product_id"));
            salesRecord.setUnitPrice(rs.getString("amount"));
            salesRecord.setQuantity(rs.getString("quantity"));
            salesRecord.setSubTotal(rs.getDouble("sub_total"));
            salesRecord.setVat(rs.getDouble("vat"));
            salesRecord.setDiscount(rs.getDouble("discount"));
            salesRecord.setPayable(rs.getDouble("payeble"));
            recordsList.add(salesRecord);
        }
        return recordsList;
    }


    private static ObservableList<MonthlyRecord> getRecordsList(ResultSet rs) throws SQLException, ClassNotFoundException {

        ObservableList<MonthlyRecord> recordsList = FXCollections.observableArrayList();
        while (rs.next()) {
            MonthlyRecord monthlyRecord = new MonthlyRecord();
            monthlyRecord.setRecordId(rs.getInt("id"));
            monthlyRecord.setYear(rs.getString("year"));
            monthlyRecord.setMonth(rs.getString("month"));
            monthlyRecord.setExpense(rs.getString("expense"));
            //monthlyRecord.setExpense(rs.getString("fmt_date"));
            monthlyRecord.setIncome(rs.getString("income"));
            monthlyRecord.setProfit(rs.getString("profit"));
            monthlyRecord.setInventoryTurnover(rs.getString("inventory_turnover"));
            monthlyRecord.setInventoryStatusRatio(rs.getString("inventory_status_ratio"));
            monthlyRecord.setGoodProductCount(rs.getString("good_product_count"));
            monthlyRecord.setExpiredProductCount(rs.getString("expired_product_count"));
            monthlyRecord.setDamagedProductCount(rs.getString("damaged_product_count"));
            recordsList.add(monthlyRecord);
        }
        return recordsList;
    }


    private static ObservableList<MonthlyRecord> getRecords(ResultSet rs) throws SQLException, ClassNotFoundException {

        ObservableList<MonthlyRecord> recordsList = FXCollections.observableArrayList();
        while (rs.next()) {
            MonthlyRecord monthlyRecord = new MonthlyRecord();
            monthlyRecord.setRecordId(rs.getInt("id"));
            monthlyRecord.setYear(rs.getString("year"));
            monthlyRecord.setMonth(rs.getString("month"));
            monthlyRecord.setExpense(rs.getString("expense"));
            monthlyRecord.setIncome(rs.getString("income"));
            monthlyRecord.setProfit(rs.getString("profit"));
            monthlyRecord.setInventoryTurnover(rs.getString("inventory_turnover"));
            monthlyRecord.setInventoryStatusRatio(rs.getString("inventory_status_ratio"));

            recordsList.add(monthlyRecord);
        }
        return recordsList;
    }

}

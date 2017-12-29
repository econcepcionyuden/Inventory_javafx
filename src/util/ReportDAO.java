package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.MonthlyRecord;
import models.Sale;

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


    private static ObservableList<MonthlyRecord> getRecordsList(ResultSet rs) throws SQLException, ClassNotFoundException {

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
            monthlyRecord.setGoodProductCount(rs.getString("good_product_count"));
            monthlyRecord.setExpiredProductCount(rs.getString("expired_product_count"));
            monthlyRecord.setDamagedProductCount(rs.getString("damaged_product_count"));
            recordsList.add(monthlyRecord);
        }
        return recordsList;
    }

}

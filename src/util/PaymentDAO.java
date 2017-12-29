package util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jaliya on 12/26/17.
 */
public class PaymentDAO {


    public static int getLastSaleId() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT id FROM sale ORDER BY id DESC LIMIT 1";
        int id = 0;
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            if (rsCount.next()) {
                id = rsCount.getInt(1);
            }
        } catch (SQLException e) {
            System.out.print("Error occurred while getting last sale id ");
            throw e;
        }
        return id;
    }

    public static int getLastPaymentId() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT id FROM payment ORDER BY id DESC LIMIT 1";
        int id = 0;
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            if (rsCount.next()) {
                id = rsCount.getInt(1);
            }
        } catch (SQLException e) {
            System.out.print("Error occurred while getting last payment id ");
            throw e;
        }
        return id;
    }

    public static void addPaymentRecord(String subTotal, String vat, String discount, String payable) throws SQLException, ClassNotFoundException {

        String updateStmt = "INSERT INTO payment(sub_total,vat,discount,payeble) VALUES('" + subTotal + "','" + vat + "','" + discount + "','" + payable + "')";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while inserting client data: ");
            throw e;
        }
    }



}

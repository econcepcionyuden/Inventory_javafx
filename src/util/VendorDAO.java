package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Vendor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jaliya on 12/6/17.
 */
public class VendorDAO {



    public static Vendor searchVendorByCompany(String searchByCompany) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM vendor WHERE company='"+searchByCompany+"'";
        try {
            ResultSet rsVendor = DBUtil.dbExecuteQuery(selectStmt);
            Vendor vendor = getVendorFromResultSet(rsVendor);
            return vendor;
        } catch (SQLException e) {
            System.out.println("While searching an vendor with '" + searchByCompany + "' id, an error occurred: " + e);
            throw e;
        }
    }



    public static Vendor searchVendorByName(String searchByName) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM vendor WHERE name='"+searchByName+"'";

        try {

            ResultSet rsVendor = DBUtil.dbExecuteQuery(selectStmt);
            Vendor vendor = getVendorFromResultSet(rsVendor);
            return vendor;
        } catch (SQLException e) {
            System.out.println("While searching an vendor with '" + searchByName + "' id, an error occurred: " + e);
            throw e;
        }
    }

    private static Vendor getVendorFromResultSet(ResultSet rs) throws SQLException
    {
        Vendor vendor = null;
        if (rs.next()) {
            vendor = new Vendor();
            vendor.setVendorId(rs.getString("id"));
            vendor.setName(rs.getString("name"));
            vendor.setCompany(rs.getString("company"));
            vendor.setAddress(rs.getString("address"));
            vendor.setContact(rs.getString("contact_no"));

        }
        return vendor;
    }


    public static ObservableList<Vendor> searchAllVendors() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM vendor";
        try {

            ResultSet rsVendors = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Vendor> vendorList = getVendorList(rsVendors);
            return vendorList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }


    private static ObservableList<Vendor> getVendorList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Vendor> vendorList = FXCollections.observableArrayList();

        while (rs.next()) {
            Vendor vendor = new Vendor();
            vendor.setVendorId(rs.getString("id"));
            vendor.setName(rs.getString("name"));
            vendor.setCompany(rs.getString("company"));
            vendor.setAddress(rs.getString("address"));
            vendor.setContact(rs.getString("contact_no"));
            vendorList.add(vendor);
        }
        return vendorList;
    }


    public static ObservableList<Vendor> getVendorIds() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT id FROM vendor";
        try {
            ResultSet rsVendorIds = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Vendor> vendorIdList = getVendorIdList(rsVendorIds);
            return vendorIdList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }


    private static ObservableList<Vendor> getVendorIdList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Vendor> vendorIdList = FXCollections.observableArrayList();

        while (rs.next()) {
            Vendor vendor = new Vendor();
            vendor.setVendorId(rs.getString("id"));
            vendorIdList.add(vendor);
        }
        return vendorIdList;
    }


    //INSERT a vendor
    public static void addVendor(String id,String name,String company, String address, String contact) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt = "INSERT INTO vendor(id,name,company,address,contact_no) VALUES('"+id+"','"+name+"','"+company+"','"+address+"','"+contact+"')";

        //Execute DELETE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);

        } catch (SQLException e) {
            System.out.print("Error occurred while inserting vendor data: " + e);
            throw e;
        }
    }

    public static int vendorCount() throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
        String selectStmt = "SELECT COUNT(id) AS noOfVendors FROM vendor";

        //Execute UPDATE operation
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



    //DELETE an user
    public static void deleteVendorWithId(String vendorId) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt = " DELETE FROM vendor WHERE id = '"+ vendorId +"'";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static void updateVendor(String vendorId, String name, String company,String contact,String address) throws SQLException, ClassNotFoundException {

        String updateStmt = "UPDATE vendor SET name = '" + name + "',company = '" + company + "',contact_no = '" + contact + "',address = '" + address + "' WHERE id = '" + vendorId + "'";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }

    }

}

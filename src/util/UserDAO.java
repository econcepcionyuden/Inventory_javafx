package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Client;
import models.Sale;
import models.User;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jaliya on 12/4/17.
 */
public class UserDAO {


    public static User searchUserByRole(String searchByRole) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM user WHERE role='"+searchByRole+"'";
        try {
            ResultSet rsUser = DBUtil.dbExecuteQuery(selectStmt);
            User user = getUserFromResultSet(rsUser);
            return user;
        } catch (SQLException e) {
            System.out.println("While searching an user with '" + searchByRole + "' id, an error occurred: " + e);

            throw e;
        }
    }


    public static User searchUserByName(String searchByName) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM user WHERE first_name='"+searchByName+"'";

        try {

            ResultSet rsUser = DBUtil.dbExecuteQuery(selectStmt);
            User user = getUserFromResultSet(rsUser);
            return user;
        } catch (SQLException e) {
            System.out.println("While searching an user with '" + searchByName + "' id, an error occurred: " + e);
            throw e;
        }
    }


    public static User searchUserById(String id) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM user WHERE id='"+id+"'";

        try {
            ResultSet rsUser = DBUtil.dbExecuteQuery(selectStmt);
            User user = getUserFromResultSet(rsUser);
            return user;
        } catch (SQLException e) {
            System.out.println("While searching an user with '" + id + "' id, an error occurred: " + e);
            throw e;
        }
    }


    public static void updateUser(int userId, String firstName, String lastName, String role,String password,String contact,String address) throws SQLException, ClassNotFoundException {

        String updateStmt = "UPDATE user SET first_name = '" + firstName + "',last_name = '" + lastName + "',role = '" + role + "',password = md5('" + password + "'),contact = '" + contact + "',address = '" + address + "' WHERE id = '" + userId + "'";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }

    }

    public static void updateUserWithImage(int userId, String firstName, String lastName, String role,String password,String contact,String address, String filePath) throws SQLException, ClassNotFoundException {

        String updateStmt = "UPDATE user SET first_name = '" + firstName + "',last_name = '" + lastName + "',role = '" + role + "',password = md5('" + password + "'),contact = '" + contact + "',address = '" + address + "',pro_pic = '" + filePath + "' WHERE id = '" + userId + "'";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }

    }

    private static User getUserFromResultSet(ResultSet rs) throws SQLException
    {
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setUserId(rs.getString("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setRole(rs.getString("role"));
            user.setAddress(rs.getString("address"));
            user.setContactNo(rs.getString("contact"));
            user.setPassword(rs.getString("password"));
            user.setPicLocation(rs.getString("pro_pic"));

        }
        return user;
    }


    public static ObservableList<User> searchAllUsers() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM user";
        try {

            ResultSet rsUsers = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<User> userList = getUserList(rsUsers);
            return userList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);

            throw e;
        }
    }


    public static ObservableList<User> searchUsersBYRole(String searchByRole) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM user WHERE role='"+searchByRole+"'";
        try {

            ResultSet rsUsers = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<User> userList = getUserList(rsUsers);
            return userList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);

            throw e;
        }
    }

    private static ObservableList<User> getUserList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<User> userList = FXCollections.observableArrayList();
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getString("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setRole(rs.getString("role"));
            user.setPassword(rs.getString("password"));
            user.setContactNo(rs.getString("contact"));
            user.setAddress(rs.getString("address"));
            userList.add(user);
        }
        return userList;
    }


    public static void addUser(String firstName,String lastName, String role, String password, String contact, String address, String picLocation) throws SQLException, ClassNotFoundException {

        String updateStmt = "INSERT INTO user(first_name,last_name,role,password,contact,address,pro_pic) VALUES('"+firstName+"','"+lastName+"','"+role+"',md5('"+password+"'),'"+contact+"','"+address+"','"+picLocation+"')";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while inserting user data: " + e);
            throw e;
        }
    }


    public static int userCount() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT COUNT(id) AS noOfUsers FROM user";
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




    public static void deleteUserWithId(String userId) throws SQLException, ClassNotFoundException {

        String updateStmt = " DELETE FROM user WHERE id = '"+ userId +"'";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }


}

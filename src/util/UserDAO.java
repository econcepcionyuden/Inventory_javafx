package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Client;
import models.Sale;
import models.User;

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


    public static void updateUser(String userId, String newName, String newRole) throws SQLException, ClassNotFoundException {


        if (newName.isEmpty()) {
            String updateStmt = "UPDATE user SET role = '" + newRole + "'  WHERE id = '" + userId + "'";
            try {
                DBUtil.dbExecuteUpdate(updateStmt);
            } catch (SQLException e) {
                System.out.print("Error occurred while UPDATE Operation: " + e);
                throw e;
            }

        } else if (newRole.isEmpty()) {
            String updateStmt = "UPDATE user SET first_name='" + newName + "' WHERE id = '" + userId + "'";
            try {
                DBUtil.dbExecuteUpdate(updateStmt);
            } catch (SQLException e) {
                System.out.print("Error occurred while UPDATE Operation: " + e);
                throw e;
            }
        } else {

            String updateStmt = "UPDATE user SET first_name='" + newName + "',first_name='" + newName + "' WHERE id = '" + userId + "'";
            try {
                DBUtil.dbExecuteUpdate(updateStmt);
            } catch (SQLException e) {
                System.out.print("Error occurred while UPDATE Operation: " + e);
                throw e;
            }

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


    private static User getUserFromResultSet(ResultSet rs) throws SQLException
    {
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setUserId(rs.getString("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setRole(rs.getString("role"));
            user.setPassword(rs.getString("password"));

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


    public static void addUser(String firstName,String lastName, String role, String password, String contact, String address) throws SQLException, ClassNotFoundException {

        String updateStmt = "INSERT INTO user(first_name,last_name,role,password,contact,address) VALUES('"+firstName+"','"+lastName+"','"+role+"','"+password+"','"+contact+"','"+address+"')";
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

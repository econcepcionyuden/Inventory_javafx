package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Client;
import models.Vendor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jaliya on 12/6/17.
 */
public class ClientDAO {


    //SELECT a client by company
    public static Client searchClientByCompany(String searchByCompany) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM client WHERE company='" + searchByCompany + "'";

        try {

            ResultSet rsClient = DBUtil.dbExecuteQuery(selectStmt);
            Client client = getClientFromResultSet(rsClient);
            return client;
        } catch (SQLException e) {
            System.out.println("While searching the client with '" + searchByCompany + "' company, an error occurred: ");
            throw e;
        }
    }


    //SELECT a client by name
    public static Client searchClientByName(String searchByName) throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM client WHERE name='" + searchByName + "'";


        try {
            ResultSet rsClient = DBUtil.dbExecuteQuery(selectStmt);
            Client client = getClientFromResultSet(rsClient);
            return client;
        } catch (SQLException e) {
            System.out.println("While searching an client with '" + searchByName + "' id, an error occurred: ");
            throw e;
        }
    }

    private static Client getClientFromResultSet(ResultSet rs) throws SQLException {
        Client client = null;
        if (rs.next()) {
            client = new Client();
            client.setClientId(rs.getString("id"));
            client.setName(rs.getString("name"));
            client.setCompany(rs.getString("company"));
            client.setAddress(rs.getString("address"));
            client.setContact(rs.getString("contact_no"));

        }
        return client;
    }

    //SELECT all clients
    public static ObservableList<Client> searchAllClients() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM client";
        try {
            ResultSet rsClients = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Client> clientList = getClientList(rsClients);
            return clientList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: ");
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
            client.setAddress(rs.getString("address"));
            client.setContact(rs.getString("contact_no"));
            clientList.add(client);
        }
        return clientList;
    }


    //INSERT a client
    public static void addClient(String id, String name, String company, String address, String contact) throws SQLException, ClassNotFoundException {

        String updateStmt = "INSERT INTO client(id,name,company,address,contact_no) VALUES('" + id + "','" + name + "','" + company + "','" + address + "','" + contact + "')";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while inserting client data: ");
            throw e;
        }
    }

    public static int clientCount() throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT COUNT(id) AS noOfClients FROM client";
        try {
            ResultSet rsCount = DBUtil.dbExecuteQuery(selectStmt);
            int count = 0;
            if (rsCount.next()) {
                count = rsCount.getInt(1);
            }
            return count;

        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: ");
            throw e;
        }
    }


    //DELETE a client
    public static void deleteClientWithId(String clientId) throws SQLException, ClassNotFoundException {

        String updateStmt = " DELETE FROM client WHERE id = '" + clientId + "'";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: ");
            throw e;
        }
    }


}


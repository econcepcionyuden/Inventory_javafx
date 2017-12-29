package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Client;
import util.ClientDAO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by jaliya on 12/6/17.
 */
public class ClientController {

    @FXML
    private TextField clientId;
    @FXML
    private TextField name;
    @FXML
    private TextField company;
    @FXML
    private TextField address;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> searchChoice;
    @FXML
    private TextField contact;
    @FXML
    private TableView clientTable;
    @FXML
    private TableColumn<Client, String> clientIdColumn;
    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> companyColumn;
    @FXML
    private TableColumn<Client, String> addressColumn;
    @FXML
    private TableColumn<Client, String> contactColumn;
    @FXML
    private TextField searchByCompany;
    @FXML
    private TextField searchByName;
    @FXML
    private Button backBtn2;

    ObservableList<String> criteriaList = FXCollections.observableArrayList("","By company", "By Name");
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        clientId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
    }

    @FXML
    public void companyClear(ActionEvent actionEvent) {
        searchByCompany.clear();
    }

    @FXML
    public void nameClear(ActionEvent actionEvent) {
        searchByName.clear();
    }


    //  Search a client by company and name
    @FXML
    private void searchClients(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {


        if (searchChoice.getValue().toString().equals("By company")) {
            try {

                Client client = ClientDAO.searchClientByCompany(searchField.getText());
                populateAndShowClient(client);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting client information from DB" + e);
                alert.showAndWait();
                throw e;
            }

        } else {
            try {

                Client client = ClientDAO.searchClientByName(searchField.getText());
                populateAndShowClient(client);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting client information from DB" + e);
                alert.showAndWait();
                throw e;
            }

        }

    }


    @FXML
    private void deleteRow(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        Client selectedItem = (Client) clientTable.getSelectionModel().getSelectedItem();
        String id = ((Client) clientTable.getSelectionModel().getSelectedItem()).getClientId();


        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Delete Confirmation");
        alert1.setHeaderText("Please confirm the action");
        alert1.setContentText("Are you sure you want to delete the client " + id + " \nfrom the system? ");
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                clientTable.getItems().remove(selectedItem);
                ClientDAO.deleteClientWithId(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Client delete");
                alert.setHeaderText("Success message");
                alert.setContentText("The client " + id + " was successfully deleted!!");
                alert.showAndWait();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while deleting client " + e);
                alert.showAndWait();
                throw e;
            }
        } else {

            alert1.close();
        }

    }


    //Search all clients
    @FXML
    private void searchAllClients(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {

            ObservableList<Client> clientData = ClientDAO.searchAllClients();
            populateClient(clientData);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting client information from DB" + e);
            alert.showAndWait();
            throw e;
        }
    }


    @FXML
    private void initialize() throws SQLException {

        searchChoice.setItems(criteriaList);
        clientIdColumn.setCellValueFactory(cellData -> cellData.getValue().clientIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        companyColumn.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        contactColumn.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
        try {

            ObservableList<Client> clientData = ClientDAO.searchAllClients();
            populateClient(clientData);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting client information from DB" + e);
            alert.showAndWait();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Populate vendors for TableView
    @FXML
    private void populateClient(ObservableList<Client> clientData) throws ClassNotFoundException {
        clientTable.setItems(clientData);
    }

    @FXML
    private void populateAndShowClient(Client client) throws ClassNotFoundException {
        if (client != null) {
            populateClient(client);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("This client does not exist!");
            alert.showAndWait();
        }
    }

    //Populate Product
    @FXML
    private void populateClient(Client client) throws ClassNotFoundException {

        ObservableList<Client> clientData = FXCollections.observableArrayList();
        clientData.add(client);
        clientTable.setItems(clientData);
    }

    @FXML
    private void backAction2(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == backBtn2) {
            stage = (Stage) backBtn2.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
            Scene scene = new Scene(root, 950, 550);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) clientTable.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) clientTable.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void addClient(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/addClient.fxml")));
        Parent root = loader.load();
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Client");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();
    }

}


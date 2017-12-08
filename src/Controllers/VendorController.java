package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import models.Vendor;
import util.UserDAO;
import util.VendorDAO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by jaliya on 12/6/17.
 */
public class VendorController {


    @FXML
    private TextField vendorId;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> searchChoice;
    @FXML
    private TextField name;
    @FXML
    private TextField company;
    @FXML
    private TextField address;
    @FXML
    private TextField contact;
    @FXML
    private TableView vendorTable;
    @FXML
    private TableColumn<Vendor, String> vendorIdColumn;
    @FXML
    private TableColumn<Vendor, String> nameColumn;
    @FXML
    private TableColumn<Vendor, String> companyColumn;
    @FXML
    private TableColumn<Vendor, String> addressColumn;
    @FXML
    private TableColumn<Vendor, String> contactColumn;
    @FXML
    private TextField searchByCompany;
    @FXML
    private TextField searchByName;
    @FXML
    private Button backBtn2;

    ObservableList<String> criteriaList = FXCollections.observableArrayList("","Company", "Name");

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        vendorId.clear();
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

    //  Search a vendor by company
    @FXML
    private void searchVendor(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        if (searchChoice.getValue().toString().equals("Company")) {

            try {
                Vendor vendor = VendorDAO.searchVendorByCompany(searchField.getText());
                populateAndShowVendor(vendor);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting vendor information from DB" + e);
                alert.showAndWait();
                throw e;
            }

        } else {

            try {

                Vendor vendor = VendorDAO.searchVendorByName(searchField.getText());
                populateAndShowVendor(vendor);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting vendor information from DB" + e);
                alert.showAndWait();
                throw e;
            }

        }

    }


    @FXML
    private void deleteRow(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        Vendor selectedItem = (Vendor) vendorTable.getSelectionModel().getSelectedItem();
        String id = ((Vendor) vendorTable.getSelectionModel().getSelectedItem()).getVendorId();
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Delete Confirmation");
        alert1.setHeaderText("Please confirm the action");
        alert1.setContentText("Are you sure you want to delete the vendor " + id + " \nfrom the system? ");
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                vendorTable.getItems().remove(selectedItem);
                VendorDAO.deleteVendorWithId(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vendor delete");
                alert.setHeaderText("Success message");
                alert.setContentText("The vendor " + id + " was successfully deleted!!");
                alert.showAndWait();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while deleting vendor " + e);
                alert.showAndWait();
                throw e;
            }
        } else {

            alert1.close();
        }

    }


    //Search all vendors
    @FXML
    private void searchAllVendors(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {

            ObservableList<Vendor> vendorData = VendorDAO.searchAllVendors();
            populateVendors(vendorData);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting user information from DB" + e);
            alert.showAndWait();
            throw e;

        }
    }


    @FXML
    private void initialize() throws SQLException {

        searchChoice.setItems(criteriaList);
        vendorIdColumn.setCellValueFactory(cellData -> cellData.getValue().vendorIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        companyColumn.setCellValueFactory(cellData -> cellData.getValue().companyProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        contactColumn.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
        try {

            ObservableList<Vendor> vendorData = VendorDAO.searchAllVendors();
            populateVendors(vendorData);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting user information from DB" + e);
            alert.showAndWait();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void populateVendors(ObservableList<Vendor> vendorData) throws ClassNotFoundException {
        vendorTable.setItems(vendorData);
    }

    @FXML
    private void populateAndShowVendor(Vendor vendor) throws ClassNotFoundException {
        if (vendor != null) {
            populateVendor(vendor);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("This vendor does not exist!");
            alert.showAndWait();
        }
    }


    @FXML
    private void populateVendor(Vendor vendor) throws ClassNotFoundException {

        ObservableList<Vendor> vendorData = FXCollections.observableArrayList();
        vendorData.add(vendor);
        vendorTable.setItems(vendorData);
    }


    @FXML
    private void addVendor(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        try {
            VendorDAO.addVendor(vendorId.getText(), name.getText(), company.getText(), address.getText(), contact.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add vendor");
            alert.setHeaderText("Success message");
            alert.setContentText("The vendor " + vendorId.getText() + " was successfully added!!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Problem occurred while adding the vendor");
            alert.showAndWait();
            throw e;
        }
    }

    @FXML
    private void backAction2(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == backBtn2) {
            stage = (Stage) backBtn2.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
            Scene scene = new Scene(root, 700, 400);
            stage.setScene(scene);
            stage.show();
        }

    }


}

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
import models.Product;
import models.Purchase;
import models.Vendor;
import util.ProductDAO;
import util.PurchaseDAO;
import util.VendorDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created by jaliya on 11/9/17.
 */
public class PurchaseController {

    ObservableList<String> vendorList = FXCollections.observableArrayList("", "V001", "V002", "V003");
    ObservableList<String> productList = FXCollections.observableArrayList("", "G001", "G002", "G003", "G004");
    ObservableList<String> criteriaList = FXCollections.observableArrayList("", "Date", "ID");

    @FXML
    private TextField purchaseDate;
    @FXML
    private TextField purchaseId;
    @FXML
    private TextField insertPurchaseId;
    @FXML
    private TextField amount;
    @FXML
    private ChoiceBox<String> vendorId;
    @FXML
    private ChoiceBox<String> productId;
    @FXML
    private ChoiceBox<String> searchChoice;
    @FXML
    private TextField quantity;
    @FXML
    private TextField searchField;
    @FXML
    private Button backBtn;
    @FXML
    private TableView purchaseTable;
    @FXML
    private TableColumn<Purchase, String> purchaseIdColumn;
    @FXML
    private TableColumn<Purchase, String> transactionDateColumn;
    @FXML
    private TableColumn<Purchase, String> purchasingPriceColumn;
    @FXML
    private TableColumn<Purchase, String> quantityColumn;


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);


    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        insertPurchaseId.clear();
        amount.clear();
        quantity.clear();
        productId.setValue("");
        vendorId.setValue("");
    }


    @FXML
    public void dateClear(ActionEvent actionEvent) {

        purchaseDate.clear();
    }

    @FXML
    public void idClear(ActionEvent actionEvent) {

        purchaseId.clear();
    }

    //  Search a purchase by date and ID
    @FXML
    private void searchPurchase(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        if (searchChoice.getValue().toString().equals("Date")) {

            try {
                //Get Sale information
                Purchase purchase = PurchaseDAO.searchPurchaseByDate(searchField.getText());
                //Populate Product on TableView and Display on TextArea
                populateAndShowSale(purchase);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product delete");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting product information from DB" + e);
                alert.showAndWait();
                throw e;
            }

        } else {

            try {
                Purchase purchase = PurchaseDAO.searchPurchase(searchField.getText());
                populateAndShowSale(purchase);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product delete");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting product information from DB" + e);
                alert.showAndWait();
                throw e;
            }

        }


    }



    //Search all purchases
    @FXML
    private void searchPurchases(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {

            ObservableList<Purchase> purchaseData = PurchaseDAO.searchPurchases();
            populatePurchases(purchaseData);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product delete");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while refreshing" + e);
            alert.showAndWait();
            throw e;
        }
    }


    @FXML
    private void populateAndShowSale(Purchase purchase) throws ClassNotFoundException {
        if (purchase != null) {
            populatePurchase(purchase);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product delete");
            alert.setHeaderText("Failure message");
            alert.setContentText("This product does not exist!");
            alert.showAndWait();
        }
    }


    @FXML
    private void populatePurchases(ObservableList<Purchase> purchaseData) throws ClassNotFoundException {
        //Set items to the productsTable
        purchaseTable.setItems(purchaseData);
    }


    @FXML
    private void populatePurchase(Purchase purchase) throws ClassNotFoundException {

        ObservableList<Purchase> purchaseData = FXCollections.observableArrayList();
        purchaseData.add(purchase);
        purchaseTable.setItems(purchaseData);
    }


    @FXML
    private void initialize() throws SQLException {

        productId.setItems(productList);
        vendorId.setItems(vendorList);
        searchChoice.setItems(criteriaList);
        purchaseIdColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseIdProperty());
        transactionDateColumn.setCellValueFactory(cellData -> cellData.getValue().transaction_dateProperty());
        purchasingPriceColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        try {

            ObservableList<Purchase> purchaseData = PurchaseDAO.searchPurchases();

            populatePurchases(purchaseData);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product delete");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting results from DB");
            alert.showAndWait();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


//        try {
//            //Get all users information
//            ObservableList<Vendor> vendorIds = VendorDAO.getVendorIds();
//
//            //Vendor vendor = new Vendor();
//            //vendorIds.add(vendor);
//            vendorId.setItems(vendorIds);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e;
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }


    @FXML
    private void purchase(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Product product = ProductDAO.searchProduct(productId.getValue().toString());
        int newQuantity = Integer.parseInt(product.getQuantity()) + Integer.parseInt(quantity.getText());
        String newQuantityString = String.valueOf(newQuantity);


        try {
            PurchaseDAO.purchase(insertPurchaseId.getText(), productId.getValue().toString(), date, amount.getText(), quantity.getText(), vendorId.getValue().toString());
            ProductDAO.updateProductAfterPurchasing(productId.getValue().toString(), newQuantityString);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add purchase");
            alert.setHeaderText("Success message");
            alert.setContentText("The purchase of " + productId.getValue().toString() + " product was successfully added!!");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while adding the transaction" + e);
            alert.showAndWait();
            throw e;
        }
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == backBtn) {
            stage = (Stage) backBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
            Scene scene = new Scene(root, 700, 600);
            stage.setScene(scene);
            stage.show();
        }

    }

}
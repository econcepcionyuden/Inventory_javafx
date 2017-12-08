package Controllers;

import models.Product;
import util.ProductDAO;
import util.SaleDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Sale;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jaliya on 11/20/17.
 */
public class SaleController {

    ObservableList<String> clientList = FXCollections.observableArrayList("","C001", "C002", "C003");
    ObservableList<String> productList = FXCollections.observableArrayList("","G001", "G002", "G003", "G004");
    ObservableList<String> criteriaList = FXCollections.observableArrayList("", "Date", "ID");

    @FXML
    private TextField insertSaleId;
    @FXML
    private TextField saleId;
    @FXML
    private ChoiceBox<String> productId;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField sellingPrice;
    @FXML
    private TextField quantity;
    @FXML
    private ChoiceBox<String> clientId;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> searchChoice;
    @FXML
    private Button backBtn2;
    @FXML
    private TableView saleTable;
    @FXML
    private TableColumn<Sale, String> saleIdColumn;
//    @FXML
//    private TableColumn<Sale, String> productColumn;
    @FXML
    private TableColumn<Sale, String> transactionDateColumn;
    @FXML
    private TableColumn<Sale, String> sellingPriceColumn;
    @FXML
    private TableColumn<Sale, String> quantityColumn;
    @FXML
    private Button dateClearBtn;
    @FXML
    private TextField saleDate;
    @FXML
    private Button idClearBtn;

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {

        insertSaleId.clear();
        sellingPrice.clear();
        quantity.clear();
        clientId.setValue("");
        productId.setValue("");
    }


    @FXML
    public void dateClear(ActionEvent actionEvent) {

        saleDate.clear();
    }

    @FXML
    public void idClear(ActionEvent actionEvent) {

        saleId.clear();
    }


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);

    //  Search a sale by date and id
    @FXML
    private void searchSale(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        if (searchChoice.getValue().toString().equals("Date")) {
            try {
                ObservableList<Sale> saleData = SaleDAO.searchSalesByDate(searchField.getText());
                populateSales(saleData);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting product information from DB" + e);
                alert.showAndWait();
                throw e;
            }

        } else {

            try {
                Sale sale = SaleDAO.searchSale(searchField.getText());
                populateAndShowSale(sale);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting product information from DB" + e);
                alert.showAndWait();
                throw e;
            }
        }

    }


    //Search all clients
    @FXML
    private static List getClients() throws SQLException, ClassNotFoundException {
        try {
            //Get all Clients information
            // ObservableList<Client> clientData = SaleDAO.getClients();

            List clients = SaleDAO.getClients();

            ObservableList<String> clientList = FXCollections.observableArrayList(clients);
            return clientList;
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting product information from DB" + e);
            alert.showAndWait();
            throw e;
        }
    }


    //Search all sales
    @FXML
    private void searchSales(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {

            ObservableList<Sale> saleData = SaleDAO.searchSales();
            populateSales(saleData);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while refreshing" + e);
            alert.showAndWait();
            throw e;
        }
    }




    @FXML
    private void populateAndShowSale(Sale sale) throws ClassNotFoundException {
        if (sale != null) {
            populateSale(sale);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("This product does not exist!");
            alert.showAndWait();
        }
    }


    @FXML
    private void populateSales(ObservableList<Sale> saleData) throws ClassNotFoundException {
        saleTable.setItems(saleData);
    }

    //Populate Product
    @FXML
    private void populateSale(Sale sale) throws ClassNotFoundException {

        ObservableList<Sale> saleData = FXCollections.observableArrayList();
        saleData.add(sale);
        saleTable.setItems(saleData);
    }



    @FXML
    private void initialize() throws SQLException {


        clientId.setItems(clientList);
        productId.setItems(productList);
        searchChoice.setItems(criteriaList);
        saleIdColumn.setCellValueFactory(cellData -> cellData.getValue().saleIdProperty());
       // productColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        transactionDateColumn.setCellValueFactory(cellData -> cellData.getValue().transaction_dateProperty());
        sellingPriceColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        try {

            ObservableList<Sale> saleData = SaleDAO.searchSales();

            populateSales(saleData);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting product information from DB" + e);
            alert.showAndWait();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void sell(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Product product = ProductDAO.searchProduct(productId.getValue().toString());

        int newQuantity = Integer.parseInt(product.getQuantity()) - Integer.parseInt(quantity.getText());

        String newQuantityString = String.valueOf(newQuantity);

        try {
            SaleDAO.sell(insertSaleId.getText(), productId.getValue().toString(), date, sellingPrice.getText(), quantity.getText(), clientId.getValue().toString());
            ProductDAO.updateProductAfterSelling(productId.getValue().toString(), newQuantityString);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add sale");
            alert.setHeaderText("Success message");
            alert.setContentText("The sale of " + productId.getValue().toString() + " product was successfully added!!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Problem occurred while adding transaction" + e);
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
            Scene scene = new Scene(root, 700, 600);
            stage.setScene(scene);
            stage.show();
        }

    }

}

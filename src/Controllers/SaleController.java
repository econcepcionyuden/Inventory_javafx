package Controllers;

import javafx.util.Duration;
import models.Client;
import models.Product;
import org.controlsfx.control.Notifications;
import util.ClientDAO;
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
    @FXML
    private TableColumn<Sale, String> transactionDateColumn;
    @FXML
    private TableColumn<Sale, String> productColumn;
    @FXML
    private TableColumn<Sale, String> sellingPriceColumn;
    @FXML
    private TableColumn<Sale, String> quantityColumn;
    @FXML
    private TableColumn<Sale, String> clientColumn;
    @FXML
    private Button dateClearBtn;
    @FXML
    private TextField saleDate;
    @FXML
    private Button idClearBtn;


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
                alert.setContentText("Error occurred while getting sales information from DB" + e);
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
                alert.setContentText("Error occurred while getting sales information from DB" + e);
                alert.showAndWait();
                throw e;
            }
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
            alert.setContentText("This transaction does not exist!");
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
    private void initialize() throws SQLException, ClassNotFoundException {

//        ObservableList<Product> productsData = ProductDAO.searchProducts();
//        ObservableList<Client> clientsData = ClientDAO.searchAllClients();
//
//        String productIds[] = new String[productsData.size()];
//        for (int i = 0; i < productsData.size(); i++) {
//            productIds[i] = productsData.get(i).getProductId();
//        }
//        String clientIds[] = new String[clientsData.size()];
//        for (int i = 0; i < clientsData.size(); i++) {
//            clientIds[i] = clientsData.get(i).getClientId();
//        }

//        ObservableList<String> productList = FXCollections.observableArrayList(productIds);
//        ObservableList<String> clientList = FXCollections.observableArrayList(clientIds);

        try {
            ObservableList<Sale> saleData = SaleDAO.searchSales();
            populateSales(saleData);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting sales information from DB" + e);
            alert.showAndWait();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        searchChoice.setItems(criteriaList);
        saleIdColumn.setCellValueFactory(cellData -> cellData.getValue().saleIdProperty());
        productColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        transactionDateColumn.setCellValueFactory(cellData -> cellData.getValue().transaction_dateProperty());
        sellingPriceColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        clientColumn.setCellValueFactory(cellData -> cellData.getValue().clientIdProperty());

        ObservableList<Product> productData = ProductDAO.productTypeCount();

        String[] title = new String[productData.size()];
        int[] quantities = new int[productData.size()];
        int[] reOrderPoints = new int[productData.size()];
        int[] surpluses = new int[productData.size()];
        for (int i = 0; i < productData.size(); i++) {
            quantities[i] = Integer.parseInt(productData.get(i).getQuantity());
            reOrderPoints[i] = Integer.parseInt(productData.get(i).getReOrderPoint());
            surpluses[i] = Integer.parseInt(productData.get(i).getSurplusPoint());
            title[i] = productData.get(i).getTitle();

            if (quantities[i] >= surpluses[i]) {

                Notifications.create()
                        .title("Attention")
                        .text("There is a surplus in " + title[i] + "")
                        .hideAfter(Duration.seconds(5))
                        .darkStyle()
                        .showWarning();

            }

        }

    }


//    @FXML
//    private void sell(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
//
//        Product product = ProductDAO.searchProduct(productId.getValue().toString());
//
//        int newQuantity = Integer.parseInt(product.getQuantity()) - Integer.parseInt(quantity.getText());
//
//        String newQuantityString = String.valueOf(newQuantity);
//
//        try {
//            SaleDAO.sell(productId.getValue().toString(), date, sellingPrice.getText(), quantity.getText(), clientId.getValue().toString());
//            ProductDAO.updateProductAfterSelling(productId.getValue().toString(), newQuantityString);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Add sale");
//            alert.setHeaderText("Success message");
//            alert.setContentText("The sale of " + productId.getValue().toString() + " product was successfully added!!");
//            alert.showAndWait();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Failure message");
//            alert.setContentText("Problem occurred while adding transaction" + e);
//            alert.showAndWait();
//            throw e;
//        }
//    }

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
        Stage stage = (Stage) saleTable.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) saleTable.getScene().getWindow();
        stage.setIconified(true);
    }
}

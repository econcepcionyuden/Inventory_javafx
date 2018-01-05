package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Product;
import models.Vendor;
import util.ProductDAO;
import util.PurchaseDAO;
import util.VendorDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jaliya on 12/28/17.
 */
public class AddPurchaseController {

    @FXML
    private ComboBox<String> productId;
    @FXML
    private ComboBox<String> vendorId;
    @FXML
    private TextField amount;
    @FXML
    private TextField quantity;
    @FXML
    private Label errorLabel;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);


    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        amount.clear();
        quantity.clear();
        productId.setValue("");
        vendorId.setValue("");
    }

    @FXML
    public void clear() {
        amount.clear();
        quantity.clear();
        productId.setValue("");
        vendorId.setValue("");
    }

    @FXML
    private void purchase(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Product product = ProductDAO.searchProduct(productId.getValue().toString());
        int newQuantity = Integer.parseInt(product.getQuantity()) + Integer.parseInt(quantity.getText());
        String newQuantityString = String.valueOf(newQuantity);

        if (validateInput()) {
            try {
                PurchaseDAO.purchase(productId.getValue().toString(), date, amount.getText(), quantity.getText(), vendorId.getValue().toString());
                ProductDAO.updateProductAfterPurchasing(productId.getValue().toString(), newQuantityString);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add purchase");
                alert.setHeaderText("Success message");
                alert.setContentText("The purchase of " + productId.getValue().toString() + " product was successfully added!!");
                alert.showAndWait();
                clear();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while adding the transaction" + e);
                alert.showAndWait();
                throw e;
            }
        }
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) vendorId.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {


        ObservableList<Product> productsData = ProductDAO.searchProducts();
        ObservableList<Vendor> vendorsData = VendorDAO.searchAllVendors();

        String productIds[] = new String[productsData.size()];
        for (int i = 0; i < productsData.size(); i++) {
            productIds[i] = productsData.get(i).getProductId();
        }
        String vendorIds[] = new String[vendorsData.size()];
        for (int i = 0; i < vendorsData.size(); i++) {
            vendorIds[i] = vendorsData.get(i).getVendorId();
        }

        ObservableList<String> productList = FXCollections.observableArrayList(productIds);
        ObservableList<String> vendorList = FXCollections.observableArrayList(vendorIds);

        productId.setItems(productList);
        vendorId.setItems(vendorList);
    }

    private boolean validateInput() {
        String errorMessage = "";
        if (productId.getValue().toString().matches("^[A-Za-z0-9\\s\\-_,-]+$")&&amount.getText().matches("^[0-9]{1,10}$")&&quantity.getText().matches("^[0-9]{1,10}$")&&vendorId.getValue().toString().matches("^[A-Za-z0-9\\s\\-_,-]+$")) {
            return true;
        } else {
            errorMessage += "Invalid input entered!!\n";
            errorLabel.setText(errorMessage);
        }
        return false;
    }
}

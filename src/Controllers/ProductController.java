package Controllers;

/**
 * Created by jaliya on 11/10/17.
 */



import util.ProductDAO;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ProductController {

    ObservableList<String> productType = FXCollections.observableArrayList("","Construction", "Electric", "Decoration","Other");


    @FXML
    private TextField insertProductId;

    @FXML
    private TextField productId;
    @FXML
    private TextArea description;
    @FXML
    private TextField newUnitPrice;
    @FXML
    private TextField title;
    @FXML
    private TextField newQuantity;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> searchChoice;
    @FXML
    private TextField unitPrice;
    @FXML
    private TextField reOrderPoint;
    @FXML
    private TextField surplusPoint;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private ChoiceBox<String> newStatus;
    @FXML
    private TextField quantity;
    @FXML
    private TableView productTable;
    @FXML
    private TableColumn<Product, String> productIdColumn;
    @FXML
    private TableColumn<Product, String> productStatusColumn;
    @FXML
    private TableColumn<Product, String> productTitleColumn;
    @FXML
    private TableColumn<Product, String> productTypeColumn;
    @FXML
    private TableColumn<Product, String> productUnitPriceColumn;
    @FXML
    private TableColumn<Product, String> productQuantityColumn;

    ObservableList<String> criteriaList = FXCollections.observableArrayList("", "Title", "Type");
    ObservableList<String> statusList = FXCollections.observableArrayList("", "In stock", "Returned","Expired");

    @FXML
    private Button backBtn;
    @FXML
    private Button addProducts;


    @FXML
    public void fieldsClear(ActionEvent actionEvent) {

        insertProductId.clear();
        title.clear();
        unitPrice.clear();
        quantity.clear();
        description.clear();
        type.setValue("");

    }


    //Search a product
    @FXML
    private void searchProduct(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        if (searchChoice.getValue().toString().equals("Title")) {
            try {

                ObservableList<Product> productData= ProductDAO.searchProductsByTitle(searchField.getText());
                populateProducts(productData);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product search");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while searching product " + e);
                alert.showAndWait();
                throw e;
            }
        } else {

            try {

                ObservableList<Product> productData= ProductDAO.searchProductsByType(searchField.getText());
                populateProducts(productData);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product search");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while searching product " + e);
                alert.showAndWait();
                throw e;
            }
        }

    }

    //Search all products
    @FXML
    private void searchProducts(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {

            ObservableList<Product> productData = ProductDAO.searchProducts();
            populateProducts(productData);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Products search");
            alert.setHeaderText("Failure message");
            alert.setContentText("Problem occurred while refreshing " + e);
            alert.showAndWait();
            throw e;
        }
    }


    @FXML
    private void initialize() throws SQLException {

        type.setItems(productType);
        searchChoice.setItems(criteriaList);
        newStatus.setItems(statusList);
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        productTitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        productTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        productUnitPriceColumn.setCellValueFactory(cellData -> cellData.getValue().unitPriceProperty());
        productQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        productStatusColumn.setCellValueFactory(cellData -> cellData.getValue().productStatusProperty());



        try {

            ObservableList<Product> productData = ProductDAO.searchProducts();
            populateProducts(productData);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Products search");
            alert.setHeaderText("Failure message");
            alert.setContentText("Problem occurred while searching products " + e);
            alert.showAndWait();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void populateProduct(Product product) throws ClassNotFoundException {

        ObservableList<Product> productData = FXCollections.observableArrayList();
        productData.add(product);
        productTable.setItems(productData);
    }


    @FXML
    private void populateAndShowProduct(Product product) throws ClassNotFoundException {
        if (product != null) {
            populateProduct(product);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Products search");
            alert.setHeaderText("Failure message");
            alert.setContentText("This product does not exist!");
            alert.showAndWait();
        }
    }

    //Populate Products for TableView
    @FXML
    private void populateProducts(ObservableList<Product> productData) throws ClassNotFoundException {
        //Set items to the productsTable
        productTable.setItems(productData);
    }


    //Update product's unit price and quantity is updated
    @FXML
    private void updateProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ProductDAO.updateProduct(productId.getText(), newUnitPrice.getText(), newQuantity.getText(),newStatus.getValue().toString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product update");
            alert.setHeaderText("Success message");
            alert.setContentText("The product "+ productId.getText() + " was successfully updated!!");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product delete");
            alert.setHeaderText("Failure message");
            alert.setContentText("Problem occurred while updating product " + e);
            alert.showAndWait();
        }
    }


    @FXML
    private void insertProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String status = "In stock";
        try {
            ProductDAO.insertProduct(insertProductId.getText(), title.getText(), type.getValue().toString(), description.getText(), unitPrice.getText(), quantity.getText(),status,reOrderPoint.getText(),surplusPoint.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product add");
            alert.setHeaderText("Success message");
            alert.setContentText("The product was successfully added!!");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product add");
            alert.setHeaderText("Failure message");
            alert.setContentText("Problem occurred while adding product " + e);
            alert.showAndWait();
            throw e;
        }
    }


    @FXML
    private void deleteRow(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        Product selectedItem = (Product) productTable.getSelectionModel().getSelectedItem();
        String id = ((Product) productTable.getSelectionModel().getSelectedItem()).getProductId();

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Delete Confirmation");
        alert1.setHeaderText("Please confirm the action");
        alert1.setContentText("Are you sure you want to delete the product "+id+" \nfrom the system? ");

        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK){

            try {
                ProductDAO.deleteProductWithId(id);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Product delete");
                alert2.setHeaderText("Success message");
                alert2.setContentText("The product "+ id + " was successfully deleted!!");
                alert2.showAndWait();
                productTable.getItems().remove(selectedItem);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while deleting " + id + "  product.\nThis product has been occurred in a transaction.");
                alert.showAndWait();
                throw e;
            }

        } else {

            alert1.close();
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

//
//    @FXML
//    private void addProduct(ActionEvent event) throws IOException {
//        Stage stage;
//        Parent root;
//
//        if (event.getSource() == addProducts) {
//            //get reference to the button's stage
//            stage = (Stage) addProducts.getScene().getWindow();
//            //load up OTHER FXML document
//            root = FXMLLoader.load(getClass().getResource("../views/addProducts.fxml"));
//            Scene scene = new Scene(root, 700, 450);
//            stage.setScene(scene);
//            stage.show();
//        }
//
//    }

//
//    @FXML
//    public void start(final Stage primaryStage) {
//        Button btn = new Button();
//        btn.setText("Open Dialog");
//        btn.setOnAction(
//                new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        final Stage dialog = new Stage();
//                        dialog.initModality(Modality.APPLICATION_MODAL);
//                        dialog.initOwner(primaryStage);
//                        VBox dialogVbox = new VBox(20);
//                        dialogVbox.getChildren().add(new Text("This is a Dialog"));
//                        Scene dialogScene = new Scene(dialogVbox, 300, 200);
//                        dialog.setScene(dialogScene);
//                        dialog.show();
//                    }
//                });
//    }

}
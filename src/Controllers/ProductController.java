package Controllers;

/**
 * Created by jaliya on 11/10/17.
 */


import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public class ProductController {

    ObservableList<String> productType = FXCollections.observableArrayList("", "Construction", "Electric", "Decoration", "Other");


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
    private DatePicker expireDate;
    @FXML
    private TextField reOrderPoint;
    @FXML
    private TextField surplusPoint;
    @FXML
    private ComboBox<String> typeBox;
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
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, String> expireDateColumn;
    @FXML
    private TableColumn<Product, String> reorderPointColumn;
    @FXML
    private TableColumn<Product, String> surplusPointColumn;

    private double xOffset = 0;
    private double yOffset = 0;


    public static String editableProductId = null;
    public static String editableProductTitle = null;
    public static String editableProductCategory = null;
    public static String editableProductUnitPrice = null;
    public static String editableProductQuantity = null;
    public static String editableProductDescription = null;
    public static String editableProductReorderPoint = null;
    public static String editableProductSurplusPoint = null;
    public static String editableProductExpireDate = null;
    public static String editableStatus = null;



    ObservableList<String> criteriaList = FXCollections.observableArrayList("", "Title", "Type");


    @FXML
    private Button backBtn;
    @FXML
    private Button addProducts;


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);




    @FXML
    public void fieldsClear(ActionEvent actionEvent) {

        insertProductId.clear();
        title.clear();
        unitPrice.clear();
        quantity.clear();
        description.clear();
        typeBox.setValue("");
        reOrderPoint.clear();
        surplusPoint.clear();
        expireDate.getEditor().clear();
        expireDate.setValue(null);

    }


    @FXML
    public void fieldsClear2(ActionEvent actionEvent) {
        newQuantity.clear();
        newUnitPrice.clear();
        productId.clear();
        newQuantity.clear();
        newStatus.setValue("");
    }

    @FXML
    public void addProduct() throws Exception {

        FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/addProduct.fxml")));
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
        stage.setTitle("Add Product");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();

    }


    //Search a product
    @FXML
    private void searchProduct() throws ClassNotFoundException, SQLException {

        if (searchChoice.getValue().toString().equals("Title")) {
            try {
                ObservableList<Product> productData = ProductDAO.searchProductsByTitle(searchField.getText());
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
                ObservableList<Product> productData = ProductDAO.searchProductsByType(searchField.getText());
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
    private void searchProducts() throws SQLException, ClassNotFoundException {
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
    private void initialize() throws SQLException, ParseException {


        searchChoice.setItems(criteriaList);
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        productTitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        productTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        productUnitPriceColumn.setCellValueFactory(cellData -> cellData.getValue().unitPriceProperty());
        productQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        productStatusColumn.setCellValueFactory(cellData -> cellData.getValue().productStatusProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        expireDateColumn.setCellValueFactory(cellData -> cellData.getValue().expireDateProperty());
        reorderPointColumn.setCellValueFactory(cellData -> cellData.getValue().reOrderPointProperty());
        surplusPointColumn.setCellValueFactory(cellData -> cellData.getValue().surplusPointProperty());



        try {

            ObservableList<Product> productData = ProductDAO.searchProducts();
            populateProducts(productData);
            String[] expireDate = new String[productData.size()];
            String[] id = new String[productData.size()];
            for (int i = 0; i < productData.size(); i++) {
                expireDate[i] = productData.get(i).getExpireDate();
                id[i] = productData.get(i).getProductId();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date expireDateString = format.parse(expireDate[i]);

                Date in = new Date();
                LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
                Date today = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

                if (expireDateString.before(today)) {
                    ProductDAO.updateProductStatus(id[i]);
                }
            }


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Products loading");
            alert.setHeaderText("Failure message");
            alert.setContentText("Problem occurred while loading products " + e);
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

//
//    //Update product's unit price and quantity is updated
//    @FXML
//    private void updateProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
//        try {
//            ProductDAO.updateProduct(productId.getText(), newUnitPrice.getText(), newQuantity.getText(), newStatus.getValue().toString());
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Product update");
//            alert.setHeaderText("Success message");
//            alert.setContentText("The product " + productId.getText() + " was successfully updated!!");
//            alert.showAndWait();
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Product delete");
//            alert.setHeaderText("Failure message");
//            alert.setContentText("Problem occurred while updating product " + e);
//            alert.showAndWait();
//        }
//    }



    @FXML
    private void editAction() throws SQLException, ClassNotFoundException, IOException {

        Product product = (Product) productTable.getSelectionModel().getSelectedItem();

        editableProductId = product.getProductId();
        editableProductTitle = product.getTitle();
        editableProductCategory = product.getType();
        editableProductUnitPrice = product.getUnitPrice();
        editableProductQuantity = product.getQuantity();
        editableProductDescription = product.getDescription();
        editableProductReorderPoint = product.getReOrderPoint();
        editableProductSurplusPoint = product.getSurplusPoint();
        editableProductExpireDate = product.getExpireDate();
        editableStatus = product.getProductStatus();



        FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/editProduct.fxml")));
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
        stage.setTitle("Add Product");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    private void insertProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String status = "In stock";
        try {
            ProductDAO.insertProduct(insertProductId.getText(), title.getText(), typeBox.getValue().toString(), description.getText(), unitPrice.getText(), quantity.getText(), status, reOrderPoint.getText(), surplusPoint.getText(), date, expireDate.getValue().toString());
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
    private void deleteRow() throws ClassNotFoundException, SQLException {

        Product selectedItem = (Product) productTable.getSelectionModel().getSelectedItem();
        String id = ((Product) productTable.getSelectionModel().getSelectedItem()).getProductId();

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Delete Confirmation");
        alert1.setHeaderText("Please confirm the action");
        alert1.setContentText("Are you sure you want to delete the product " + id + " \nfrom the system? ");

        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                ProductDAO.deleteProductWithId(id);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Product delete");
                alert2.setHeaderText("Success message");
                alert2.setContentText("The product " + id + " was successfully deleted!!");
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
    private void backAction() throws IOException {
        Stage stage;
        Parent root;

            stage = (Stage) productTable.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
            Scene scene = new Scene(root,1170, 600);
            stage.setScene(scene);
            stage.show();

    }


    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) productTable.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) productTable.getScene().getWindow();
        stage.setIconified(true);
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
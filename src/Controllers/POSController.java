package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import org.controlsfx.control.textfield.TextFields;
import util.ClientDAO;
import util.PaymentDAO;
import util.ProductDAO;
import util.SaleDAO;

public class POSController {

    @FXML
    private TableView<Product> productTableView;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<Item> listTableView;
    @FXML
    private MenuItem logoutItem;
    @FXML
    private MenuButton menuButton;
    @FXML
    private TableColumn<Item, String> itemColumn;
    @FXML
    private ChoiceBox<String> clientId;
    @FXML
    private Button backBtn2;
    @FXML
    private TableColumn<Item, Double> priceColumn, totalColumn;
    @FXML
    private TableColumn<Item, Integer> quantityColumn;
    @FXML
    private TextField searchField, productField, priceField, quantityField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField subTotalField, discountField, vatField, netPayableField;
    @FXML
    private Button addButton, removeButton, paymentButton;
    @FXML
    private Label quantityLabel;
    @FXML
    private ObservableList<Item> ITEMLIST;
    @FXML
    private TableColumn<Product, String> productColumn;
    // private ProductModel productModel;

    private double xOffset = 0;
    private double yOffset = 0;

    public static double finalNetPayablePrice = 0.0;
    public static double finalSubTotalPrice = 0.0;
    public static double finalVat = 0.0;
    public static double finalDiscount = 0.0;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);

    @FXML
    private void searchProduct(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

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
    }

    @FXML
    private void populateProducts(ObservableList<Product> productData) throws ClassNotFoundException {
        //Set items to the productsTable
        productTableView.setItems(productData);
    }

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException, IOException {

        ObservableList<Product> productsData = ProductDAO.searchProducts();
        populateProducts(productsData);

        String role = LoginController.sessionRole;

        if (role.equals("Operator")) {
            backBtn2.setVisible(false);
            menuButton.setText(role);
        } else {

            menuButton.setVisible(false);
        }

        ObservableList<Client> clientsData = ClientDAO.searchAllClients();
        String clientIds[] = new String[clientsData.size()];
        for (int i = 0; i < clientsData.size(); i++) {
            clientIds[i] = clientsData.get(i).getClientId();
        }
        ObservableList<String> clientList = FXCollections.observableArrayList(clientIds);
        clientId.setItems(clientList);
        productColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        ITEMLIST = FXCollections.observableArrayList();

        ObservableList<Product> productData = ProductDAO.searchProducts();

        String possibleProducts[] = new String[productData.size()];
        for (int i = 0; i < productData.size(); i++) {
            possibleProducts[i] = productData.get(i).getTitle();
        }


//      //  productModel = new ProductModel();
//
//        loadData();
//
//        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        productTableView.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> showDetails(newValue));
//        productTableView.setItems(PRODUCTLIST);
//
//        filterData();
//
        TextFields.bindAutoCompletion(searchField, possibleProducts);
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        listTableView.setItems(ITEMLIST);
//
//        addButton
//                .disableProperty()
//                .bind(Bindings.isEmpty(productTableView.getSelectionModel().getSelectedItems()));
//        removeButton
//                .disableProperty()
//                .bind(Bindings.isEmpty(listTableView.getSelectionModel().getSelectedItems()));
        productTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));

    }

    private void showDetails(Product product) {
        if (product != null) {
            quantityField.setDisable(false);
            productField.setText(product.getTitle());
            priceField.setText(product.getUnitPrice());

            String quantity = product.getQuantity();
            double doubleQuantity = Double.parseDouble(quantity);


            if (doubleQuantity > 0) {
                quantityField.setEditable(true);
                quantityField.setStyle(null);
            } else {
                quantityField.setEditable(false);
                quantityField.setStyle("-fx-background-color: red;");
            }
            quantityLabel.setText("Available: " + String.valueOf(quantity));
            descriptionArea.setText(product.getDescription());
        } else {
            productField.setText("");
            priceField.setText("");
            quantityLabel.setText("");
            descriptionArea.setText("");
        }
    }

//    private void filterData() {
//        FilteredList<Product> searchedData = new FilteredList<>(PRODUCTLIST, e -> true);
//
//        searchField.setOnKeyReleased(e -> {
//            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//                searchedData.setPredicate(product -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    if (product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else if (product.getDescription().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }
//                    return false;
//                });
//            });
//
//            SortedList<Product> sortedData = new SortedList<>(searchedData);
//            sortedData.comparatorProperty().bind(productTableView.comparatorProperty());
//            productTableView.setItems(sortedData);
//        });
//    }

//    private void loadData() {
//        if (!PRODUCTLIST.isEmpty()) {
//            PRODUCTLIST.clear();
//        }
//
//        PRODUCTLIST.addAll(productModel.getProducts());
//    }


//    private void showDetails(Product product) {
//        if (product != null) {
//            quantityField.setDisable(false);
//            productField.setText(product.getProductName());
//            priceField.setText(String.valueOf(product.getPrice()));
//
//            double quantity = product.getQuantity();
//
//            if (quantity > 0) {
//                quantityField.setEditable(true);
//                quantityField.setStyle(null);
//            } else {
//                quantityField.setEditable(false);
//                quantityField.setStyle("-fx-background-color: red;");
//            }
//            quantityLabel.setText("Stock: " + String.valueOf(quantity));
//            descriptionArea.setText(product.getDescription());
//        } else {
//            productField.setText("");
//            priceField.setText("");
//            quantityLabel.setText("");
//            descriptionArea.setText("");
//        }
//    }

//    private void resetProductTableSelection() {
//        productTableView.getSelectionModel().clearSelection();
//    }
//
    @FXML
    private void resetItemTable() {
        ITEMLIST.clear();
        subTotalField.clear();
        vatField.clear();
        netPayableField.clear();
        discountField.clear();
        finalNetPayablePrice = 0.0;
        finalSubTotalPrice = 0.0;
        finalVat = 0.0;
        finalDiscount = 0.0;

    }
//
//    private void resetAdd() {
//        productField.setText("");
//        priceField.setText("");
//        quantityField.setText("");
//        resetQuantityField();
//        quantityLabel.setText("Available: ");
//        descriptionArea.setText("");
//    }

    private void resetInvoice() {
        subTotalField.setText("0.00");
        vatField.setText("0.00");
        netPayableField.setText("0.00");
    }
//
//    private void resetQuantityField() {
//        quantityField.setDisable(true);
//    }
//
//    private void resetPaymentButton() {
//        paymentButton.setDisable(true);
//    }
//
    private void resetInterface() {
//        loadData();
//        resetPaymentButton();
//        resetProductTableSelection();
//        resetQuantityField();
        resetItemTable();
        resetAdd();
        resetInvoice();
    }

    @FXML
    public void resetAction(ActionEvent event) {

        productField.clear();
        priceField.clear();
        quantityLabel.setText("");
        descriptionArea.clear();
        quantityField.clear();
        clientId.setValue("");

    }

    @FXML
    public void addAction(ActionEvent event) {

        if (validateInput()) {
        String productName = productField.getText();
        double unitPrice = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        double total = unitPrice * quantity;
        ITEMLIST.add(new Item(productName, unitPrice, quantity, total));
        calculation();

        resetAdd();
        productTableView.getSelectionModel().clearSelection();
        }
    }

    private boolean validateInput() {

        String errorMessage = "";

        if (quantityField.getText() == null || quantityField.getText().length() == 0) {
            errorMessage += "Please fill the quantity!\n";
        } else {
            double quantity = Double.parseDouble(quantityField.getText());
            String available = quantityLabel.getText();
            double availableQuantity = Double.parseDouble(available.substring(11));

            if (quantity > availableQuantity) {
                errorMessage += "Out of Stock!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please input the valid number of products");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            quantityField.setText("");

            return false;
        }
    }


    private boolean validateInput2() {

        String errorMessage = "";

        if (clientId.getValue()==null) {
            errorMessage += "Please select a client for the transaction!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Payment failure!");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }


    @FXML
    private void calculation() {

        double subTotalPrice = 0.0;
        subTotalPrice = listTableView.getItems().stream().map(
                (item) -> item.getTotal()).reduce(subTotalPrice, (accumulator, _item) -> accumulator + _item);
        double vat = (double) subTotalPrice * 0.025;
        double discount = subTotalPrice * 0.05;
        double netPayablePrice = (double) (Math.abs((subTotalPrice + vat) - discount));

        if (subTotalPrice > 0) {
            paymentButton.setDisable(false);
//            vat = (double) subTotalPrice * 0.025;
//            netPayablePrice = (double) (Math.abs((subTotalPrice + vat) - discount));

            subTotalField.setText(String.valueOf(subTotalPrice));
            vatField.setText(String.valueOf(vat));
            netPayableField.setText(String.valueOf(netPayablePrice));
            discountField.setText(String.valueOf(discount));

            finalSubTotalPrice = subTotalPrice;
            finalVat = vat;
            finalDiscount = discount;
            finalNetPayablePrice = netPayablePrice;

        }
    }

    private void resetAdd() {
        productField.setText("");
        priceField.setText("");
        quantityField.setText("");
        //resetQuantityField();
        quantityLabel.setText("Available: ");
        descriptionArea.setText("");
    }

    //    private void calculation() {
//
//        double subTotalPrice = 0.0;
//        subTotalPrice = listTableView.getItems().stream().map(
//                (item) -> item.getTotal()).reduce(subTotalPrice, (accumulator, _item) -> accumulator + _item);
//
//        if (subTotalPrice > 0) {
//            paymentButton.setDisable(false);
//            double vat = (double) subTotalPrice * 0.025;
//            double netPayablePrice = (double) (Math.abs((subTotalPrice + vat) - 5));
//
//            subTotalField.setText(String.valueOf(subTotalPrice));
//            vatField.setText(String.valueOf(vat));
//            netPayableField.setText(String.valueOf(netPayablePrice));
//        }
//    }



    @FXML
    public void paymentAction(ActionEvent event) throws Exception {


        if(validateInput2()) {

            Payment payment = new Payment(
                    Double.parseDouble(subTotalField.getText().trim()),
                    Double.parseDouble(vatField.getText().trim()),
                    Double.parseDouble(discountField.getText().trim()),
                    Double.parseDouble(netPayableField.getText().trim())
            );

            ObservableList<Item> sold = listTableView.getItems();

            FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/invoice.fxml")));
//        InvoiceController controller = new InvoiceController();
//        loader.setController(controller);
//        controller.setData(Double.parseDouble(netPayableField.getText().trim()), sold, payment);
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
            stage.setTitle("Payment");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();

//        resetInterface();


            // String itemName [] = new String[0];

            TableColumn<Item, String> column1 = itemColumn;
            TableColumn<Item, Integer> column2 = quantityColumn;
            List<String> columnItemData = new ArrayList<>();
            List<String> columnQuantityData = new ArrayList<>();
            for (Item item : listTableView.getItems()) {
                columnItemData.add(column1.getCellObservableValue(item).getValue());
                columnQuantityData.add(String.valueOf(column2.getCellObservableValue(item).getValue()));
            }

            for (int i = 0; i < columnItemData.size(); i++) {

                try {
                    int paymentId = PaymentDAO.getLastPaymentId();
                    Product product = ProductDAO.searchProductByTitle(columnItemData.get(i));
                    String id = product.getProductId();
                    String sellingPrice = product.getUnitPrice();
                    int newQuantity = Integer.parseInt(product.getQuantity()) - Integer.parseInt(columnQuantityData.get(i));
                    String stringNewQuantity = String.valueOf(newQuantity);

                    SaleDAO.sell(paymentId, id, date, sellingPrice, columnQuantityData.get(i), clientId.getValue().toString());
                    ProductDAO.updateProductQuantity(columnItemData.get(i), stringNewQuantity);

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

        }
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
    public void removeAction(ActionEvent event) {

        Integer index = listTableView.getSelectionModel().getSelectedIndex();

        double dropValue = 0.0;

        if (index.equals(-1)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Failure message");
            alert.setContentText("Please select an item to remove");
            alert.showAndWait();

        } else {
            dropValue = listTableView.getSelectionModel().getSelectedItem().getTotal();
            finalSubTotalPrice = finalSubTotalPrice - dropValue;
            listTableView.getSelectionModel().clearSelection();
//            resetInvoice();
            calculation();
        }
    }

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == logoutItem) {
            //get reference to the button's stage
            stage = (Stage) backBtn2.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
            Scene scene = new Scene(root, 625, 400);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void maximizeRevert() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.setMaximized(false);
    }

    @FXML
    private void maximizeAction() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.setMaximized(true);
    }
//
//    private boolean validateInput() {
//
//        String errorMessage = "";
//
//        if (quantityField.getText() == null || quantityField.getText().length() == 0) {
//            errorMessage += "Quantity not supplied!\n";
//        } else {
//            double quantity = Double.parseDouble(quantityField.getText());
//            String available = quantityLabel.getText();
//            double availableQuantity = Double.parseDouble(available.substring(7));
//
//            if (quantity > availableQuantity) {
//                errorMessage += "Out of Stock!\n";
//            }
//        }
//
//        if (errorMessage.length() == 0) {
//            return true;
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Warning");
//            alert.setHeaderText("Please input the valid number of products");
//            alert.setContentText(errorMessage);
//            alert.showAndWait();
//            quantityField.setText("");
//
//            return false;
//        }
//    }
//
//    @FXML
//    public void logoutAction(ActionEvent event) throws Exception {
//        ((Node) (event.getSource())).getScene().getWindow().hide();
//        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
//        Stage stage = new Stage();
//        root.setOnMousePressed((MouseEvent e) -> {
//            xOffset = e.getSceneX();
//            yOffset = e.getSceneY();
//        });
//        root.setOnMouseDragged((MouseEvent e) -> {
//            stage.setX(e.getScreenX() - xOffset);
//            stage.setY(e.getScreenY() - yOffset);
//        });
//
//        Scene scene = new Scene(root);
//        stage.setTitle("Inventory:: Version 1.0");
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
//    }
}
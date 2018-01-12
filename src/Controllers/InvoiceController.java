package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Item;
import models.Payment;
import models.Product;
import util.PaymentDAO;

public class InvoiceController {

    @FXML
    private TextField totalAmountField, paidAmountField;
    private ObservableList<Item> items;
    private Payment payment;

    private double xOffset = 0;
    private double yOffset = 0;

    POSController pos = new POSController();
    double netPrice = pos.finalNetPayablePrice;



    @FXML
    private void initialize() throws SQLException, ClassNotFoundException, IOException {

        POSController pos = new POSController();
        double netPrice = pos.finalNetPayablePrice;
        totalAmountField.setText(String.valueOf(netPrice));
//        productModel = new ProductModel();
//        employeeModel = new EmployeeModel();
//        salesModel = new SalesModel();
//        invoiceModel = new InvoiceModel();

    }
//
    public void setData(double netPrice, ObservableList<Item> items, Payment payment) {

        POSController pos = new POSController();
        this.netPrice = netPrice;
        this.items = pos.itemList;
        this.payment = payment;

    }

    @FXML
    public void confirmAction(ActionEvent event) throws Exception {

        if (validateInput()) {
            double paid = Double.parseDouble(paidAmountField.getText().trim());
            double retail = Math.abs(paid - netPrice);

           // int saleId = PaymentDAO.getLastSaleId();
            double subTotal = pos.finalSubTotalPrice;
            double discount = pos.finalDiscount;
            double vat = pos.finalVat;
            double payable = (subTotal + vat) - discount;

            PaymentDAO.addPaymentRecord(String.valueOf(subTotal), String.valueOf(vat), String.valueOf(discount),String.valueOf(payable));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selling Transaction");
            alert.setHeaderText("Success message");
            alert.setContentText("The transaction was successful!!");
            alert.showAndWait();

            POSController pos = new POSController();
            String invoiceId = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
            BillController controller = new BillController();
            controller.setData(retail, pos.itemList, invoiceId);


            FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/bill.fxml")));
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
            stage.setTitle("Bill");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();




         //   pos.resetItemTable();




//            Invoice invoice = new Invoice(
//                    invoiceId,
//                    employeeModel.getEmployee(2),
//                    payment.getSubTotal(),
//                    payment.getVat(),
//                    payment.getDiscount(),
//                    payment.getPayable(),
//                    paid,
//                    retail
//            );
//
//            invoiceModel.saveInvoice(invoice);
//
//            for (Item i : items) {
//
//                Product p = productModel.getProductByName(i.getItemName());
//                double quantity = p.getQuantity() - i.getQuantity();
//                p.setQuantity(quantity);
//                productModel.decreaseProduct(p);
//
//                Sale sale = new Sale(
//                        invoiceModel.getInvoice(invoiceId),
//                        productModel.getProductByName(i.getItemName()),
//                        i.getQuantity(),
//                        i.getUnitPrice(),
//                        i.getTotal()
//                );
//
//                salesModel.saveSale(sale);
//            }

//            FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/Confirm.fxml")));

//            loader.setController(controller);
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            root.setOnMousePressed((MouseEvent e) -> {
//                xOffset = e.getSceneX();
//                yOffset = e.getSceneY();
//            });
//            root.setOnMouseDragged((MouseEvent e) -> {
//                stage.setX(e.getScreenX() - xOffset);
//                stage.setY(e.getScreenY() - yOffset);
//            });
//            stage.setTitle("Confirm");
//            stage.getIcons().add(new Image("/images/logo.png"));
//            stage.setScene(scene);
//            stage.show();
        }

    }

    private boolean validateInput() {

        String errorMessage = "";

        if (paidAmountField.getText() == null || paidAmountField.getText().length() == 0) {
            errorMessage += "Invalid Input!\n";
        } else if (Double.parseDouble(paidAmountField.getText()) < netPrice) {
            errorMessage += "Insufficient Input!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please input the valid amount");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            paidAmountField.setText("");

            return false;
        }
    }

    @FXML
    private void closeAction() {
        Stage stage = (Stage) totalAmountField.getScene().getWindow();
        stage.close();
    }

//    @FXML
//    public void closeAction(ActionEvent event) {
//        ((Node) (event.getSource())).getScene().getWindow().hide();
//    }
}
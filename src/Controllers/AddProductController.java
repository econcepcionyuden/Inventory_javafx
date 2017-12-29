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
import models.Product;
import util.ProductDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jaliya on 12/27/17.
 */
public class AddProductController {

    ObservableList<String> productType = FXCollections.observableArrayList("", "Construction", "Electric", "Decoration", "Other");


    @FXML
    private TextField insertProductId;
    @FXML
    private TextArea description;
    @FXML
    private TextField title;
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
    private TextField quantity;


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);


    @FXML
    private void initialize() throws SQLException, ParseException {
        typeBox.setItems(productType);

    }


    @FXML
    private void insertProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String status = "Good";
        try {
            ProductDAO.insertProduct(insertProductId.getText(), title.getText(), typeBox.getValue().toString(), description.getText(), unitPrice.getText(), quantity.getText(), status, reOrderPoint.getText(), surplusPoint.getText(), date, expireDate.getValue().toString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product add");
            alert.setHeaderText("Success message");
            alert.setContentText("The product was successfully added!!");
            alert.showAndWait();
            clear();
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
    private void closeButtonAction() {
        Stage stage = (Stage) description.getScene().getWindow();
        stage.close();
    }

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

    private void clear() {
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
}

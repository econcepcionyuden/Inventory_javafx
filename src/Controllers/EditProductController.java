package Controllers;

import Controllers.ProductController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.ProductDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;

/**
 * Created by jaliya on 12/29/17.
 */
public class EditProductController {

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
    @FXML
    private ComboBox<String> status;
    @FXML
    private Label errorLabel;




    @FXML
    private void initialize() throws SQLException, ParseException {


        ProductController pc = new ProductController();
        insertProductId.setText(pc.editableProductId);
        description.setText(pc.editableProductDescription);
        title.setText(pc.editableProductTitle);
        unitPrice.setText(pc.editableProductUnitPrice);
        expireDate.setValue(LocalDate.parse(pc.editableProductExpireDate));
        reOrderPoint.setText(pc.editableProductReorderPoint);
        surplusPoint.setText(pc.editableProductSurplusPoint);
        typeBox.setValue(pc.editableProductCategory);
        quantity.setText(pc.editableProductQuantity);
        status.setValue(pc.editableStatus);

    }


    @FXML
    private void updateProduct(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (validateInput()) {
            try {

                ProductDAO.updateProduct(insertProductId.getText(), description.getText(), title.getText(), unitPrice.getText(), expireDate.getValue().toString(), reOrderPoint.getText(), surplusPoint.getText(), typeBox.getValue().toString(), quantity.getText(), status.getValue().toString());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product update");
                alert.setHeaderText("Success message");
                alert.setContentText("The product " + insertProductId.getText() + " was successfully updated!!");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product delete");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while updating product " + e);
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        insertProductId.clear();
        description.clear();
        title.clear();
        unitPrice.clear();
        reOrderPoint.clear();
        expireDate.setValue(null);
        surplusPoint.clear();
        typeBox.setValue("");
        quantity.clear();
        status.setValue("");
    }

    @FXML
    public void clear() {
        insertProductId.clear();
        description.clear();
        title.clear();
        unitPrice.clear();
        reOrderPoint.clear();
        surplusPoint.clear();
        typeBox.setValue("");
        expireDate.setValue(null);
        quantity.clear();
        status.setValue("");
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) description.getScene().getWindow();
        stage.close();
    }


    private boolean validateInput() {
        String errorMessage = "";
        if (title.getText().matches("^[A-Za-z0-9\\s\\-_,-]+$")&&unitPrice.getText().matches("^[0-9]{1,10}$")&&quantity.getText().matches("^[0-9]{1,10}$")&&description.getText().matches("^[A-Za-z0-9\\s\\-_,-]+$")&&typeBox.getValue().toString().matches("^[a-zA-Z\\s]+") && reOrderPoint.getText().matches("^[0-9]{1,10}$")&& expireDate.getValue().toString().matches("^(19[5-9][0-9]|20[0-4][0-9]|2050)[-/](0?[1-9]|1[0-2])[-/](0?[1-9]|[12][0-9]|3[01])$")&& surplusPoint.getText().matches("^[0-9]{1,10}$")) {
            return true;
        } else {
            errorMessage += "Invalid input entered!!\n";
            errorLabel.setText(errorMessage);
        }
        return false;
    }
}

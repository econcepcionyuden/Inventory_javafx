package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ClientDAO;
import util.VendorDAO;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by jaliya on 12/29/17.
 */
public class EditVendorController {


    @FXML
    private TextField name;
    @FXML
    private TextField company;
    @FXML
    private TextField address;
    @FXML
    private TextField contact;
    @FXML
    private TextField vendorId;
    @FXML
    private Label errorLabel;


    @FXML
    private void initialize() throws SQLException, ParseException {

        VendorController vc = new VendorController();
        vendorId.setDisable(true);
        vendorId.setText(vc.editableVendorId);
        name.setText(vc.editableName);
        company.setText(vc.editableCompany);
        address.setText(vc.editableAddress);
        contact.setText(vc.editableContactNo);
    }

    @FXML
    private void updateVendor(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (validateInput()) {
            try {

                VendorDAO.updateVendor(vendorId.getText(), name.getText(), company.getText(), contact.getText(), address.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Supplier update");
                alert.setHeaderText("Success message");
                alert.setContentText("The supplier was successfully updated!!");
                alert.showAndWait();
                errorLabel.setText("");
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Supplier Update");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while updating supplier " + e);
                alert.showAndWait();
            }
        }
    }

    private boolean validateInput() {
        String errorMessage = "";
        if (!(vendorId.getText() == null || vendorId.getText().length() == 0 || name.getText() == null || name.getText().length() == 0 || company.getText() == null || company.getText().length() == 0 || address.getText() == null || address.getText().length() == 0 || contact.getText() == null || contact.getText().length() == 0)) {

            if (vendorId.getText().matches("^[A-Za-z0-9\\s\\-]+$") && name.getText().matches("^[a-zA-Z\\s]+") && company.getText().matches("^[A-Za-z0-9\\s\\-]+$") && address.getText().matches("^[A-Za-z0-9\\s\\-_,/-]+$") && contact.getText().matches("^[0-9]{10,15}$")) {
                return true;
            } else {
                errorMessage += "Invalid input entered!!\n";
                errorLabel.setText(errorMessage);
            }
            return false;
        }
        errorMessage += "Please fill all required fields!\n";
        errorLabel.setText(errorMessage);
        return false;
    }


    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) vendorId.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        vendorId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
    }

    @FXML
    public void clear() {
        vendorId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
    }

}

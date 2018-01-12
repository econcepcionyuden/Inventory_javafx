package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.VendorDAO;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Created by jaliya on 12/28/17.
 */
public class AddVendorController {

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
    public void clear() {
        vendorId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
        errorLabel.setText("");
    }

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        vendorId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
        errorLabel.setText("");
    }



    @FXML
    private void addVendor(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        if (validateInput()) {
            try {
                VendorDAO.addVendor(vendorId.getText(), name.getText(), company.getText(), address.getText(), contact.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add vendor");
                alert.setHeaderText("Success message");
                alert.setContentText("The vendor " + vendorId.getText() + " was successfully added!!");
                alert.showAndWait();
                clear();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while adding the vendor");
                alert.showAndWait();
                throw e;
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
        Stage stage = (Stage) contact.getScene().getWindow();
        stage.close();
    }
}

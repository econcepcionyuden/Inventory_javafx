package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    public void clear() {
        vendorId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
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
    private void addVendor(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {

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



    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) contact.getScene().getWindow();
        stage.close();
    }
}

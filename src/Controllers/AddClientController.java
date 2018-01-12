package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ClientDAO;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by jaliya on 12/28/17.
 */
public class AddClientController {

    @FXML
    private TextField clientId;
    @FXML
    private TextField name;
    @FXML
    private TextField company;
    @FXML
    private TextField address;
    @FXML
    private TextField contact;
    @FXML
    private Label errorLabel;

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        clientId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
        errorLabel.setText("");
    }

    @FXML
    private void addClient(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {


        if(validateInput()) {
            try {
                ClientDAO.addClient(clientId.getText(), name.getText(), company.getText(), address.getText(), contact.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add client");
                alert.setHeaderText("Success message");
                alert.setContentText("The client " + clientId.getText() + " was successfully added!!");
                alert.showAndWait();
                clear();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while adding the client");
                alert.showAndWait();
                throw e;
            }
        }
    }

    private boolean validateInput() {
        String errorMessage = "";
        if (!(name.getText() == null || name.getText().length() == 0 || company.getText() == null || company.getText().length() == 0 || address.getText() == null || address.getText().length() == 0 || contact.getText() == null || contact.getText().length() == 0)) {

            if (name.getText().matches("^[a-zA-Z\\s]+")&&company.getText().matches("^[a-zA-Z\\s]+")&&address.getText().matches("^[a-zA-Z\\s]+")&&contact.getText().matches("^[0-9]{10,15}$")) {
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
    public void clear() {
        clientId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
        errorLabel.setText("");
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) contact.getScene().getWindow();
        stage.close();
    }


}

package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ClientDAO;
import util.UserDAO;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by jaliya on 12/29/17.
 */
public class EditClientController {


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
    private void initialize() throws SQLException, ParseException {


        clientId.setDisable(true);
        ClientController cc = new ClientController();
        clientId.setText(cc.editableClientId);
        name.setText(cc.editableName);
        company.setText(cc.editableCompany);
        address.setText(cc.editableAddress);
        contact.setText(cc.editableContactNo);
    }

    @FXML
    private void updateClient(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        if(validateInput()) {
            try {

                ClientDAO.updateClient(clientId.getText(), name.getText(), company.getText(), contact.getText(), address.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Client update");
                alert.setHeaderText("Success message");
                alert.setContentText("The client was successfully updated!!");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Client Update");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while updating client " + e);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) clientId.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        clientId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
    }

    @FXML
    public void clear() {
        clientId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
    }

    private boolean validateInput() {
        String errorMessage = "";
        if (name.getText().matches("^[a-zA-Z\\s]+")&&company.getText().matches("^[a-zA-Z\\s]+")&&address.getText().matches("^[a-zA-Z\\s]+")&&contact.getText().matches("^[0-9]{10,15}$")) {
            return true;
        } else {
            errorMessage += "Invalid input entered!!\n";
            errorLabel.setText(errorMessage);
        }
        return false;
    }

}

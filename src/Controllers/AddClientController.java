package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    public void fieldsClear(ActionEvent actionEvent) {
        clientId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
    }

    @FXML
    private void addClient(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {

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

    @FXML
    public void clear() {
        clientId.clear();
        name.clear();
        company.clear();
        address.clear();
        contact.clear();
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) contact.getScene().getWindow();
        stage.close();
    }

}

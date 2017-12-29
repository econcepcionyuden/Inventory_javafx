package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.UserDAO;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by jaliya on 12/28/17.
 */
public class AddUserController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField password;
    @FXML
    private ComboBox<String> role;
    @FXML
    private TextField contactNo;
    @FXML
    private TextArea address;


    ObservableList<String> roleList = FXCollections.observableArrayList("", "Admin", "Operator");

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {

        firstName.clear();
        lastName.clear();
        password.clear();
        role.setValue("");
        contactNo.clear();
        address.clear();
    }


    @FXML
    private void addUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        try {
            UserDAO.addUser(firstName.getText(), lastName.getText(), role.getValue().toString(), password.getText(),contactNo.getText(),address.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add user");
            alert.setHeaderText("Success message");
            alert.setContentText("The user " + firstName.getText() + " was successfully added!!");
            alert.showAndWait();
            clear();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Problem occurred while adding the user" + e);
            alert.showAndWait();
            throw e;
        }
    }

    private void clear() {

        firstName.clear();
        lastName.clear();
        password.clear();
        role.setValue("");
        contactNo.clear();
        address.clear();

    }

    @FXML
    private void initialize() throws SQLException, ParseException {
        role.setItems(roleList);
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) role.getScene().getWindow();
        stage.close();
    }



}

package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ProductDAO;
import util.UserDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;

/**
 * Created by jaliya on 12/29/17.
 */
public class EditUserController {

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


    ObservableList<String> newRoleList = FXCollections.observableArrayList("", "Admin", "Operator");

    @FXML
    private void initialize() throws SQLException, ParseException {


        role.setItems(newRoleList);
        UserController uc = new UserController();
        password.setText(uc.editablePassword);
        firstName.setText(uc.editableFirstName);
        lastName.setText(uc.editableLastName);
        role.setValue(uc.editableRole);
        contactNo.setText(uc.editableContactNo);
        address.setText(uc.editableAddress);
    }



    @FXML
    public void fieldsClear(ActionEvent actionEvent) {
        firstName.clear();
        lastName.clear();
        password.clear();
        password.clear();
        role.setValue("");
        contactNo.clear();
        address.clear();
    }

    @FXML
    public void clear() {
        firstName.clear();
        lastName.clear();
        password.clear();
        password.clear();
        role.setValue("");
        contactNo.clear();
        address.clear();
    }


    @FXML
    private void updateUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        UserController uc = new UserController();
        int id = Integer.parseInt(uc.editableUserId);
        try {

            UserDAO.updateUser(id,firstName.getText(), lastName.getText(), role.getValue().toString(), password.getText(), contactNo.getText(), address.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User update");
            alert.setHeaderText("Success message");
            alert.setContentText("The user was successfully updated!!");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User delete");
            alert.setHeaderText("Failure message");
            alert.setContentText("Problem occurred while updating user " + e);
            alert.showAndWait();
        }
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) firstName.getScene().getWindow();
        stage.close();
    }
}

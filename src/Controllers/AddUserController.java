package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.UserDAO;

import java.security.MessageDigest;
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
    @FXML
    private Label errorLabel;


    ObservableList<String> roleList = FXCollections.observableArrayList("", "Admin", "Operator");

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {

        firstName.clear();
        lastName.clear();
        password.clear();
        role.setValue("");
        contactNo.clear();
        address.clear();
        errorLabel.setText("");
    }


    @FXML
    private void addUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {


        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getText().getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        String imageLocation = "/home/jaliya/github/Inventory_javafx/src/Scenes/default-user.png";

        if (validateInput()) {

            try {
                UserDAO.addUser(firstName.getText(), lastName.getText(), role.getValue().toString(), password.getText(), contactNo.getText(), address.getText(), imageLocation);
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
    }


    private boolean validateInput() {
        String errorMessage = "";
        if (!(firstName.getText() == null || firstName.getText().length() == 0 || lastName.getText() == null || lastName.getText().length() == 0 || role.getValue() == null || role.getValue().length() == 0 || password.getText() == null || password.getText().length() == 0 || contactNo.getText() == null || contactNo.getText().length() == 0 || address.getText() == null || address.getText().length() == 0)) {

            if (firstName.getText().matches("^[a-zA-Z\\s]+") && lastName.getText().matches("^[a-zA-Z\\s]+") && role.getValue().toString().matches("^[a-zA-Z\\s]+") && password.getText().matches("^[A-Za-z0-9\\s\\-_$%&*+/@!-]+$") && contactNo.getText().matches("^[0-9]{10,15}$") && address.getText().matches("^[A-Za-z0-9\\s\\-_,-]+$")) {
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

    private void clear() {

        firstName.clear();
        lastName.clear();
        password.clear();
        role.setValue("");
        contactNo.clear();
        address.clear();
        errorLabel.setText("");

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

package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.User;
import util.DBUtil;
import util.UserDAO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by jaliya on 12/29/17.
 */
public class ProfileEditUserController {

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

    @FXML
    private ImageView pic;

    private Image image;
    private FileInputStream fis;
    private File file;


    ObservableList<String> newRoleList = FXCollections.observableArrayList("", "Admin", "Operator");


    @FXML
    private void initialize() throws SQLException, ParseException, ClassNotFoundException, FileNotFoundException {

        LoginController lg = new LoginController();
        User user = UserDAO.searchUserById(lg.userId);
        String id = user.getUserId();

        role.setItems(newRoleList);
        //password.setText(uc.editablePassword);
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        role.setValue(user.getRole());
        contactNo.setText(user.getContactNo());
        address.setText(user.getAddress());

        Image image = new Image(new FileInputStream(user.getPicLocation()));
        pic.setImage(image);
    }

    @FXML
    private void browse(ActionEvent actionEvent)  {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG =
                new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg =
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG =
                new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

        file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            pic.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(EditUserController.class.getName()).log(Level.SEVERE, null, ex);
        }



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
        errorLabel.setText("");
    }


    @FXML
    private void updateUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, FileNotFoundException {


        LoginController lg = new LoginController();
        int id = Integer.parseInt(lg.userId);

        if (validateInput()&&validatePassword()) {
            try {
                UserDAO.updateUserWithImage(id, firstName.getText(), lastName.getText(), role.getValue().toString(), password.getText(), contactNo.getText(), address.getText(), file.getPath());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User update");
                alert.setHeaderText("Success message");
                alert.setContentText("The user was successfully updated!!");
                alert.showAndWait();
                errorLabel.setText("");
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User delete");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while updating user " + e);
                alert.showAndWait();
            }
        }
    }

    private boolean validateInput() {
        String errorMessage = "";
        if (!(firstName.getText() == null || firstName.getText().length() == 0 || lastName.getText() == null || lastName.getText().length() == 0 || role.getValue() == null || role.getValue().length() == 0 || contactNo.getText() == null || contactNo.getText().length() == 0 || address.getText() == null || address.getText().length() == 0)) {

            if (firstName.getText().matches("^[a-zA-Z\\s]+") && lastName.getText().matches("^[a-zA-Z\\s]+") && role.getValue().toString().matches("^[a-zA-Z\\s]+") && contactNo.getText().matches("^[0-9]{10,15}$") && address.getText().matches("^[A-Za-z0-9\\s\\-_,-]+$")) {
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

    private boolean validatePassword() {
        String errorMessage = "";
        if (!(password.getText() == null || password.getText().length() == 0)) {

            if (password.getText().matches("^[A-Za-z0-9\\s\\-#%$*&@!_,-]+$")) {
                return true;
            } else {
                errorMessage += "Invalid characters in the password!!\n";
                errorLabel.setText(errorMessage);
            }
            return false;
        }
        errorMessage += "Please enter the password to change details!\n";
        errorLabel.setText(errorMessage);
        return false;
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) firstName.getScene().getWindow();
        stage.close();
    }
}

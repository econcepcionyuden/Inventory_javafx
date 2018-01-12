package Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.User;
import util.UserDAO;
import javafx.scene.image.Image;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by jaliya on 1/8/18.
 */
public class ProfileController {

    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label address;
    @FXML
    private Label contact;
    @FXML
    private Label role;
    @FXML
    private CheckBox pos;
    @FXML
    private CheckBox userCheck;
    @FXML
    private CheckBox inventory;
    @FXML
    private CheckBox stakeholder;
    @FXML
    private CheckBox report;
    @FXML
    private ImageView proPic;


    private double xOffset = 0;
    private double yOffset = 0;
    String userRole = null;
    String userContact = null;
    String userAddress = null;
    String userLastName = null;
    String userFirstName = null;

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException, IOException {

        LoginController lg = new LoginController();
        User user = UserDAO.searchUserById(lg.userId);

        String id = user.getUserId();
        userFirstName = user.getFirstName();
        userLastName = user.getLastName();
        userAddress = user.getAddress();
        userContact = user.getContactNo();
        userRole = user.getRole();
        Image image = new Image(new FileInputStream(user.getPicLocation()));
        proPic.setImage(image);

        firstName.setText(userFirstName);
        lastName.setText(userLastName);
        address.setText(userAddress);
        contact.setText(userContact);
        role.setText(userRole);

        if(userRole.equals("Admin")){

            pos.setSelected(true);
            userCheck.setSelected(true);
            inventory.setSelected(true);
            stakeholder.setSelected(true);
            report.setSelected(true);

        } else {

            pos.setSelected(true);

        }


        pos.setDisable(true);
        userCheck.setDisable(true);
        inventory.setDisable(true);
        stakeholder.setDisable(true);
        report.setDisable(true);




    }

    @FXML
    private void editAction() throws SQLException, ClassNotFoundException, IOException {


        FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/profileEditUser.fxml")));
        Parent root = loader.load();
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit User");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    private void backAction2() throws IOException {
        Stage stage;
        Parent root;

        if (userRole.equals("Admin")) {
            stage = (Stage) contact.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
            Scene scene = new Scene(root, 1170, 600);
            stage.setScene(scene);
            stage.show();
        } else {

            stage = (Stage) contact.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/POS.fxml"));
            Scene scene = new Scene(root, 1070, 610);
            stage.setScene(scene);
            stage.show();

        }


    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) contact.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) contact.getScene().getWindow();
        stage.setIconified(true);
    }


}

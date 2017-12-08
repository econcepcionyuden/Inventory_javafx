package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import util.ProductDAO;
import util.UserDAO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;


/**
 * Created by jaliya on 12/5/17.
 */
public class UserController {

    @FXML
    private TextField userId;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> searchChoice;
    @FXML
    private TextField searchById;
    @FXML
    private TextField searchByName;
    @FXML
    private TextField password;
    @FXML
    private ChoiceBox<String> role;
    @FXML
    private TableView userTable;
    @FXML
    private TableColumn<User, String> userIdColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private Button backBtn2;
    @FXML
    private Button reset;

    ObservableList<String> roleList = FXCollections.observableArrayList("","Admin", "Operator");
    ObservableList<String> criteriaList = FXCollections.observableArrayList("","Name", "Role");

    @FXML
    public void fieldsClear(ActionEvent actionEvent) {

        userId.clear();
        firstName.clear();
        lastName.clear();
        password.clear();
        role.setValue("");

    }


    @FXML
    public void nameClear(ActionEvent actionEvent) {

        searchByName.clear();
    }

    @FXML
    public void idClear(ActionEvent actionEvent) {

        searchById.clear();
    }


    //  Search a user by name and role
    @FXML
    private void searchUsers(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        if (searchChoice.getValue().toString().equals("Role")) {
            try {

                ObservableList<User> userData = UserDAO.searchUsersBYRole(searchField.getText());
                populateUsers(userData);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting user information from DB" + e);
                alert.showAndWait();
                throw e;
            }

        } else {
            try {

                User user = UserDAO.searchUserByName(searchField.getText());
                populateAndShowUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Error occurred while getting user information from DB" + e);
                alert.showAndWait();
                throw e;
            }
        }

    }


    @FXML
    private void deleteRow(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        User selectedItem = (User) userTable.getSelectionModel().getSelectedItem();
        String id = ((User) userTable.getSelectionModel().getSelectedItem()).getUserId();


        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Delete Confirmation");
        alert1.setHeaderText("Please confirm the action");
        alert1.setContentText("Are you sure you want to delete the user " + id + " \nfrom the system? ");
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                userTable.getItems().remove(selectedItem);
                UserDAO.deleteUserWithId(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User delete");
                alert.setHeaderText("Success message");
                alert.setContentText("The user " + id + " was successfully deleted!!");
                alert.showAndWait();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while deleting user " + e);
                alert.showAndWait();
                throw e;
            }
        } else {

            alert1.close();
        }

    }

    //Search all users
    @FXML
    private void searchAllUsers(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {

            ObservableList<User> userData = UserDAO.searchAllUsers();
            populateUsers(userData);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting user information from DB" + e);
            alert.showAndWait();
            throw e;
        }
    }


    @FXML
    private void initialize() throws SQLException {


        role.setItems(roleList);
        searchChoice.setItems(criteriaList);
        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

        try {

            ObservableList<User> userData = UserDAO.searchAllUsers();
            populateUsers(userData);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("Error occurred while getting user information from DB" + e);
            alert.showAndWait();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void populateUsers(ObservableList<User> userData) throws ClassNotFoundException {
        userTable.setItems(userData);
    }


    @FXML
    private void populateAndShowUser(User user) throws ClassNotFoundException {
        if (user != null) {
            populateUser(user);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failure message");
            alert.setContentText("This user does not exist!");
            alert.showAndWait();
        }
    }


    @FXML
    private void populateUser(User user) throws ClassNotFoundException {

        ObservableList<User> userData = FXCollections.observableArrayList();
        userData.add(user);
        userTable.setItems(userData);
    }


    @FXML
    private void addUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {

//        MessageDigest md = MessageDigest.getInstance("MD5");
//        byte[] messageDigest = md.digest( password.getText().getBytes());
//        BigInteger number = new BigInteger(1,messageDigest);
//        String hashtext = number.toString(16);

        try {
            UserDAO.addUser(userId.getText(), firstName.getText(), lastName.getText(), role.getValue().toString(), password.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add user");
            alert.setHeaderText("Success message");
            alert.setContentText("The user " + userId.getText() + " was successfully added!!");
            alert.showAndWait();
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

    @FXML
    private void backAction2(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == backBtn2) {
            //get reference to the button's stage
            stage = (Stage) backBtn2.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
            Scene scene = new Scene(root, 700, 600);
            stage.setScene(scene);
            stage.show();
        }

    }

}

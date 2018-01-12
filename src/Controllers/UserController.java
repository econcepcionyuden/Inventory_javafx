package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.User;
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
    private TextField newName;
    @FXML
    private TextField newUserId;
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
    private ChoiceBox<String> newRole;
    @FXML
    private TableView userTable;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> contactColumn;
    @FXML
    private TableColumn<User, String> addressColumn;
    @FXML
    private Button backBtn2;
    @FXML
    private Button reset;
    @FXML
    private Button addUser;

    private double xOffset = 0;
    private double yOffset = 0;

    ObservableList<String> criteriaList = FXCollections.observableArrayList("", "Name", "Role");
    ObservableList<String> newRoleList = FXCollections.observableArrayList("", "Admin", "Operator");

    public static String editablePassword = null;
    public static String editableFirstName = null;
    public static String editableLastName = null;
    public static String editableRole = null;
    public static String editableContactNo = null;
    public static String editableAddress = null;
    public static String editableUserId = null;



    @FXML
    public void fieldsClear(ActionEvent actionEvent) {

        userId.clear();
        firstName.clear();
        lastName.clear();
        password.clear();
        role.setValue("");
    }

    @FXML
    public void fieldsClear2(ActionEvent actionEvent) {

        newName.clear();
        newUserId.clear();
        newRole.setValue("");
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
    private void searchUsers() throws ClassNotFoundException, SQLException {

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
    private void editAction() throws SQLException, ClassNotFoundException, IOException {

        User user = (User) userTable.getSelectionModel().getSelectedItem();

        editableUserId = user.getUserId();
        editableFirstName = user.getFirstName();
        editableLastName = user.getLastName();
        editableContactNo = user.getContactNo();
        editableRole = user.getRole();
        editableAddress = user.getAddress();
        editablePassword = user.getPassword();


        FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/editUser.fxml")));
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
    private void deleteRow() throws ClassNotFoundException, SQLException {

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
    private void searchAllUsers() throws SQLException, ClassNotFoundException {
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

        searchChoice.setItems(criteriaList);
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        contactColumn.setCellValueFactory(cellData -> cellData.getValue().contactNoProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

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
    public void addAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/createUser.fxml")));
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
        stage.setTitle("Add user");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();
    }


    @FXML
    private void backAction2() throws IOException {
        Stage stage;
        Parent root;

            //get reference to the button's stage
            stage = (Stage) userTable.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
            Scene scene = new Scene(root,1170, 600);
            stage.setScene(scene);
            stage.show();

    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) userTable.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) userTable.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    public void addUser() throws Exception {

        FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/createUser.fxml")));
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
        stage.setTitle("Add User");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();

    }


}

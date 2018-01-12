package Controllers;


import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import util.UserDAO;

import java.security.MessageDigest;


public class LoginController {
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label errorLabel;

    public void initialize() {
    }


    public static String sessionRole = null;
    public static String userId = null;


    @FXML
    public void loginAction(ActionEvent event) throws Exception {
        errorLabel.setText("");
        authenticate(event);
    }

    private String passSession(String role){
        sessionRole = role;
       return sessionRole;
    }


    private void authenticate(Event event) throws Exception {

        validateInput();

        if (validateInput()) {
            String username = userName.getText().trim();
            User user = UserDAO.searchUserByName(username);
            //passSession(user.getRole());


            if (user == null) {
                userName.setText("");
                password.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Invalid credentials!!");
                alert.showAndWait();

            } else {

                String userPassword = user.getPassword();
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getText().getBytes());
                byte byteData[] = md.digest();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

                }

                String role = user.getRole();
                String id = user.getUserId();
                sessionRole = role;
                userId = id;

                if (sb.toString().equals(userPassword)) {

                    if (role.equals("Admin")) {
                        windows("../views/admin.fxml", "Admin Dashboard");
                    } else {
                        windows("../views/POS.fxml", "Point of Sales");
                    }
                } else {
                    userName.setText("");
                    password.setText("");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Error");
                    alert.setHeaderText("Failure message");
                    alert.setContentText("Invalid password!!");
                    alert.showAndWait();

                }

            }

//            if (model.checkUser(username)) {
//
//                if (model.checkPassword(username, password)) {
//
//                    ((Node) (event.getSource())).getScene().getWindow().hide();
//
//                    String type = model.getEmployeeType(username);
//
//                    switch (type) {
//                        case "admin":
//                            windows("../views/login.fxml", "Admin Dashboard");
//                            break;
//
//                        case "operator":
//                            windows("../views/POS.fxml", "Point of Sales");
//                            break;
//                    }
//                } else {
//                    passwordField.setText("");
//                    errorLabel.setText("Wrong Password!");
//                }
//            } else {
//                resetFields();
//                errorLabel.setText("User doesn't exist!");
//            }
        }
    }

    private void windows(String path, String title) throws Exception {
        Stage stage;
        Parent root;
        root = FXMLLoader.load(getClass().getResource(path));
        stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private boolean validateInput() {

        String errorMessage = "";


        if(!(userName.getText().matches("^[A-Za-z0-9\\s\\-_]+$")&&password.getText().matches("^[A-Za-z0-9\\s\\-_]+$"))){
            errorMessage += "Inputs are not in the correct format!!\n";
        }

        if (userName.getText().length() == 0 || password.getText().length() == 0) {
            errorMessage += "Please enter both credentials!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            errorLabel.setText(errorMessage);
            return false;
        }
    }

//
//    public void initManager(final LoginManager loginManager) {
//        loginButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                if(user.getText().equals("admin")){
//
//
//                    String sessionID = authorizeAdmin();
//                    if (sessionID != null) {
//                        loginManager.adminAuthenticated(sessionID);
//
//
//                }else if(user.getText().equals("operator")){
//
//                        sessionID = authorizeOperator();
//                        if (sessionID != null) {
//                            loginManager.operatorAuthenticated(sessionID);
////                            Alert alert = new Alert(Alert.AlertType.ERROR);
////                            alert.setTitle("Login Error");
////                            alert.setHeaderText("Failure message");
////                            alert.setContentText("Invalid Username or Password");
////                            alert.showAndWait();
//                        }
//
//                    }
//
//                }
//            }
//        });
//    }


    //    private String authorizeAdmin() {
//        return
//                "admin".equals(user.getText()) && "admin".equals(password.getText())
//                        ? generateAdminSessionID()
//                        : null;
//    }
//
//    private String authorizeOperator() {
//        return
//                "operator".equals(user.getText()) && "operator".equals(password.getText())
//                        ? generateOperatorSessionID()
//                        : null;
//    }
    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void maximizeAction() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setMaximized(true);
    }


}
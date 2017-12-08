package Controllers;


import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controls the login screen
 */
public class LoginController {
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    public void initialize() {
    }

    public void initManager(final LoginManager loginManager) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(user.getText().equals("admin")){


                    String sessionID = authorizeAdmin();
                    if (sessionID != null) {
                        loginManager.adminAuthenticated(sessionID);


                }else {

                        sessionID = authorizeOperator();
                        if (sessionID != null) {
                            loginManager.operatorAuthenticated(sessionID);
//                            Alert alert = new Alert(Alert.AlertType.ERROR);
//                            alert.setTitle("Login Error");
//                            alert.setHeaderText("Failure message");
//                            alert.setContentText("Invalid Username or Password");
//                            alert.showAndWait();
                        }

                    }

                }
            }
        });
    }


    private String authorizeAdmin() {
        return
                "admin".equals(user.getText()) && "admin".equals(password.getText())
                        ? generateAdminSessionID()
                        : null;
    }

    private String authorizeOperator() {
        return
                "operator".equals(user.getText()) && "operator".equals(password.getText())
                        ? generateOperatorSessionID()
                        : null;
    }



    private static int sessionID = 0;

    private String  generateAdminSessionID() {
        sessionID++;
        return "Welcome Admin" + sessionID;
    }

    private String generateOperatorSessionID() {
        sessionID++;
        return "Welcome Operator" + sessionID;
    }
}
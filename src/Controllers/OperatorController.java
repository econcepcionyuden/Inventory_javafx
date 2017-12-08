package Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by jaliya on 11/17/17.
 */
public class OperatorController {

    @FXML
    private Button purchasesBtn1;
    @FXML
    private Button salesBtn1;
    @FXML
    private Button productsBtn1;

    @FXML
    private Button dashboardBtn1;

    @FXML private Button logoutButton1;

    @FXML
    private void handleOperatorNavigation(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if(event.getSource()==purchasesBtn1){
            stage=(Stage) purchasesBtn1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../Controllers.views/Purchases.fxml"));
        }
        else if(event.getSource()==salesBtn1){
            stage=(Stage) salesBtn1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../Controllers.views/Sales.fxml"));
        }
        else if(event.getSource()==productsBtn1){
            stage=(Stage) productsBtn1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../Controllers.views/OperatorProducts.fxml"));
        } else{
            stage=(Stage) dashboardBtn1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../Controllers.views/operator.fxml"));
        }

        Scene scene = new Scene(root,800,500);
        stage.setScene(scene);
        stage.show();
    }

    public void initSessionID(final LoginManager loginManager, String sessionID) {
        logoutButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                loginManager.logout();
            }
        });
    }


}

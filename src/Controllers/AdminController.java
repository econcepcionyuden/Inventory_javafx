package Controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.ProductDAO;
import util.PurchaseDAO;
import util.SaleDAO;
import util.UserDAO;

import java.io.IOException;
import java.sql.SQLException;


public class AdminController {


    @FXML
    private Button purchasesBtn;
    @FXML
    private Button usersBtn;

    @FXML
    private Button reportsBtn;
    @FXML
    private Button salesBtn;
    @FXML
    private Button productsBtn;
    @FXML
    private TextField userCount;
    @FXML
    private TextField saleCount;
    @FXML
    private TextField purchaseCount;
    @FXML
    private TextField productCount;
    @FXML
    private Button vendorsBtn;
    @FXML
    private Button clientsBtn;
    @FXML
    private Button logoutButton;

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == purchasesBtn) {

            stage = (Stage) purchasesBtn.getScene().getWindow();

            root = FXMLLoader.load(getClass().getResource("../views/Purchases.fxml"));
        } else if (event.getSource() == usersBtn) {
            stage = (Stage) usersBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/Users.fxml"));
        } else if (event.getSource() == reportsBtn) {
            stage = (Stage) reportsBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/Reports.fxml"));
        } else if (event.getSource() == salesBtn) {
            stage = (Stage) salesBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/Sales.fxml"));
        } else if (event.getSource() == productsBtn) {
            stage = (Stage) productsBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/AdminProducts.fxml"));
        } else if (event.getSource() == logoutButton) {
            stage = (Stage) logoutButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
            Scene scene = new Scene(root, 625, 400);
            stage.setScene(scene);
            stage.show();
        } else if (event.getSource() == vendorsBtn) {
            stage = (Stage) vendorsBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/vendors.fxml"));
        } else {
            stage = (Stage) clientsBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/clients.fxml"));

        }

        Scene scene = new Scene(root, 750, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void initSessionID(final LoginManager loginManager, String sessionID) {
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginManager.logout();
            }
        });
    }


    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {

        //Get all overall information
        int productNo = ProductDAO.productCount();
        String productNoString = Integer.toString(productNo);
        productCount.setText(productNoString);

        int userNo = UserDAO.userCount();
        String userNoString = Integer.toString(userNo);
        userCount.setText(userNoString);

        int saleNo = SaleDAO.saleCount();
        String saleNoString = Integer.toString(saleNo);
        saleCount.setText(saleNoString);

        int purchaseNo = PurchaseDAO.purchaseCount();
        String purchaseNoString = Integer.toString(purchaseNo);
        purchaseCount.setText(purchaseNoString);

    }


}

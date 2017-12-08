package Controllers;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Product;
import models.Purchase;
import models.Sale;
import org.controlsfx.control.Notifications;
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
    private TextField totalExpenseField;
    @FXML
    private TextField totalIncomeField;
    @FXML
    private TextField grossProfitField;
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
    private void initialize() throws SQLException, ClassNotFoundException, IOException {

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

        ObservableList<Purchase> purchaseData = PurchaseDAO.totalPurchaseAmounts();

        int[] quantities = new int[purchaseData.size()];
        int[] amounts = new int[purchaseData.size()];
        int totalExpense = 0;
        for (int i = 0; i < purchaseData.size(); i++) {
            quantities[i] = Integer.parseInt(purchaseData.get(i).getQuantity());
            amounts[i] = Integer.parseInt(purchaseData.get(i).getAmount());

            totalExpense += quantities[i] * amounts[i];

        }
        String totalExpenseString = Integer.toString(totalExpense);
        totalExpenseField.setText(totalExpenseString);


        ObservableList<Sale> saleData = SaleDAO.totalSaleAmounts();

        int[] saleQuantities = new int[saleData.size()];
        int[] saleAmounts = new int[saleData.size()];
        int totalIncome = 0;
        for (int i = 0; i < saleData.size(); i++) {
            saleQuantities[i] = Integer.parseInt(saleData.get(i).getQuantity());
            saleAmounts[i] = Integer.parseInt(saleData.get(i).getAmount());
            totalIncome += saleQuantities[i] * saleAmounts[i];
        }

        String totalIncomeString = Integer.toString(totalIncome);
        totalIncomeField.setText(totalIncomeString);

        int grossProfit = totalIncome - totalExpense;
        String stringGrossProfit = Integer.toString(grossProfit);
        grossProfitField.setText(stringGrossProfit);



        //  String totalPurchaseQuntities = PurchaseDAO.totalPurchaseQuntities();


//        ObservableList<Product> productData = ProductDAO.productTypeCount();
//
//        String[] title = new String[productData.size()];
//        int[] quantities = new int[productData.size()];
//        int[] reOrderPoints = new int[productData.size()];
//        int[] surpluses = new int[productData.size()];
//        for (int i = 0; i < productData.size(); i++) {
//            quantities[i] = Integer.parseInt(productData.get(i).getQuantity());
//            reOrderPoints[i] = Integer.parseInt(productData.get(i).getReOrderPoint());
//            surpluses[i] = Integer.parseInt(productData.get(i).getSurplusPoint());
//            title[i] = productData.get(i).getTitle();
//
//            if (quantities[i] >= surpluses[i]) {
//
//                Notifications.create()
//                        .title("Attention")
//                        .text("There is a surplus in " + title[i] + "")
//                        .hideAfter(Duration.seconds(5))
//                        .darkStyle()
//                        .showWarning();
//
//            } else if (quantities[i] <= reOrderPoints[i]) {
//
//                Notifications.create()
//                        .title("Attention")
//                        .text("There is a need for reorder in " + title[i] + "")
//                        .hideAfter(Duration.seconds(5))
//                        .darkStyle()
//                        .showWarning();
//            }
//
//        }

    }

}

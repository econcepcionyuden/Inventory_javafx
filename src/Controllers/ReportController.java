package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Purchase;
import models.Sale;
import util.ProductDAO;
import util.PurchaseDAO;
import util.ReportDAO;
import util.SaleDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jaliya on 12/5/17.
 */
public class ReportController {

    @FXML
    private Button backBtn2;


//    @FXML
//    private static int getTotalExpenseByMonth() throws SQLException, ClassNotFoundException {
//        try {
//
//           int total = SaleDAO.getClients();
//
//            ObservableList<String> clientList = FXCollections.observableArrayList(clients);
//            return clientList;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Failure message");
//            alert.setContentText("Error occurred while getting product information from DB" + e);
//            alert.showAndWait();
//            throw e;
//        }
//    }

    @FXML
    private void updateMonthlyRecord(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String today = dtf.format(now);

        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String date = year + "/" + month + "/" + "01";

        Calendar cal = Calendar.getInstance();
        int noOfLastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        String lastDayOfTheMonth = year + "-" + month + "-" + noOfLastDay;

        if (today.equals(lastDayOfTheMonth)) {

            int saleNo = SaleDAO.saleCount(date);
            String saleNoString = Integer.toString(saleNo);

            int purchaseNo = PurchaseDAO.purchaseCount(date);
            String purchaseNoString = Integer.toString(purchaseNo);

            int goodProductNo = ProductDAO.productCount();
            String productNoString = Integer.toString(goodProductNo);
            int expiredProductNo = ProductDAO.expiredProductCount();
            String expiredProductNoString = Integer.toString(expiredProductNo);
            int damagedProductNo = ProductDAO.damagedProductCount();
            String damagedProductNoString = Integer.toString(damagedProductNo);

            String inventoryStatusRatio = productNoString + ":" + expiredProductNoString + ":" + damagedProductNoString;

            ObservableList<Purchase> purchaseData = PurchaseDAO.totalPurchaseAmounts(date);

            int[] quantities = new int[purchaseData.size()];
            int[] amounts = new int[purchaseData.size()];
            int totalExpense = 0;
            for (int i = 0; i < purchaseData.size(); i++) {
                quantities[i] = Integer.parseInt(purchaseData.get(i).getQuantity());
                amounts[i] = Integer.parseInt(purchaseData.get(i).getAmount());

                totalExpense += quantities[i] * amounts[i];

            }
            String totalExpenseString = Integer.toString(totalExpense);


            ObservableList<Sale> saleData = SaleDAO.totalSaleAmounts(date);

            int[] saleQuantities = new int[saleData.size()];
            int[] saleAmounts = new int[saleData.size()];
            int totalIncome = 0;
            for (int i = 0; i < saleData.size(); i++) {
                saleQuantities[i] = Integer.parseInt(saleData.get(i).getQuantity());
                saleAmounts[i] = Integer.parseInt(saleData.get(i).getAmount());
                totalIncome += saleQuantities[i] * saleAmounts[i];
            }

            String totalIncomeString = Integer.toString(totalIncome);


            int grossProfit = totalIncome - totalExpense;
            String stringGrossProfit = Integer.toString(grossProfit);

            float assetTurnoverRatio = ((float) saleNo) / ((float) goodProductNo + expiredProductNo + damagedProductNo);
            String stringAssetTurnoverRatio = String.valueOf(assetTurnoverRatio);


            try {
                ReportDAO.insertMonthlyRecord(year, month, totalExpenseString, totalIncomeString, stringGrossProfit, stringAssetTurnoverRatio, inventoryStatusRatio,productNoString,expiredProductNoString,damagedProductNoString);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Monthly Record");
                alert.setHeaderText("Success message");
                alert.setContentText("The record of " + month + " month was successfully added!!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while adding monthly record" + e);
                alert.showAndWait();
                throw e;
            }


        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Monthly Record");
            alert.setHeaderText("Error message");
            alert.setContentText("Couldn't add the record since today is not the\nlast day of the current month");
            alert.showAndWait();

        }
    }


    @FXML
    private void initialize() throws SQLException, ClassNotFoundException, IOException {


    }


    @FXML
    private void backAction2(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == backBtn2) {
            stage = (Stage) backBtn2.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
            Scene scene = new Scene(root, 950, 550);
            stage.setScene(scene);
            stage.show();
        }

    }


    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) backBtn2.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) backBtn2.getScene().getWindow();
        stage.setIconified(true);
    }
}

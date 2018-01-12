package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import models.MonthlyRecord;
import models.Purchase;
import models.Sale;
import models.SalesRecord;
import util.ProductDAO;
import util.PurchaseDAO;
import util.ReportDAO;
import util.SaleDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Created by jaliya on 12/5/17.
 */
public class ReportController {

    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private ComboBox type;


    ObservableList<String> reportType = FXCollections.observableArrayList("", "Buying", "Selling", "Monthly summary");


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
    private void generateReport(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

        if (type.getValue().toString().equals("Buying")) {

            try {
                ObservableList<Purchase> purchaseData = ReportDAO.getMonthlyPurchase(fromDate.getValue().toString(), toDate.getValue().toString());
                GeneratePurchaseReport gp = new GeneratePurchaseReport(fromDate.getValue().toString(), toDate.getValue().toString(), purchaseData);
                gp.generatePurchaseReport();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Purchase Report");
                alert.setHeaderText("Success message");
                alert.setContentText("The purchase report from " + fromDate.getValue().toString() + " to " + toDate.getValue().toString() + " was successfully created!!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while creating the purchase report");
                alert.showAndWait();
                throw e;
            }


        } else if (type.getValue().toString().equals("Selling")) {

            try {
                ObservableList<SalesRecord> salesData = ReportDAO.getMonthlySales(fromDate.getValue().toString(), toDate.getValue().toString());
                GenerateSalesReport gp = new GenerateSalesReport(fromDate.getValue().toString(), toDate.getValue().toString(), salesData);
                gp.generateSalesReport();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sales Report");
                alert.setHeaderText("Success message");
                alert.setContentText("The sales report from " + fromDate.getValue().toString() + " to " + toDate.getValue().toString() + " was successfully created!!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while creating the sales report");
                alert.showAndWait();
                throw e;
            }

        } else {

            String fromDateV = fromDate.getValue().toString();
            String[] monthV = fromDateV.split("-", 3);
            String fromMonth = monthV[1];
            String fromYear = monthV[0];

            String toDateV = toDate.getValue().toString();
            String[] monthT = toDateV.split("-", 3);
            String toMonth = monthT[1];
            String toYear = monthT[0];

            try {
                ObservableList<MonthlyRecord> recordsData = ReportDAO.monthlyRecordsBYRange(fromDateV,toDateV);
                GenerateMonthlyReport mr = new GenerateMonthlyReport(fromMonth, toMonth, recordsData,fromYear,toYear);
                mr.generateMonthlyReport();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Monthly Report");
                alert.setHeaderText("Success message");
                alert.setContentText("The monthly report from was successfully created!!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failure message");
                alert.setContentText("Problem occurred while creating the monthly report");
                alert.showAndWait();
                throw e;
            }
        }

    }


    @FXML
    private void updateMonthlyRecord() throws SQLException, ClassNotFoundException {

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
                ReportDAO.insertMonthlyRecord(year, month, totalExpenseString, totalIncomeString, stringGrossProfit, stringAssetTurnoverRatio, inventoryStatusRatio, productNoString, expiredProductNoString, damagedProductNoString);
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

        type.setItems(reportType);

    }


    @FXML
    private void backAction2() throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) toDate.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../views/admin.fxml"));
        Scene scene = new Scene(root, 1170, 600);
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) toDate.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) toDate.getScene().getWindow();
        stage.setIconified(true);
    }
}

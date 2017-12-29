package Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.MonthlyRecord;
import models.Purchase;
import models.Sale;
import util.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;


public class AdminController {


    @FXML
    private Button purchasesBtn;
    @FXML
    private Button usersBtn;
    @FXML
    private PieChart inventoryPieChart;
    @FXML
    private AreaChart profitAndTurnover;
    @FXML
    private Button reportsBtn;
    @FXML
    private Button salesBtn;
    @FXML
    private Button productsBtn;
    @FXML
    private TextField totalExpenseField;
    @FXML
    private TextField totalIncomeField;
    @FXML
    private TextField assetTurnover;
    @FXML
    private TextField damagedProductCount;
    @FXML
    private TextField previousProfit;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis time;
    @FXML
    private NumberAxis profit;
    @FXML
    private CategoryAxis month;
    @FXML
    private NumberAxis inventory;
    @FXML
    private LineChart inventoryLineChart;
    @FXML
    private LineChart profitLineChart;
    @FXML
    private TextField profitProgress;
    @FXML
    private TextField grossProfitField;
    @FXML
    private MenuItem logoutItem;
    @FXML
    private MenuItem posItem;
    @FXML
    private TextField saleCount;
    @FXML
    private TextField purchaseCount;
    @FXML
    private TextField expiredProductCount;
    @FXML
    private TextField goodProductCount;
    @FXML
    private Button vendorsBtn;
    @FXML
    private Button clientsBtn;



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
            root = FXMLLoader.load(getClass().getResource("../views/reportMaking.fxml"));
        } else if (event.getSource() == salesBtn) {
            stage = (Stage) salesBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/Sales.fxml"));
        } else if (event.getSource() == productsBtn) {
            stage = (Stage) productsBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/Products.fxml"));
        }else if (event.getSource() == posItem) {
            stage = (Stage) salesBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/POS.fxml"));
            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.show();
        } else if (event.getSource() == logoutItem) {
            stage = (Stage) salesBtn.getScene().getWindow();
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
        salesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginManager.logout();
            }
        });
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) salesBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) salesBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException, IOException {


        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String previousMonth = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String date = year + "-" + month + "-" + "01";

        String previousDate = year + "-" + previousMonth + "-" + "01";

        //Get all overall information
        int goodProductNo = ProductDAO.productCount();
        String productNoString = Integer.toString(goodProductNo);
        goodProductCount.setText(productNoString);

        int expiredProductNo = ProductDAO.expiredProductCount();
        String expiredProductNoString = Integer.toString(expiredProductNo);
        expiredProductCount.setText(expiredProductNoString);


        int damagedProductNo = ProductDAO.damagedProductCount();
        String damagedProductNoString = Integer.toString(damagedProductNo);
        damagedProductCount.setText(damagedProductNoString);


        int saleNo = SaleDAO.saleCount(date);
        String saleNoString = Integer.toString(saleNo);
        saleCount.setText(saleNoString);

        int purchaseNo = PurchaseDAO.purchaseCount(date);
        String purchaseNoString = Integer.toString(purchaseNo);
        purchaseCount.setText(purchaseNoString);

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
        totalExpenseField.setText(totalExpenseString);


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
        totalIncomeField.setText(totalIncomeString);

        int grossProfit = totalIncome - totalExpense;
        String stringGrossProfit = Integer.toString(grossProfit);
        grossProfitField.setText(stringGrossProfit);


        ObservableList<Purchase> previousPurchaseData = PurchaseDAO.previousTotalPurchaseAmounts(previousDate, date);

        int[] previousQuantities = new int[previousPurchaseData.size()];
        int[] previousAmounts = new int[previousPurchaseData.size()];
        int previousTotalExpense = 0;
        for (int i = 0; i < previousPurchaseData.size(); i++) {
            previousQuantities[i] = Integer.parseInt(previousPurchaseData.get(i).getQuantity());
            previousAmounts[i] = Integer.parseInt(previousPurchaseData.get(i).getAmount());

            previousTotalExpense += previousQuantities[i] * previousAmounts[i];

        }

        ObservableList<Sale> previousSaleData = SaleDAO.previousTotalSaleAmounts(previousDate, date);

        int[] previousSaleQuantities = new int[previousSaleData.size()];
        int[] previousSaleAmounts = new int[previousSaleData.size()];
        int previousTotalIncome = 0;
        for (int i = 0; i < previousSaleData.size(); i++) {
            previousSaleQuantities[i] = Integer.parseInt(previousSaleData.get(i).getQuantity());
            previousSaleAmounts[i] = Integer.parseInt(previousSaleData.get(i).getAmount());
            previousTotalIncome += previousSaleQuantities[i] * previousSaleAmounts[i];
        }

        int previousGrossProfit = previousTotalIncome - previousTotalExpense;
        String stringPreviousGrossProfit = Integer.toString(previousGrossProfit);
        previousProfit.setText(stringPreviousGrossProfit);

        float profitPercentage = ((float) grossProfit - (float) previousGrossProfit) / (float) previousGrossProfit;
        profitPercentage = profitPercentage * 100;
        String stringProfitPercentage = String.valueOf(Math.round(profitPercentage));
        profitProgress.setText(stringProfitPercentage + "%");

        float assetTurnoverRatio = ((float) saleNo) / ((float) goodProductNo + expiredProductNo + damagedProductNo);
        String stringAssetTurnoverRatio = String.valueOf(assetTurnoverRatio);
        assetTurnover.setText(stringAssetTurnoverRatio);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("GOOD", goodProductNo),
                new PieChart.Data("EXPIRED", expiredProductNo),
                new PieChart.Data("DAMAGED", damagedProductNo));

        inventoryPieChart.setData(pieChartData);
        inventoryPieChart.setLabelLineLength(5);


        //record analysis

        ObservableList<MonthlyRecord> monthlyRecordsData = ReportDAO.monthlyRecords();
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        float[] turnoverValues = new float[monthlyRecordsData.size()];
        int[] profitValues = new int[monthlyRecordsData.size()];
        int[] damagedProductCounts = new int[monthlyRecordsData.size()];
        int[] expiredProductCounts = new int[monthlyRecordsData.size()];
        int[] goodProductCounts = new int[monthlyRecordsData.size()];
        int[] totalProductCounts = new int[monthlyRecordsData.size()];


        String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December" };

        for (int i = 0; i < monthlyRecordsData.size(); i++) {
            turnoverValues[i] = Float.parseFloat(monthlyRecordsData.get(i).getInventoryTurnover());
            profitValues[i] = Integer.parseInt(monthlyRecordsData.get(i).getProfit());
            damagedProductCounts[i] = Integer.parseInt(monthlyRecordsData.get(i).getDamagedProductCount());
            expiredProductCounts[i] = Integer.parseInt(monthlyRecordsData.get(i).getExpiredProductCount());
            goodProductCounts[i] = Integer.parseInt(monthlyRecordsData.get(i).getGoodProductCount());

            totalProductCounts[i] = damagedProductCounts[i]+expiredProductCounts[i]+goodProductCounts[i];




            series1.getData().add(new XYChart.Data(monthName[(i)], turnoverValues[i]));
            series2.getData().add(new XYChart.Data(monthName[(i)], profitValues[i]));
            series3.getData().add(new XYChart.Data(monthName[(i)], damagedProductCounts[i]));
            series4.getData().add(new XYChart.Data(monthName[(i)], totalProductCounts[i]));

        }

        inventoryLineChart.getData().addAll(series1);
        y.setLabel("Turnover\nvalues");
        x.setLabel("Months");
        profit.setLabel("Profit");
        time.setLabel("Months");
        inventory.setLabel("Number\nof\ninventory");
        time.setLabel("Months");
        profitLineChart.getData().addAll(series2);

        profitAndTurnover.getData().add(series3);
        profitAndTurnover.getData().add(series4);



        // inventoryAreaChart.getData().add(series);


    }

}

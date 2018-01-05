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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    private double xOffset = 0;
    private double yOffset = 0;

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
            Scene scene = new Scene(root, 1045, 565);
            stage.setScene(scene);
            stage.show();
        }else if (event.getSource() == posItem) {
            stage = (Stage) salesBtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../views/POS.fxml"));
            Scene scene = new Scene(root,1070, 610);
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

        Scene scene = new Scene(root, 775, 500);
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
    public void moreStat() throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/adminStat.fxml")));
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
        stage.setTitle("Statistics");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();

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
        int expiredProductNo = ProductDAO.expiredProductCount();
        int damagedProductNo = ProductDAO.damagedProductCount();
        int saleNo = SaleDAO.saleCount(date);
        int purchaseNo = PurchaseDAO.purchaseCount(date);


        ObservableList<Purchase> purchaseData = PurchaseDAO.totalPurchaseAmounts(date);

        int[] quantities = new int[purchaseData.size()];
        int[] amounts = new int[purchaseData.size()];
        int totalExpense = 0;
        for (int i = 0; i < purchaseData.size(); i++) {
            quantities[i] = Integer.parseInt(purchaseData.get(i).getQuantity());
            amounts[i] = Integer.parseInt(purchaseData.get(i).getAmount());

            totalExpense += quantities[i] * amounts[i];

        }



        ObservableList<Sale> saleData = SaleDAO.totalSaleAmounts(date);

        int[] saleQuantities = new int[saleData.size()];
        int[] saleAmounts = new int[saleData.size()];
        int totalIncome = 0;
        for (int i = 0; i < saleData.size(); i++) {
            saleQuantities[i] = Integer.parseInt(saleData.get(i).getQuantity());
            saleAmounts[i] = Integer.parseInt(saleData.get(i).getAmount());
            totalIncome += saleQuantities[i] * saleAmounts[i];
        }

        int grossProfit = totalIncome - totalExpense;



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
        float profitPercentage = ((float) grossProfit - (float) previousGrossProfit) / (float) previousGrossProfit;
        profitPercentage = profitPercentage * 100;
        float assetTurnoverRatio = ((float) saleNo) / ((float) goodProductNo + expiredProductNo + damagedProductNo);


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
        int[] monthNo = new int[monthlyRecordsData.size()];


        String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December" };

        for (int i = 0; i < monthlyRecordsData.size(); i++) {
            turnoverValues[i] = Float.parseFloat(monthlyRecordsData.get(i).getInventoryTurnover());
            profitValues[i] = Integer.parseInt(monthlyRecordsData.get(i).getProfit());
            damagedProductCounts[i] = Integer.parseInt(monthlyRecordsData.get(i).getDamagedProductCount());
            expiredProductCounts[i] = Integer.parseInt(monthlyRecordsData.get(i).getExpiredProductCount());
            goodProductCounts[i] = Integer.parseInt(monthlyRecordsData.get(i).getGoodProductCount());
            monthNo[i] = Integer.parseInt(monthlyRecordsData.get(i).getMonth());

            totalProductCounts[i] = damagedProductCounts[i]+expiredProductCounts[i]+goodProductCounts[i];

            series1.getData().add(new XYChart.Data(monthName[monthNo[i]-1], turnoverValues[i]));
            series2.getData().add(new XYChart.Data(monthName[monthNo[i]-1], profitValues[i]));
            series3.getData().add(new XYChart.Data(monthName[monthNo[i]-1], damagedProductCounts[i]));
            series4.getData().add(new XYChart.Data(monthName[monthNo[i]-1], totalProductCounts[i]));

        }

        inventoryLineChart.getData().addAll(series1);
        inventoryLineChart.setLegendVisible(false);
        y.setLabel("Turnover\nvalues");
        profit.setLabel("Profit");
        inventory.setLabel("Number\nof\ninventory");
        profitLineChart.getData().addAll(series2);
        profitLineChart.setLegendVisible(false);

        profitAndTurnover.getData().add(series3);
        profitAndTurnover.getData().add(series4);
        profitAndTurnover.setLegendVisible(false);



        // inventoryAreaChart.getData().add(series);


    }

}

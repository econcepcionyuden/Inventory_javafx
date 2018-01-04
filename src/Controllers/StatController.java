package Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Purchase;
import models.Sale;
import util.ProductDAO;
import util.PurchaseDAO;
import util.SaleDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by jaliya on 1/2/18.
 */
public class StatController {





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
    private TextField profitProgress;
    @FXML
    private TextField grossProfitField;
    @FXML
    private TextField saleCount;
    @FXML
    private TextField purchaseCount;
    @FXML
    private TextField expiredProductCount;
    @FXML
    private TextField goodProductCount;




    @FXML
    private void initialize() throws SQLException, ClassNotFoundException, IOException {

        int monthInt = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String place = null;
        if (monthInt % 10 < 10) {
            place = "0"+String.valueOf(monthInt);
        }
        String month = place;

        int previousMonthInt = Calendar.getInstance().get(Calendar.MONTH);
        String place2 = null;
        if (previousMonthInt % 10 < 10) {
            place2 = "0"+String.valueOf(previousMonthInt);
        }
        String previousMonth = place2;

        int yearInt = Calendar.getInstance().get(Calendar.YEAR);
        int yearIntP = Calendar.getInstance().get(Calendar.YEAR);

        if(month.equals("01")){

            yearIntP = yearIntP - 1;
            previousMonth = "12";

        }

        String year = String.valueOf(yearInt);
        String date = year + "-" + month + "-" + "01";
        String previousDate = yearIntP + "-" + previousMonth + "-" + "01";

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
        String turnOverRatio = String.valueOf(assetTurnoverRatio);
        assetTurnover.setText(turnOverRatio);



    }


    @FXML
    private void minimizeAction() {
        Stage stage = (Stage) saleCount.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) saleCount.getScene().getWindow();
        stage.close();
    }

}

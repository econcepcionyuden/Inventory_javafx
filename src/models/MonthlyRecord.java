package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by jaliya on 12/19/17.
 */
public class MonthlyRecord {


    private IntegerProperty recordId;
    private StringProperty year;
    private StringProperty month;
    private StringProperty expense;
    private StringProperty income;
    private StringProperty profit;
    private StringProperty inventoryTurnover;
    private StringProperty inventoryStatusRatio;
    private StringProperty goodProductCount;
    private StringProperty expiredProductCount;
    private StringProperty damagedProductCount;


    //Constructor
    public MonthlyRecord() {
        this.recordId = new SimpleIntegerProperty();
        this.year = new SimpleStringProperty();
        this.month = new SimpleStringProperty();
        this.expense = new SimpleStringProperty();
        this.income = new SimpleStringProperty();
        this.profit = new SimpleStringProperty();
        this.inventoryTurnover = new SimpleStringProperty();
        this.inventoryStatusRatio = new SimpleStringProperty();
        this.goodProductCount = new SimpleStringProperty();
        this.expiredProductCount = new SimpleStringProperty();
        this.damagedProductCount = new SimpleStringProperty();
    }

    public String getGoodProductCount() {
        return goodProductCount.get();
    }

    public StringProperty goodProductCountProperty() {
        return goodProductCount;
    }

    public void setGoodProductCount(String goodProductCount) {
        this.goodProductCount.set(goodProductCount);
    }

    public String getExpiredProductCount() {
        return expiredProductCount.get();
    }

    public StringProperty expiredProductCountProperty() {
        return expiredProductCount;
    }

    public void setExpiredProductCount(String expiredProductCount) {
        this.expiredProductCount.set(expiredProductCount);
    }

    public String getDamagedProductCount() {
        return damagedProductCount.get();
    }

    public StringProperty damagedProductCountProperty() {
        return damagedProductCount;
    }

    public void setDamagedProductCount(String damagedProductCount) {
        this.damagedProductCount.set(damagedProductCount);
    }

    public int getRecordId() {
        return recordId.get();
    }

    public IntegerProperty recordIdProperty() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId.set(recordId);
    }

    public String getYear() {
        return year.get();
    }

    public StringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getMonth() {
        return month.get();
    }

    public StringProperty monthProperty() {
        return month;
    }

    public void setMonth(String month) {
        this.month.set(month);
    }

    public String getExpense() {
        return expense.get();
    }

    public StringProperty expenseProperty() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense.set(expense);
    }

    public String getIncome() {
        return income.get();
    }

    public StringProperty incomeProperty() {
        return income;
    }

    public void setIncome(String income) {
        this.income.set(income);
    }

    public String getProfit() {
        return profit.get();
    }

    public StringProperty profitProperty() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit.set(profit);
    }

    public String getInventoryTurnover() {
        return inventoryTurnover.get();
    }

    public StringProperty inventoryTurnoverProperty() {
        return inventoryTurnover;
    }

    public void setInventoryTurnover(String inventoryTurnover) {
        this.inventoryTurnover.set(inventoryTurnover);
    }

    public String getInventoryStatusRatio() {
        return inventoryStatusRatio.get();
    }

    public StringProperty inventoryStatusRatioProperty() {
        return inventoryStatusRatio;
    }

    public void setInventoryStatusRatio(String inventoryStatusRatio) {
        this.inventoryStatusRatio.set(inventoryStatusRatio);
    }
}

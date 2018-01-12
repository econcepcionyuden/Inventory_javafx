package models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by jaliya on 1/8/18.
 */
public class SalesRecord {

    private StringProperty transactionDate;
    private StringProperty productId;
    private StringProperty unitPrice;
    private StringProperty quantity;
    private SimpleDoubleProperty subTotal;
    private SimpleDoubleProperty vat;
    private SimpleDoubleProperty discount;
    private SimpleDoubleProperty payable;

    public SalesRecord() {

        this.transactionDate = new SimpleStringProperty();
        this.productId = new SimpleStringProperty();
        this.unitPrice = new SimpleStringProperty();
        this.quantity = new SimpleStringProperty();
        this.subTotal = new SimpleDoubleProperty();
        this.discount = new SimpleDoubleProperty();
        this.vat = new SimpleDoubleProperty();
        this.payable = new SimpleDoubleProperty();

    }

    public String getTransactionDate() {
        return transactionDate.get();
    }

    public StringProperty transactionDateProperty() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate.set(transactionDate);
    }

    public String getProductId() {
        return productId.get();
    }

    public StringProperty productIdProperty() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId.set(productId);
    }

    public String getUnitPrice() {
        return unitPrice.get();
    }

    public StringProperty unitPriceProperty() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public double getSubTotal() {
        return subTotal.get();
    }

    public SimpleDoubleProperty subTotalProperty() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal.set(subTotal);
    }

    public double getVat() {
        return vat.get();
    }

    public SimpleDoubleProperty vatProperty() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat.set(vat);
    }

    public double getDiscount() {
        return discount.get();
    }

    public SimpleDoubleProperty discountProperty() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount.set(discount);
    }

    public double getPayable() {
        return payable.get();
    }

    public SimpleDoubleProperty payableProperty() {
        return payable;
    }

    public void setPayable(double payable) {
        this.payable.set(payable);
    }
}

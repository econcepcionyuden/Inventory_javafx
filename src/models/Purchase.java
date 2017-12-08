package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by jaliya on 11/21/17.
 */
public class Purchase {


    private StringProperty purchaseId;
    private StringProperty transaction_date;
    private StringProperty amount;
    private StringProperty quantity;
    private StringProperty vendorId;
    private StringProperty productId;

    //Constructor
    public Purchase() {
        this.purchaseId = new SimpleStringProperty();
        this.transaction_date = new SimpleStringProperty();
        this.amount = new SimpleStringProperty();
        this.quantity = new SimpleStringProperty();
        this.vendorId = new SimpleStringProperty();
        this.productId = new SimpleStringProperty();

    }

    public String getPurchaseId() {
        return purchaseId.get();
    }

    public StringProperty purchaseIdProperty() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId.set(purchaseId);
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

    public String getVendorId() {
        return vendorId.get();
    }

    public StringProperty vendorIdProperty() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId.set(vendorId);
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

    public String getAmount() {
        return amount.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public String getTransaction_date() {
        return transaction_date.get();
    }

    public StringProperty transaction_dateProperty() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date.set(transaction_date);
    }
}

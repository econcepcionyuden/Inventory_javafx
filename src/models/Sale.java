package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by jaliya on 11/21/17.
 */
public class Sale {

    private StringProperty saleId;
    private StringProperty transaction_date;
    private StringProperty amount;
    private StringProperty quantity;
    private StringProperty clientId;
    private StringProperty productId;

    //Constructor
    public Sale() {
        this.saleId = new SimpleStringProperty();
        this.transaction_date = new SimpleStringProperty();
        this.amount = new SimpleStringProperty();
        this.quantity = new SimpleStringProperty();
        this.clientId = new SimpleStringProperty();
        this.productId = new SimpleStringProperty();

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

    public String getClientId() {
        return clientId.get();
    }

    public StringProperty clientIdProperty() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId.set(clientId);
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

    public String getSaleId() {
        return saleId.get();
    }

    public StringProperty saleIdProperty() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId.set(saleId);
    }
}

package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    //Declare Product Table Columns
    private StringProperty productId;
    private StringProperty title;
    private StringProperty type;
    private StringProperty description;
    private StringProperty unitPrice;
    private StringProperty quantity;
    private StringProperty saleId;
    private StringProperty purchaseId;
    private StringProperty productStatus;

    //Constructor
    public Product() {
        this.productId = new SimpleStringProperty();
        this.title = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.unitPrice = new SimpleStringProperty();
        this.quantity = new SimpleStringProperty();
        this.saleId = new SimpleStringProperty();
        this.purchaseId = new SimpleStringProperty();
        this.productStatus = new SimpleStringProperty();
    }

    public String getProductStatus() {
        return productStatus.get();
    }

    public StringProperty productStatusProperty() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus.set(productStatus);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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

    public String getPurchaseId() {
        return purchaseId.get();
    }

    public StringProperty purchaseIdProperty() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId.set(purchaseId);
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


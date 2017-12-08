package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by jaliya on 11/23/17.
 */
public class Vendor {


    private StringProperty vendorId;
    private StringProperty name;
    private StringProperty company;
    private StringProperty address;
    private StringProperty contact;

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getContact() {
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public Vendor() {
        this.vendorId = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.company = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.contact = new SimpleStringProperty();
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

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty() {
        return company;
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}

package com.hedspi.javalorant.order;

public class CustomerInfor {
    public static long countCustomer = 0;
    private long customerID;
    private String name;
    private String contactInfo;

    public CustomerInfor(String name, String contactInfo) {
        CustomerInfor.countCustomer++;
        this.customerID = CustomerInfor.countCustomer;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}

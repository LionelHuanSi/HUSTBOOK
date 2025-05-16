package com.hedspi.javalorant.expense;

import java.util.Date;

public class Expense {
    private String expenseID;
    private String expenseType;
    private double amount;
    private Date date;
    private String description;

    public Expense(String expenseID, String expenseType, double amount, Date date, String description) {
        this.expenseID = expenseID;
        this.expenseType = expenseType;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public String getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(String expenseID) {
        this.expenseID = expenseID;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

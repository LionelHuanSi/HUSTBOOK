package com.hedspi.javalorant.expense;

import java.util.Date;

public class Expense {
    public static long countExpense = 0;
    private long expenseID;
    private String expenseType;
    private double amount;
    private Date date;
    private String description;

    public Expense(String expenseType, double amount, Date date, String description) {
        Expense.countExpense++;
        this.expenseID = Expense.countExpense;
        this.expenseType = expenseType;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public long getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(long expenseID) {
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

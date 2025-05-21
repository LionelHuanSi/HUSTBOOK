package com.hedspi.javalorant.dto;

import java.util.Date;

public class ExpenseDTO {
    private String expenseType;
    private double amount;
    private String date;
    private String description;
    
    public ExpenseDTO(String expenseType, double amount, String date, String description){
        this.expenseType = expenseType;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }
    
    public String getExpenseType(){
        return this.expenseType;
    }
    
    public void setExpenseType(String expenseType){
        this.expenseType = expenseType;
    }
    
    public double getAmount(){
        return this.amount;
    }
    
    public void setAmount(double amount){
        this.amount = amount;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public void setDate(String date){
        this.date = date;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExpenseDTO{" + "expenseType=" + expenseType + ", amount=" + amount + ", date=" + date + ", description=" + description + '}';
    }
}

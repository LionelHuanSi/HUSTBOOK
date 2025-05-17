package javalorant.hustbook.expense;

import java.time.LocalDate;

public class Expense {
    private String expenseID;
    private String expenseType;
    private double amount;
    private LocalDate date;
    private String description;

    public Expense(String expenseID, String expenseType,double amount, LocalDate date, String description) {
        this.expenseID = expenseID;
        this.expenseType = expenseType;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public String getExpenseID() { return expenseID; }
    public void setExpenseID(String expenseID) { this.expenseID = expenseID; }

    public String getExpenseType() { return expenseType; }
    public void setExpenseType(String expenseType) { this.expenseType = expenseType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

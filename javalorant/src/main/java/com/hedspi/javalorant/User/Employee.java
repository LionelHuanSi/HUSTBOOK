package com.hedspi.javalorant.user;

public class Employee extends User implements HiredWorker{
    private static double basicSalary;
    private double coefficient;
    private static final double MAX_COEFFICIENT = 3;
    private static final double MIN_COEFFICIENT = 1;

    public Employee(String username, String password, String fullName, String phoneNumber,
            double basicSalary, double coefficient) 
    {
        super(username, password, fullName, phoneNumber);
        this.Role = UserRole.Employee;
        Employee.basicSalary = basicSalary;
        if (coefficient > MAX_COEFFICIENT || coefficient < MIN_COEFFICIENT) {
            throw new IllegalArgumentException("Coefficient cannot be greater than " + MAX_COEFFICIENT + " or less than " + MIN_COEFFICIENT);
        }
        this.coefficient = coefficient;
    }
    public double getBasicSalary() {
        return basicSalary;
    }
    public void setBasicSalary(double basicSalary) {
        Employee.basicSalary = basicSalary;
    }
    public double getCoefficient() {
        return coefficient;
    }
    public void setCoefficient(double coefficient) {
        if (coefficient > MAX_COEFFICIENT || coefficient < MIN_COEFFICIENT) {
            throw new IllegalArgumentException("Coefficient cannot be greater than " + MAX_COEFFICIENT + " or less than " + MIN_COEFFICIENT);
        }
        this.coefficient = coefficient;
    }
    @Override
    public double tinhLuong() {
        return basicSalary * coefficient;
    }
}

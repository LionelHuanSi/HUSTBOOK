package com.hedspi.javalorant.dto;

import com.hedspi.javalorant.user.UserRole;

public class EmployeeDTO {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    protected UserRole Role;
    private static double basicSalary;
    private double coefficient;

    public EmployeeDTO(String username, String password, String fullName, String phoneNumber,
            double basicSalary, double coefficient) 
    {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.Role = UserRole.Employee;
        EmployeeDTO.basicSalary = basicSalary;
        if (coefficient > 3 || coefficient < 1) {
            throw new IllegalArgumentException("Coefficient cannot be greater than 3 or less than 1");
        }
        this.coefficient = coefficient;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return Role;
    }

    public void setRole(UserRole role) {
        this.Role = role;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        EmployeeDTO.basicSalary = basicSalary;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        if (coefficient > 3 || coefficient < 1) {
            throw new IllegalArgumentException("Coefficient cannot be greater than 3 or less than 1");
        }
        this.coefficient = coefficient;
    }

    public double tinhLuong() {
        return basicSalary * coefficient;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", Role=" + Role +
                ", basicSalary=" + basicSalary +
                ", coefficient=" + coefficient +
                '}';
    }
    
}

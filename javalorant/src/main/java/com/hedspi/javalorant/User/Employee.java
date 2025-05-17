package com.hedspi.javalorant.user;

public class Employee extends User implements BanHang {
    private long salary;

    public Employee(String username, String password, String fullName, String phoneNumber, UserRole role,
            long salary) {
        super(username, password, fullName, phoneNumber, role);
        this.salary = salary;
    }
    public Long getSalary() {
        return salary;
    }
    public void setSalary(Long salary) {
        this.salary = salary;
    }
    @Override
    public long tinhTienThuong() {
        
        return 0;
    }
}

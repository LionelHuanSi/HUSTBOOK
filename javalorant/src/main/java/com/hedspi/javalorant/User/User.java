package com.hedspi.javalorant.user;

public class User {
    public static long countUser = 0;
    private long user_ID;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private UserRole Role;

    public User(String username, String password, String fullName, String phoneNumber, UserRole role) {
        User.countUser++;
        this.user_ID = countUser;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.Role = role;
    }

    public long getUserID() {
        return user_ID;
    }

    public void setUserID(long userID) {
        this.user_ID = userID;
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

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}

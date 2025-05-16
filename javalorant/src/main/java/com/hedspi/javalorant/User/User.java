package com.hedspi.javalorant.user;

public class User {
    private String User_ID;
    private String Username;
    private String Password;
    private UserRole Role;

    public User(String userID, String username, String password, UserRole role) {
        this.User_ID = userID;
        this.Username = username;
        this.Password = password;
        this.Role = role;
    }

    public String getUserID() {
        return User_ID;
    }

    public void setUserID(String userID) {
        this.User_ID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public UserRole getRole() {
        return Role;
    }

    public void setRole(UserRole role) {
        this.Role = role;
    }

    public boolean authenticate(String inputPassword) {
        return this.Password.equals(inputPassword);
    }
}

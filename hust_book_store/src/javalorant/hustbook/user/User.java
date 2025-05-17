package javalorant.hustbook.user;

public class User {
    private String userID;
    private String username;
    private String password;
    private UserRole role;

    public User(String userID, String username, String password, UserRole role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }



    public void setUserID(String userID) {
        this.userID = userID;
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



    public UserRole getRole() {
        return role;
    }



    public void setRole(UserRole role) {
        this.role = role;
    }



    /** So sánh mật khẩu đầu vào với mật khẩu đã lưu **/
    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}

public class UserSystem {
    private int idUser;
    private String username;
    private String password;
    private String role;

    public UserSystem(int idUser, String username, String password, String role) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("User " + username + " logged out.");
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
}


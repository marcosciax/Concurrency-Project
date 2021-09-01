package account_management.Models;

/**
 * Account represents a user
 * user contains a userName that must be unique and a password
 *
 * @author marcosciaxx
 *
 */
public class Account {

    private String userName;
    private String password;

    /**
     * Constructs and Initializes a Account with userName and password
     * @param userName
     * @param password
     */
    public Account(String userName, String password){
        this.userName=userName;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

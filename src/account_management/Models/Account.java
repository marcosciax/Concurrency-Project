package account_management.Models;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Account represents a user
 * user contains a userName that must be unique and a password
 *
 * @author marcosciaxx
 *
 */
public class Account implements Serializable {

    private String userName;
    private String password;
    private InetAddress ipAddress;
    private int port;

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

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

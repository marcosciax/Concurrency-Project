package account_management.Controller;

import ConnectionPage.Connect;
import account_management.DataHandle.AllData;
import account_management.Models.Account;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Controls the Login.fxml file
 */
public class LoginController {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private TextField adress;
    @FXML
    private Label ip;

    public static String adressToCopy;

    public void initialize() throws UnknownHostException {
        ip.setText(InetAddress.getLocalHost().getHostAddress());
    }

    /**
     * Compares the data taken from TextField and PasswordField from login.fxml file with All the account present
     * if the userName and password matches, user continues to Main Game page.
     * @param actionEvent
     */
    public void login(ActionEvent actionEvent) throws IOException {
        for(Account account : AllData.accounts)
            if(userName.getText().equals(account.getUserName()) && password.getText().equals(account.getPassword())){
                Connect.player=account;
                adressToCopy  =  adress.getText();
                Parent root = FXMLLoader.load(getClass().getResource("/ConnectionPage/connect.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        System.exit(0);
                    }
                });
                stage.show();
            }
    }

    /**
     * User can go to sign up page if he/she has no account or wants to make another
     * @param mouseEvent
     * @throws IOException
     */
    public void toSignup(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/account_management/Interface/Signup.fxml"));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

package account_management.Controller;

import account_management.DataHandle.AllData;
import account_management.Models.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls the Login.fxml file
 */
public class LoginController {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    /**
     * Compares the data taken from TextField and PasswordField from login.fxml file with All the account present
     * if the userName and password matches, user continues to Main Game page.
     * @param actionEvent
     */
    public void login(ActionEvent actionEvent){
        for(Account account : AllData.accounts)
            if(userName.getText().equals(account.getUserName()) && password.getText().equals(account.getPassword()))
                System.out.println("Login Successful");
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

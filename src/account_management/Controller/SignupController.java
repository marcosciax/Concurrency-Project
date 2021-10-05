package account_management.Controller;

import account_management.DataHandle.AllData;
import account_management.Models.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.postgresql.util.PSQLException;

import java.io.IOException;
import java.sql.*;

/**
 * Controls the Signup.fxml file
 */
public class SignupController {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    private final String url = "jdbc:postgresql://localhost/AccountData";
    private final String user = "postgres";
    private final String sql_password = "03105784747";

    /**
     * Takes the Data from TextField and PasswordField from Signup.fxml file
     * Checks the Data and then add it to the dataBase
     * Makes a new Account
     * @param actionEvent
     * @throws SQLException
     */
    public void signup(ActionEvent actionEvent) throws SQLException {
        boolean NotRepeat=true; // To check if userName is repeated(not unique) or not

        String SQL = "INSERT INTO account_data(user_name,password) " + "VALUES(?,?)"; // query

        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1,userName.getText()); // adding Data to DataBase
        preparedStatement.setString(2,password.getText()); // adding Data to DataBase
        try {
            preparedStatement.executeUpdate(); // executing the update
        }catch (PSQLException e){
            NotRepeat=false;
            Alert alert = new Alert(Alert.AlertType.ERROR,"UserName already exist! Please enter something else!"); // if userName is not Unique
            alert.show();
        }

        if(NotRepeat)
            AllData.accounts.add(new Account(userName.getText(),password.getText())); // new Account created

    }

    /**
     * user can go to login page if he/she has already an account
     * @param mouseEvent
     * @throws IOException
     */
    public void toLogin(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/account_management/Interface/Login.fxml"));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates a new Connection to PostgreSQL DataBase
     * @return Connection
     */
    public Connection connect(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url,user,sql_password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }
}

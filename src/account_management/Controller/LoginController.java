package account_management.Controller;

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
import java.sql.*;

public class LoginController {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    private final String url = "jdbc:postgresql://localhost/AccountData";
    private final String user = "postgres";
    private final String sql_password = "03105784747";

    public void login(ActionEvent actionEvent) throws SQLException {
        String SQL = "SELECT user_name,password FROM account_data";


        Connection connection = connect();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);

        while(resultSet.next()){
            if(userName.getText().equals(resultSet.getString("user_name")) && password.getText().equals(resultSet.getString("password")))
                System.out.println("Login Successful");
        }

    }

    public void toSignup(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/account_management/Interface/Signup.fxml"));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public Connection connect(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url,user,sql_password);
            System.out.println("Connected to DataBase Successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }
}

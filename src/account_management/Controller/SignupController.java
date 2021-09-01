package account_management.Controller;

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

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;

public class SignupController {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    private final String url = "jdbc:postgresql://localhost/AccountData";
    private final String user = "postgres";
    private final String sql_password = "03105784747";

    public void signup(ActionEvent actionEvent) throws SQLException {
        String SQL = "INSERT INTO account_data(user_name,password) " + "VALUES(?,?)";

        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1,userName.getText());
        preparedStatement.setString(2,password.getText());
        try {
            preparedStatement.executeUpdate();
        }catch (PSQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"UserName already exist! Please enter something else!");
            alert.show();
        }
    }

    public void toLogin(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/account_management/Interface/Login.fxml"));
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

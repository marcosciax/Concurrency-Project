package account_management.DataHandle;

import account_management.Models.Account;

import java.io.FileReader;
import java.sql.*;

/**
 * ReadData Class is accountable for all the Data reading
 */
public class ReadData {

    private final String url = "jdbc:postgresql://localhost/AccountData";
    private final String user = "postgres";
    private final String sql_password = "03105784747";

    /**
     * Creates a new Connection
     * Creates a new Query of Reading Data from DataBase
     * Creates new Accounts from read Data
     * @throws SQLException
     */
    public void read() throws SQLException {
        String SQL = "SELECT user_name,password FROM account_data"; // Query

        Connection connection = connect();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);

        while(resultSet.next()){
            AllData.accounts.add(new Account(resultSet.getString("user_name"),resultSet.getString("password")));
        }
    }

    /**
     * Creates a new Connection to PostgreSQL DataBase
     * @return Connection
     */
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

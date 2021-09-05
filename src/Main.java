import account_management.DataHandle.ReadData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * <p>This Application can run four games simultaneously with the option of chatting with opponents</p>
 * <p>This Class initiates the Application</p>
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("account_management/Interface/login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("ChessGame/Interface/Board.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root,1080,1080));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        ReadData readData = new ReadData();
        readData.read();
        launch(args);
    }
}

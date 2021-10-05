import ServerNClient.GameClient;
import ServerNClient.GameServer;
import account_management.DataHandle.ReadData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * <p>This Application can run four games simultaneously with the option of chatting with opponents</p>
 * <p>This Class initiates the Application</p>
 */
public class Main extends Application {

    private static GameClient socketClient;
    private static GameServer socketServer;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("account_management/Interface/login.fxml"));
        primaryStage.setTitle("Multi Player Games");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        ReadData readData = new ReadData();
        readData.read();
        launch(args);
    }


    public static GameClient getSocketClient() {
        return socketClient;
    }

    public static GameServer getSocketServer() {
        return socketServer;
    }

}

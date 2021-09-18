import Packets.Packet00Login;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import account_management.DataHandle.AllData;
import account_management.DataHandle.ReadData;
import account_management.Models.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.BindException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * <p>This Application can run four games simultaneously with the option of chatting with opponents</p>
 * <p>This Class initiates the Application</p>
 */
public class Main extends Application {

    private static GameClient socketClient;
    private static GameServer socketServer;


    @Override
    public void start(Stage primaryStage) throws Exception{

//        Parent root = FXMLLoader.load(getClass().getResource("account_management/Interface/login.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("Checkers/Interface/board.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root,1920,1080));
//        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException, IOException {
        ReadData readData = new ReadData();
        readData.read();


        socketClient = new GameClient("localhost");
        socketClient.start();

        Account playerOne = null;
        Account playerTwo = null;

        for(Account account: AllData.accounts) {
            account.setIpAddress(null);
            account.setPort(-1);
            if (account.getUserName().equals("abdul"))
                playerOne = account;
            else if (account.getUserName().equals("hello"))
                playerTwo = account;
        }

        boolean p= false;
        Packet00Login loginPacket = null;
        try{
            socketServer=new GameServer();
        }catch (BindException e){
            p=true;
            assert playerTwo != null;
            loginPacket = new Packet00Login(playerTwo.getUserName());
        }

        if(!p){
            socketServer.start();
            assert playerOne != null;
            loginPacket = new Packet00Login(playerOne.getUserName());
//            socketServer.addConnection(playerOne,loginPacket);
        }


        loginPacket.writeData(socketClient);

//        System.out.println("Do you want to run the server : ");
//        Scanner scanner = new Scanner(System.in);
//        int choice = scanner.nextInt();
//        if(choice==0){



//        socketClient.sendData("ping".getBytes(StandardCharsets.UTF_8));
//        launch(args);
    }
}

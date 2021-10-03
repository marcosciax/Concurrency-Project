package ChessGame.template;

//Chess  application

//imports
import ChatSystem.ChatController;
import Checkers.Models.BoardInfo;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import account_management.DataHandle.AllData;
import account_management.DataHandle.ReadData;
import account_management.Models.Account;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.BindException;
import java.sql.SQLException;

//class definition
public class ChessApplication extends Application {
	// overridden init method
	@Override
	public void init() {
//		ChessBoard.playerOne = new Account("Abdul Mannan ", "Hello");
//		ChessBoard.playerTwo = new Account("Abdullah Khan ", "jello");
		// initialize the layout, create a CustomControl and it to the layout
		sp_mainlayout = new StackPane(); 
		cc_custom = new CustomControl();
		sp_mainlayout.getChildren().add(cc_custom);
	}
	
	// overridden start method
	@Override
	public void start(Stage primaryStage) {
		// set the title and scene, and show the stage
		primaryStage.setTitle("Chess game");
		primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
		primaryStage.setMinWidth(300);
		primaryStage.setMinHeight(300);
		primaryStage.show();
	}

	// overridden stop method
	@Override
	public void stop() {
	}
	
	// entry point into our program to launch our JavaFX application
	public static void main(String[] args) throws SQLException, IOException {
		ReadData readData = new ReadData();
		readData.read();

		boolean p = false;

		GameServer socketServer = null;
		GameClient socketClient;

		Account playerOne = null;
		Account playerTwo = null;

		for(Account account: AllData.accounts) {
			if (account.getUserName().equals("abdul"))
				playerOne = account;
			else if (account.getUserName().equals("hello"))
				playerTwo = account;
		}

		try{
			socketServer=new GameServer(8000);
		}catch (BindException e){
			p=true;
		}

		if(p){
			socketClient = new GameClient(8000);
			socketClient.start();
			ChessBoard.gameClient = socketClient;
			ChessBoard.playerTwo=playerTwo;
			Account finalPlayerTwo = playerTwo;
			Runnable t = () -> {
				while (true) {
					try {
						socketClient.sendData(finalPlayerTwo);
						ChessBoard.playerOne = (Account) socketClient.readData();
						break;
					} catch (IOException | ClassNotFoundException k) {
						k.printStackTrace();
					}
				}
			};
			t.run();
		}

		if(!p) {
			ChessBoard.playerOne=playerOne;
			socketServer.start();
			ChessBoard.gameServer=socketServer;
			Account finalPlayerOne = playerOne;
			GameServer finalSocketServer = socketServer;
			Runnable t = () -> {
				while (true) {
					try {
						if(finalSocketServer.getClient()!=null) {
							System.out.println("in here");
							finalSocketServer.sendData(finalPlayerOne);
							ChessBoard.playerTwo = (Account) finalSocketServer.readData();
							break;
						}
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			};
			t.run();
		}
		int port = 4000;
		launch(args);
	}
	
	// private fields for this class
	private StackPane sp_mainlayout;	//layout which allows items to be positioned on top of each other
	private CustomControl cc_custom;	//control which has a board and detects mouse and keyboard events
}
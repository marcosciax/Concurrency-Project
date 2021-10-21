package ChessGame.template;

import ChatSystem.ChatController;
import Checkers.Models.BoardInfo;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomControl extends Control {
	
	//similar to previous custom controlls but must handle more
	//complex mouse interactions and key interactions
	public CustomControl(){

		int port = 4001;

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				if(ChessBoard.gameServer!=null){
					try {
						ChatController.setServer(new GameServer(port));
						ChatController.getServer().start();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ChatController.setClient(null);
				}
				else if(ChessBoard.gameClient!=null) {
					ChatController.setServer(null);
					try {
						ChatController.setClient(new GameClient(port));
						ChatController.getClient().start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();


		setSkin(new CustomControlSkin(this));
		
		statusBar = new StatusBar();
		chessBoard = new ChessBoard(statusBar);
		getChildren().addAll(statusBar, chessBoard);
		
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				try {
					chessBoard.selectPiece(event.getX(), event.getY() - (statusBarSize / 2));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});

		// Add a key listener that will reset the game
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.SPACE)
					chessBoard.resetGame();
			}
		});
		
		statusBar.getResetButton().setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				chessBoard.resetGame();
			}
			
		});

	}
	
	public void resize(double width, double height){
		super.resize(width, height - statusBarSize);
		chessBoard.setTranslateY(statusBarSize / 2);
		chessBoard.resize(width, height - statusBarSize);
		statusBar.resize(width, statusBarSize);
		statusBar.setTranslateY(-(statusBarSize / 2));
	}
	
	private ChessBoard chessBoard;
	private StatusBar statusBar; 
	private int statusBarSize = 100;	
}

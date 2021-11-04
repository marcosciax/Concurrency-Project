package Checkers.Controller;

import ChatSystem.ChatController;
import Checkers.Models.BoardInfo;
import Checkers.Models.Piece;
import Checkers.Models.Spot;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import account_management.Models.Account;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controls The Board
 * Every Change that happens to Board initiates from this Class
 */
public class BoardController {

    @FXML
    private Pane board;
    @FXML
    private Label playerOneLabel;
    @FXML
    private Label playerTwoLabel;

    private final int tile_size=135;
    public static Account playerOne;
    public static Account playerTwo;
    private BoardInfo boardInfo;
    public static Spot[] spots;
    public static Piece[] playerOnePieces;
    public static Piece[] playerTwoPieces;
    private static boolean playerOneTurn;

    /**
     * This Method is called as soon as The class is in use
     * Initiates Every Method requires to Start the Game
     */
    public void initialize(){

        spots = new Spot[64];
        playerOnePieces = new Piece[12];
        playerTwoPieces = new Piece[12];

        int port = 4000;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(BoardInfo.getSocketServer()!=null){
                    try {
                        ChatController.setServer(new GameServer(port));
                        ChatController.getServer().start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ChatController.setClient(null);
                }
                else if(BoardInfo.getSocketClient()!=null) {
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

        initializeSpots();
        initializePieces();
        setBoard();
        Move move = new Move(board);
        for(int i=0 ; i < 12 ; i++) {
            move.movePiece(playerOnePieces[i]);
            move.movePiece(playerTwoPieces[i]);
        }

    }

    /**
     * This Method initialize he Players
     * @param playerOne is First Player (that makes the game)
     * @param playerTwo is the player who will join the game created by player one
     */
    public void setPlayers(Account playerOne, Account playerTwo){
        BoardController.playerOne = playerOne;
        BoardController.playerTwo = playerTwo;
    }

    /**
     * Initializes the Spots (Tiles) of Board
     * Initializes Their Location in Board
     * Initializes weather the tile would be dark or light
     */
    public void initializeSpots(){
        boolean isDark;
        for(int i=0,j=0,k=0 ; i < 64 ; i++,k++){
            isDark = (j+k) % 2 == 0;
            spots[i] = new Spot((420+tile_size*k),(tile_size*j),k,j,isDark,i);
            if(i==7||i==15||i==23||i==31||i==39||i==47||i==55||i==63) {
                j++;
                k=-1;
            }
        }
        spots[8] = new Spot((420),(tile_size),0,1,false,8);
        spots[16] = new Spot((420),(tile_size*2),0,2,true,16);  
        spots[24] = new Spot((420),(tile_size*3),0,3,false,24);
        spots[32] = new Spot((420),(tile_size*4),0,4,true,32);
        spots[40] = new Spot((420),(tile_size*5),0,5,false,40);
        spots[48] = new Spot((420),(tile_size*6),0,6,true,48);
        spots[56] = new Spot((420),(tile_size*7),0,7,false,56);
    }

    /**
     * Initializes Pieces of Both Players in Board
     * Black Pieces with white border for player Two
     * White Pieces with black border for Player One
     */
    public void initializePieces(){
        for(int i=0,j=0 ; i < 24 ; i++){
            if(spots[i].isDark()) {
                playerTwoPieces[j] = new Piece(spots[i], playerTwo);
                playerTwoPieces[j].setFill(Color.rgb(0,0,0));
                playerTwoPieces[j].setStroke(Color.rgb(255,255,255));
                playerTwoPieces[j].setDisable(true);
                j++;
            }
        }
        for(int i=40,k=0 ; i < 64 ; i++){
            if(spots[i].isDark()) {
                playerOnePieces[k] = new Piece(spots[i], playerOne);
                playerOnePieces[k].setFill(Color.rgb(255,255,255));
                playerOnePieces[k].setStroke(Color.rgb(0,0,0));
                k++;
            }
        }

    }

    /**
     * Adds the Initialized Pieces and Spots(Tiles) in the boardGUI
     */
    public void setBoard(){
        for(int i=0 ; i < 64 ; i++){
            board.getChildren().add(spots[i]);
        }
        for(int i =0 ; i < 12 ; i++){
            board.getChildren().add(playerOnePieces[i]);
            board.getChildren().add(playerTwoPieces[i]);
        }
    }

    public void setBoard(Pane board){
        board.getChildren().clear();
        for(int i=0 ; i < 64 ; i++){
            board.getChildren().add(spots[i]);
        }
        for(int i =0 ; i < 12 ; i++) {
            board.getChildren().add(playerOnePieces[i]);
            board.getChildren().add(playerTwoPieces[i]);
        }
    }

    public void removeKilledPieces(Pane borad, ArrayList<Piece> pieces){
        board.getChildren().clear();
//        for(Piece piece : pieces){
//            board.getChildren().remove(piece);
//        }
    }

    /**
     * Changes the Player Turn every time one player has moved the piece
     * If its player One turn Player Two pieces can't be touched and vice versa
     */
    public void changePlayerTurnServer(){
        if(playerOneTurn)
            for (int i=0 ; i < 12 ; i++) {
                playerOnePieces[i].setDisable(false);
            }
        else
            for (int i=0 ; i < 12 ; i++) {
                playerOnePieces[i].setDisable(true);
            }

        playerOneTurn= !playerOneTurn;

    }

    public void changePlayerTurnClient(){
        if(playerOneTurn)
            for (int i=0 ; i < 12 ; i++) {
                playerTwoPieces[i].setDisable(true);
            }
        else
            for (int i=0 ; i < 12 ; i++) {
                playerTwoPieces[i].setDisable(false);
            }

        playerOneTurn= !playerOneTurn;

    }

    public void chat(ActionEvent actionEvent) throws IOException {


        ChatController.setPlayerOne(BoardInfo.playerOne);
        ChatController.setPlayerTwo(BoardInfo.playerTwo);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/ChatSystem/ChatWindow.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
    }
}

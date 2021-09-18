package Checkers.Controller;

import Checkers.Models.BoardInfo;
import Checkers.Models.Piece;
import Checkers.Models.Spot;
import account_management.Models.Account;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

        boardInfo = new BoardInfo();

//        setPlayers(new Account("",""),new Account("",""));

        spots = new Spot[64];
        playerOnePieces = new Piece[12];
        playerTwoPieces = new Piece[12];

        initializeSpots();
        initializePieces();
        setBoard();
        Move move = new Move(board);
        for(int i=0 ; i < 12 ; i++) {
            move.movePiece(playerOnePieces[i]);
            move.movePiece(playerTwoPieces[i]);
//            move.getSpotInfo(spots[i]);
        }

        playerOneLabel.setText(playerOne.getUserName());
        if(playerTwo!=null)
            playerTwoLabel.setText(playerTwo.getUserName());

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
            spots[i] = new Spot((420+tile_size*k),(tile_size*j),k,j,isDark);
            System.out.println(j+" "+k);
            if(i==7||i==15||i==23||i==31||i==39||i==47||i==55||i==63) {
                j++;
                k=-1;
            }
        }
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

    /**
     * Changes the Player Turn every time one player has moved the piece
     * If its player One turn Player Two pieces can't be touched and vice versa
     */
    public void changePlayerTurn(){
        System.out.println(playerOneTurn);

        if(playerOneTurn)
            for (int i=0 ; i < 12 ; i++) {
                playerOnePieces[i].setDisable(false);
                playerTwoPieces[i].setDisable(true);
            }
        else
            for (int i=0 ; i < 12 ; i++) {
                playerOnePieces[i].setDisable(true);
                playerTwoPieces[i].setDisable(false);
            }

        playerOneTurn= !playerOneTurn;

    }

    public void update(ActionEvent actionEvent) {
        playerTwoLabel.setText(playerTwo.getUserName());
    }
}

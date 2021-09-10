package Checkers.Controller;

import Checkers.Models.BoardInfo;
import Checkers.Models.Piece;
import Checkers.Models.Spot;
import account_management.Models.Account;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BoardController {

    @FXML
    private Pane board;

    private final int tile_size=135;
    public static Account playerOne;
    public static Account playerTwo;
    private BoardInfo boardInfo;
    public static Spot[] spots;
    public static Piece[] playerOnePieces;
    public static Piece[] playerTwoPieces;

    public void initialize(){

        boardInfo = new BoardInfo(playerOne,playerTwo);

        setPlayers(new Account("abdul","123"),new Account("khan","456"));

        spots = new Spot[64];
        playerOnePieces = new Piece[12];
        playerTwoPieces = new Piece[12];

        initializeSpots();
        initializePieces();
        setBoard();
        Move move = new Move();
        for(int i=0 ; i < 12 ; i++) {
            move.movePiece(playerOnePieces[i]);
            move.movePiece(playerTwoPieces[i]);
//            move.getSpotInfo(spots[i]);
        }
    }

    public void setPlayers(Account playerOne, Account playerTwo){
        BoardController.playerOne = playerOne;
        BoardController.playerTwo = playerTwo;
    }

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
//
    public void initializePieces(){
        for(int i=0,j=0 ; i < 24 ; i++){
            if(spots[i].isDark()) {
                playerTwoPieces[j] = new Piece(spots[i], playerTwo);
                playerTwoPieces[j].setFill(Color.rgb(0,0,0));
                playerTwoPieces[j].setStroke(Color.rgb(255,255,255));
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
        spots[36].setPiece(null);
        playerTwoPieces[1].setSpot(spots[36]);

    }

    public void setBoard(){
        for(int i=0 ; i < 64 ; i++){
            board.getChildren().add(spots[i]);
        }
        for(int i =0 ; i < 12 ; i++){
            board.getChildren().add(playerOnePieces[i]);
            board.getChildren().add(playerTwoPieces[i]);
        }
    }

}

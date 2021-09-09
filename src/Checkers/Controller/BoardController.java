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
    private Account playerOne;
    private Account playerTwo;
    private BoardInfo boardInfo;
    public static Spot[][] spots;
    public static Piece[] playerOnePieces;
    public static Piece[] playerTwoPieces;

    public void initialize(){

        boardInfo = new BoardInfo(playerOne,playerTwo);

        spots = new Spot[8][8];
        playerOnePieces = new Piece[12];
        playerTwoPieces = new Piece[12];

        initializeSpots();
        initializePieces();
        setBoard();
    }

    public void setPlayers(Account playerOne, Account playerTwo){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public void initializeSpots(){
        boolean isDark;
        for(int i=0 ; i < 8 ; i++){
            for(int j=0 ; j < 8 ; j++) {
                isDark = (i + j) % 2 == 0;
                spots[i][j] = new Spot((420 + (tile_size * i)), (tile_size * j), i, j, isDark);
            }
        }
    }

    public void initializePieces(){
        int k=0,d=0;
        for (int i=0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 3 || i == 4)
                    break;
                else if ((i == 0 || i == 1 || i == 2)&&k<12&&spots[i][j].isDark()) {
                    playerOnePieces[k] = new Piece(spots[j][i], playerOne);
                    playerOnePieces[k].setFill(Color.rgb(255,255,255));
                    playerOnePieces[k].setStroke(Color.rgb(0,0,0));
                    k++;
                }
                else if ((i == 5 || i == 6 || i == 7)&& d<12 && spots[i][j].isDark()){
                    playerTwoPieces[d] = new Piece(spots[j][i], playerTwo);
                    playerTwoPieces[d].setFill(Color.rgb(0,0,0));
                    playerTwoPieces[d].setStroke(Color.rgb(255,255,255));
                    d++;
                }
            }
        }
    }

    public void setBoard(){
        for(int i=0 ; i < 8 ; i++){
            for(int j=0 ; j< 8 ; j++){
                board.getChildren().add(spots[i][j]);
                System.out.println(spots[i][j].isDark());
            }
        }
        for(int i =0 ; i < 12 ; i++){
            board.getChildren().add(playerOnePieces[i]);
            board.getChildren().add(playerTwoPieces[i]);
        }
    }

}

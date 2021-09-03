package ChessGame.Controller;

import ChessGame.Models.Chess;
import ChessGame.Models.Pieces.BlackOnes.*;
import ChessGame.Models.Pieces.WhiteOnes.*;
import ChessGame.Models.Spot;
import account_management.Models.Account;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.converter.ShortStringConverter;

public class BoardController {

    private Chess chess;
    public static Spot[][] spots;
    private Account playerOne;
    private Account playerTwo;
    @FXML
    private GridPane board;

    public void initialize(){

        chess = new Chess(this,playerOne,playerTwo);
        spots = new Spot[8][8];
        for(int i=0 ; i < 8 ; i++){
            for(int j=0 ; j < 8 ; j++){
                spots[i][j] = new Spot(i,j);
            }
        }

        initializePieces();
        resetGame();
        selectSpot();

    }

    public void selectSpot(){
        for(int i=0 ; i < 8 ; i++){
            for(int j=0 ; j < 8 ; j++){
                if(spots[i][j].getPiece()!=null){
                    int finalI = i;
                    int finalJ = j;
                    spots[i][j].getPiece().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            System.out.println("Spot Selected : Row : " + finalI + " Column " + finalJ);
                        }
                    });
                }
            }
        }
    }

    public void initializePieces(){
        // Player One // White Pieces
        spots[7][0].setPiece(new W_Rook(playerTwo,7,0));spots[7][0].setEmpty(false);
        spots[7][1].setPiece(new W_Knight(playerTwo,7,1));spots[7][1].setEmpty(false);
        spots[7][2].setPiece(new W_Bishop(playerTwo,7,2));spots[7][2].setEmpty(false);
        spots[7][3].setPiece(new W_Queen(playerTwo,7,3));spots[7][3].setEmpty(false);
        spots[7][4].setPiece(new W_King(playerTwo,7,4));spots[7][4].setEmpty(false);
        spots[7][5].setPiece(new W_Bishop(playerTwo,7,5));spots[7][5].setEmpty(false);
        spots[7][6].setPiece(new W_Knight(playerTwo,7,6));spots[7][6].setEmpty(false);
        spots[7][7].setPiece(new W_Rook(playerTwo,7,7));spots[7][7].setEmpty(false);
        for(int i=0 ; i < 8 ; i++) {
            spots[6][i].setPiece(new W_Pawn(playerTwo, 1, i));
            spots[6][i].setEmpty(false);
        }

        // Player Two // Black Pieces
        spots[0][0].setPiece(new Rook(playerTwo,0,0));spots[0][0].setEmpty(false);
        spots[0][1].setPiece(new Knight(playerTwo,0,1));spots[0][1].setEmpty(false);
        spots[0][2].setPiece(new Bishop(playerTwo,0,2));spots[0][2].setEmpty(false);
        spots[0][3].setPiece(new Queen(playerTwo,0,3));spots[0][3].setEmpty(false);
        spots[0][4].setPiece(new King(playerTwo,0,4));spots[0][4].setEmpty(false);
        spots[0][5].setPiece(new Bishop(playerTwo,0,5));spots[0][5].setEmpty(false);
        spots[0][6].setPiece(new Knight(playerTwo,0,6));spots[0][6].setEmpty(false);
        spots[0][7].setPiece(new Rook(playerTwo,0,7));spots[0][7].setEmpty(false);
        for(int i=0 ; i < 8 ; i++) {
            spots[1][i].setPiece(new Pawn(playerTwo, 1, i));
            spots[1][i].setEmpty(false);
        }
    }

    public void resetGame(){
        for(int i=0 ; i<2 ; i++){
            for(int j=0 ; j < 8 ; j++){
                board.add(spots[i][j].getPiece(),j,i);
            }
        }
        for(int i=6 ; i<8 ; i++){
            for(int j=0 ; j < 8 ; j++){
                board.add(spots[i][j].getPiece(),j,i);
            }
        }
    }

    public void setPlayerTwo(Account playerTwo) {
        this.playerTwo = playerTwo;
    }

    public void setPlayerOne(Account playerOne) {
        this.playerOne = playerOne;
    }



}

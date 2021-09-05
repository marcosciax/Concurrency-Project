package ChessGame.Controller;

import ChessGame.Models.Chess;
import ChessGame.Models.Pieces.BlackOnes.*;
import ChessGame.Models.Pieces.Piece;
import ChessGame.Models.Pieces.WhiteOnes.*;
import ChessGame.Models.Spot;
import account_management.Models.Account;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    private Chess chess;
    public static Spot[][] spots;
    public static Piece[] whitePieces;
    public static Piece[] blackPieces;
    private Account playerOne;
    private Account playerTwo;
    private final int tile_size = 135;

    @FXML
    private Pane board;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        chess = new Chess(this,playerOne,playerTwo);
//        spots = new Spot[8][8];
//        for(int i=0 ; i < 8 ; i++){
//            for(int j=0 ; j < 8 ; j++){
//                spots[i][j] = new Spot(i,j);
//            }
//        }

        makeBoard();
        initializePieces();
        resetGame();
        makeMovable();
    }

    public void makeBoard(){
        spots = new Spot[8][8];
        whitePieces= new Piece[16];
        blackPieces= new Piece[16];

        for(int i=0 ; i < 8 ; i++){
            for(int j=0 ; j < 8 ; j++){
                if((i+j)%2==0)
                    spots[i][j] = new Spot(tile_size,i,j,(j*tile_size),(i*tile_size),Color.rgb(0, 0, 139));
                else
                    spots[i][j] = new Spot(tile_size,i,j,(j*tile_size),(i*tile_size),Color.rgb(0, 150, 255));
            }
        }
    }

    public void makeMovable(){
        MovePiece movePiece = new MovePiece();
        for(int i=0 ; i < 16 ; i++) {
            movePiece.move(whitePieces[i]);
            movePiece.move(blackPieces[i]);
        }
    }

    public void initializePieces(){

        whitePieces[0] = new W_Rook(playerTwo,7,0);
        whitePieces[1] = new W_Rook(playerTwo,7,7);
        whitePieces[2] = new W_Knight(playerTwo,7,1);
        whitePieces[3] = new W_Knight(playerTwo,7,6);
        whitePieces[4] = new W_Bishop(playerTwo,7,2);
        whitePieces[5] = new W_Bishop(playerTwo,7,5);
        whitePieces[6] = new W_Queen(playerTwo,7,3);
        whitePieces[7] = new W_King(playerTwo,7,4);

        // Player One // White Pieces
        spots[7][0].setPiece(whitePieces[0]);spots[7][0].setEmpty(false);
        spots[7][1].setPiece(whitePieces[2]);spots[7][1].setEmpty(false);
        spots[7][2].setPiece(whitePieces[4]);spots[7][2].setEmpty(false);
        spots[7][3].setPiece(whitePieces[6]);spots[7][3].setEmpty(false);
        spots[7][4].setPiece(whitePieces[7]);spots[7][4].setEmpty(false);
        spots[7][5].setPiece(whitePieces[5]);spots[7][5].setEmpty(false);
        spots[7][6].setPiece(whitePieces[3]);spots[7][6].setEmpty(false);
        spots[7][7].setPiece(whitePieces[1]);spots[7][7].setEmpty(false);
        for(int i=0 ; i < 8 ; i++) {
            whitePieces[i+8] = new W_Pawn(playerTwo,6,i);
            spots[6][i].setPiece(whitePieces[i+8]);
            spots[6][i].setEmpty(false);
        }

        blackPieces[0] = new Rook(playerTwo,7,0);
        blackPieces[1] = new Rook(playerTwo,7,7);
        blackPieces[2] = new Knight(playerTwo,7,1);
        blackPieces[3] = new Knight(playerTwo,7,6);
        blackPieces[4] = new Bishop(playerTwo,7,2);
        blackPieces[5] = new Bishop(playerTwo,7,5);
        blackPieces[6] = new Queen(playerTwo,7,3);
        blackPieces[7] = new King(playerTwo,7,4);
        // Player Two // Black Pieces
        spots[0][0].setPiece(blackPieces[0]);spots[0][0].setEmpty(false);
        spots[0][1].setPiece(blackPieces[2]);spots[0][1].setEmpty(false);
        spots[0][2].setPiece(blackPieces[4]);spots[0][2].setEmpty(false);
        spots[0][3].setPiece(blackPieces[6]);spots[0][3].setEmpty(false);
        spots[0][4].setPiece(blackPieces[7]);spots[0][4].setEmpty(false);
        spots[0][5].setPiece(blackPieces[5]);spots[0][5].setEmpty(false);
        spots[0][6].setPiece(blackPieces[3]);spots[0][6].setEmpty(false);
        spots[0][7].setPiece(blackPieces[1]);spots[0][7].setEmpty(false);
        for(int i=0 ; i < 8 ; i++) {
            blackPieces[i+8] = new Pawn(playerOne,1,i);
            spots[1][i].setPiece(blackPieces[i+8]);
            spots[1][i].setEmpty(false);
        }


    }

    public void resetGame(){
        for(int i=0 ; i < 8 ; i++){
            for(int j=0 ; j < 8 ; j++){
                board.getChildren().add(spots[i][j]);
            }
        }
        for(int i = 0 ; i < 16 ; i++){
            board.getChildren().add(whitePieces[i]);
            board.getChildren().add(blackPieces[i]);
        }
    }

    public void setPlayerTwo(Account playerTwo) {
        this.playerTwo = playerTwo;
    }

    public void setPlayerOne(Account playerOne) {
        this.playerOne = playerOne;
    }


}

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


/**
 * Controls All Game
 * Responsible for working of game
 */
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

        spots = new Spot[8][8];
        whitePieces= new Piece[16];
        blackPieces= new Piece[16];

        makeBoard();
        initializePieces();
        resetGame();
        makeMovable();
    }

    /**
     * Makes the Board
     */
    public void makeBoard(){
        for(int i=0 ; i < 8 ; i++){
            for(int j=0 ; j < 8 ; j++){
                if((i+j)%2!=0)
                    spots[i][j] = new Spot(tile_size,i,j,(i*tile_size),(j*tile_size),Color.rgb(118,150,86));
                else
                    spots[i][j] = new Spot(tile_size,i,j,(i*tile_size),(j*tile_size),Color.rgb(238,238,210));
            }
        }
    }

    /**
     * Redirects to movePiece Class
     * responsible for pieces to move
     */
    public void makeMovable(){
        MovePiece movePiece = new MovePiece();
        for(int i=0 ; i < 16 ; i++) {
            movePiece.move(whitePieces[i],board);
            movePiece.move(blackPieces[i],board);
        }
    }

    /**
     * Initializes the Pieces to default location by putting them in respective Tiles(Spots)
     */
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
        spots[0][7].setPiece(whitePieces[0]);spots[7][0].setEmpty(false);
        spots[1][7].setPiece(whitePieces[2]);spots[7][1].setEmpty(false);
        spots[2][7].setPiece(whitePieces[4]);spots[7][2].setEmpty(false);
        spots[3][7].setPiece(whitePieces[7]);spots[7][3].setEmpty(false);
        spots[4][7].setPiece(whitePieces[6]);spots[7][4].setEmpty(false);
        spots[5][7].setPiece(whitePieces[5]);spots[7][5].setEmpty(false);
        spots[6][7].setPiece(whitePieces[3]);spots[7][6].setEmpty(false);
        spots[7][7].setPiece(whitePieces[1]);spots[7][7].setEmpty(false);
        for(int i=0 ; i < 8 ; i++) {
            whitePieces[i+8] = new W_Pawn(playerTwo,6,i);
            spots[i][6].setPiece(whitePieces[i+8]);
            spots[i][6].setEmpty(false);
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
        spots[1][0].setPiece(blackPieces[2]);spots[0][1].setEmpty(false);
        spots[2][0].setPiece(blackPieces[4]);spots[0][2].setEmpty(false);
        spots[3][0].setPiece(blackPieces[7]);spots[0][3].setEmpty(false);
        spots[4][0].setPiece(blackPieces[6]);spots[0][4].setEmpty(false);
        spots[5][0].setPiece(blackPieces[5]);spots[0][5].setEmpty(false);
        spots[6][0].setPiece(blackPieces[3]);spots[0][6].setEmpty(false);
        spots[7][0].setPiece(blackPieces[1]);spots[0][7].setEmpty(false);
        for(int i=0 ; i < 8 ; i++) {
            blackPieces[i+8] = new Pawn(playerOne,1,i);
            spots[i][1].setPiece(blackPieces[i+8]);
            spots[i][1].setEmpty(false);
        }


    }

    /**
     * Resets the Game Board to initial Position
     */
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

    public Pane getBoard() {
        return board;
    }
}

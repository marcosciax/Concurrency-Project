package com.client.controller;

import com.client.CDataService;
import com.client.Model.Chess;
import com.client.Model.TicTacToe;
import com.client.NetworkService;
import com.client.chess.King;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ChessController {

    String toUser;
    String playFirst;
    int roomId;

    enum TURN{
        BLACK,WHITE
    };
    boolean isEnemyTurn = false;
    TURN turn;

    Chess chess;
    boolean isDone  = false;

    @FXML
    private Label vsText;
    @FXML
    private Label turnText;
    @FXML
    private Label messageText;

    @FXML
    private GridPane gridPane;

    private HBox selectedBox = null;
    private int selectedRow = 99;
    private int selectedCol = 99;

    public ChessController(String toUser, String playFirst, int roomId) {
        this.toUser = toUser;
        this.playFirst = playFirst;
        this.roomId = roomId;
    }

    @FXML
    public void initialize() throws IOException {
        chess = new Chess();
//        ticTacToe = new TicTacToe();
        vsText.setText("VS " + toUser);
        if(playFirst.equals(CDataService.getInstance().getUsername())){
            turnText.setText("TURN: YOU");
            isEnemyTurn = false;
            messageText.setText("You are White. Move first");
            turn = TURN.WHITE;
        }
        else{
            turnText.setText("TURN: ENEMY");
            isEnemyTurn = true;
            turn = TURN.BLACK;
            messageText.setText("You are Black");
        }
        resetColor();

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8;col++){
                HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );

                int currentRow = row;
                int currentCol = col;
                p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(selectedBox == null && p.getChildren().size() == 0){
                            return;
                        }
                        if(selectedBox == null && turn == TURN.WHITE && chess.isBlackPiece(currentRow, currentCol)){
                            return;
                        }
                        if(selectedBox == null && turn == TURN.BLACK && chess.isWhitePiece(currentRow, currentCol)){
                            return;
                        }
                        if(selectedBox == p){
                            return;
                        }
                        if(selectedBox != null && turn == TURN.WHITE && chess.isWhitePiece(currentRow, currentCol)){
                            return;
                        }
                        if(selectedBox != null && turn == TURN.BLACK && chess.isBlackPiece(currentRow, currentCol)){
                            return;
                        }

                        if(selectedBox == null){
                            resetColor();
                            p.setStyle(p.getStyle() +";-fx-background-color:#138076");
                            selectedBox = p;
                            selectedRow = currentRow;
                            selectedCol = currentCol;
                        }
                        else{
                            ImageView chessPieceImage = (ImageView) selectedBox.getChildren().get(0);

                            selectedBox.getChildren().clear();
                            p.getChildren().add(chessPieceImage);
                            selectedBox = null;

                            resetColor();

                            // send movement message
                            NetworkService.getInstance().sendMessage(String.format("CHESSPLAY=%s-%s-%d-%d-%d-%d-%d",
                                    CDataService.getInstance().getUsername(),
                                    toUser,
                                    roomId,
                                    selectedRow,
                                    selectedCol,
                                    currentRow,
                                    currentCol
                            ));
                            chess.move(selectedRow,selectedCol,currentRow,currentCol);
                            selectedRow = 99;
                            selectedCol = 99;



                            isEnemyTurn = true;
                            turnText.setText("TURN: ENEMY");
                        }

                    }
                });


                ImageView chessPieceImage = getChessPieceImage(chess.get(row,col));
                if(chessPieceImage == null){
                    continue;
                }

                chessPieceImage.setFitWidth(40);
                chessPieceImage.setFitHeight(40);
                p.getChildren().add(chessPieceImage);

            }
        }
    }

    public void enemyHavePlay(int row ,int col,int toRow,int toCol){
        turnText.setText("TURN: YOU");
        isEnemyTurn = false;
        HBox fromBox = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
        HBox toBox = (HBox) gridPane.getChildren().get( getIndexFromRowCol(toRow,toCol) );

        ImageView chessPieceImage = (ImageView) fromBox.getChildren().get(0);
        fromBox.getChildren().clear();
        toBox.getChildren().add(chessPieceImage);

        chess.move(row,col,toRow,toCol);

//        checkWinner();
    }


    private void resetColor(){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8;col++){
                HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
                p.setStyle(p.getStyle().replace(";-fx-background-color:#138076",""));
            }
        }
    }

    public void loadGame(TicTacToe ticTacToe){
//        this.ticTacToe = ticTacToe;
//
//        for(int row = 0; row < 3;row++){
//            for(int col =0 ; col < 3; col++){
//                HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
//                if( ticTacToe.getAt(row,col) == 'X' ){
//                    p.getChildren().add(getXImage());
//                }
//                else if( ticTacToe.getAt(row,col) == 'O' ) {
//                    p.getChildren().add(getOImage());
//                }
//            }
//        }
//
//        int countX = ticTacToe.count('X');
//        int countO = ticTacToe.count('O');
//
//        if(turn == TicTacToeController.TURN.X ){
//            if(countX <= countO){
//                isEnemyTurn = false;
//                turnText.setText("TURN: YOU");
//            }else{
//                isEnemyTurn = true;
//                turnText.setText("TURN: ENEMY");
//            }
//        }
//        if(turn == TicTacToeController.TURN.O){
//            if(countX > countO){
//                isEnemyTurn = false;
//                turnText.setText("TURN: YOU");
//            }else{
//                isEnemyTurn = true;
//                turnText.setText("TURN: ENEMY");
//            }
//        }
//
//        checkWinner();
    }


    private void checkWinner(){

//        char status = ticTacToe.findWinner();
//
//        switch (status){
//            case 'X':
//                if(turn == TicTacToeController.TURN.X){
//                    messageText.setText("You Win!");
//                }else{
//                    messageText.setText("You Lose!");
//                }
//                isDone = true;
//                turnText.setText("GAME OVER!");
//                break;
//            case 'O':
//                if(turn == TicTacToeController.TURN.O){
//                    messageText.setText("You Win!");
//                }else{
//                    messageText.setText("You Lose!");
//                }
//                isDone = true;
//                turnText.setText("GAME OVER!");
//                break;
//            case 'D':
//                messageText.setText("Draw! Good game :v");
//                isDone = true;
//                break;
//        }

    }

    int getIndexFromRowCol(int row ,int col){
        return (row * 8) + col;
    }

    public ImageView getImage(String imageUrl){
        Image xImage = new Image(getClass().getResource(imageUrl).toExternalForm());
        ImageView imageView = new ImageView(xImage);
        return imageView;
    }

    public ImageView getChessPieceImage(char c){
        switch (c){
            case 'a':
                return getImage("/images/chess/black_rook.png");
            case 'b':
                return getImage("/images/chess/black_knight.png");
            case 'c':
                return getImage("/images/chess/black_bishop.png");

            case 'i':
                return getImage("/images/chess/white_rook.png");
            case 'j':
                return getImage("/images/chess/white_knight.png");
            case 'k':
                return getImage("/images/chess/white_bishop.png");

            case 'd':
                return getImage("/images/chess/black_king.png");
            case 'e':
                return getImage("/images/chess/black_queen.png");

            case 'l':
                return getImage("/images/chess/white_king.png");
            case 'm':
                return getImage("/images/chess/white_queen.png");

            case 'q':
                return getImage("/images/chess/black_pawn.png");
            case 'r':
                return getImage("/images/chess/white_pawn.png");
        }
        return null;
    }

}

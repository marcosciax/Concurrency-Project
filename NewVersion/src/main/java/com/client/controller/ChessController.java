package com.client.controller;

import com.client.CDataService;
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
        X,O
    };
    TicTacToeController.TURN turn;
    boolean isEnemyTurn = false;

    TicTacToe ticTacToe;
    boolean isDone  = false;

    @FXML
    private Label vsText;
    @FXML
    private Label turnText;
    @FXML
    private Label messageText;

    @FXML
    private GridPane gridPane;

    public ChessController(String toUser, String playFirst, int roomId) {
        this.toUser = toUser;
        this.playFirst = playFirst;
        this.roomId = roomId;
    }

    @FXML
    public void initialize() throws IOException {
//        ticTacToe = new TicTacToe();
        vsText.setText("VS " + toUser);
        if(playFirst.equals(CDataService.getInstance().getUsername())){
            turnText.setText("TURN: YOU");
            turn = TicTacToeController.TURN.X;
            isEnemyTurn = false;
            messageText.setText("You are White. Move first");
        }
        else{
            turnText.setText("TURN: ENEMY");
            turn = TicTacToeController.TURN.O;
            isEnemyTurn = true;
            messageText.setText("You are Black");
        }

//        for(int row = 0; row < 3;row++){
//            for(int col = 0; col < 3;col++) {
//
//                HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
//                int finalRow = row;
//                int finalCol = col;
//                p.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent mouseEvent) {
//
//                        if(p.getChildren().size() > 0 || isEnemyTurn || isDone){
//                            return;
//                        }
//
//                        if(turn == TicTacToeController.TURN.X){
//                            p.getChildren().add( getXImage() );
//                            ticTacToe.add(finalRow, finalCol,'X' );
//                            NetworkService.getInstance().sendMessage(String.format("TICTACPLAY=%s-%s-%d-%d-%d-%c",
//                                    CDataService.getInstance().getUsername(),
//                                    toUser,
//                                    roomId,
//                                    finalRow,
//                                    finalCol,
//                                    'X'
//                            ));
//                        }
//                        else{
//                            p.getChildren().add(getOImage());
//                            ticTacToe.add(finalRow, finalCol,'O');
//                            NetworkService.getInstance().sendMessage(String.format("TICTACPLAY=%s-%s-%d-%d-%d-%c",
//                                    CDataService.getInstance().getUsername(),
//                                    toUser,
//                                    roomId,
//                                    finalRow,
//                                    finalCol,
//                                    'O'
//                            ));
//                        }
//
//
//                        isEnemyTurn = true;
//                        turnText.setText("TURN: ENEMY");
//                        checkWinner();
//                    }
//                });
//            }
//        }

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

    public void enemyHavePlay(int row ,int col,char val){
//        turnText.setText("TURN: YOU");
//        isEnemyTurn = false;
//        HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
//        ticTacToe.add(row, col,val );
//        if(val == 'X'){
//            p.getChildren().add( new King("/images/chess/black_king.png"));
//        }else{
//            p.getChildren().add( getOImage());
//        }
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

}

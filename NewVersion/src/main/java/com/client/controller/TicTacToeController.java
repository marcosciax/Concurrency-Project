package com.client.controller;

import com.client.CDataService;
import com.client.Model.TicTacToe;
import com.client.NetworkService;
import com.server.DataService;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;

public class TicTacToeController {
    String toUser;
    String playFirst;
    int roomId;

    enum TURN{
        X,O
    };
    TURN turn;
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

    public TicTacToeController(String toUser, String playFirst, int roomId) {
        this.toUser = toUser;
        this.playFirst = playFirst;
        this.roomId = roomId;
    }

    @FXML
    public void initialize() throws IOException {
//        messageText.setVisible(false);
        ticTacToe = new TicTacToe();
        vsText.setText("VS " + toUser);
        if(playFirst.equals(CDataService.getInstance().getUsername())){
            turnText.setText("TURN: YOU");
            turn = TURN.X;
            isEnemyTurn = false;
            messageText.setText("You are X. Play first");
        }
        else{
            turnText.setText("TURN: ENEMY");
            turn = TURN.O;
            isEnemyTurn = true;
            messageText.setText("You are O");
        }

        for(int row = 0; row < 3;row++){
            for(int col = 0; col < 3;col++) {

                HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
                int finalRow = row;
                int finalCol = col;
                p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                        if(p.getChildren().size() > 0 || isEnemyTurn || isDone){
                            return;
                        }

                        if(turn == TURN.X){
                            p.getChildren().add( getXImage() );
                            ticTacToe.add(finalRow, finalCol,'X' );
                            NetworkService.getInstance().sendMessage(String.format("TICTACPLAY=%s-%s-%d-%d-%d-%c",
                                            CDataService.getInstance().getUsername(),
                                            toUser,
                                            roomId,
                                            finalRow,
                                            finalCol,
                                            'X'
                                            ));
                        }
                        else{
                            p.getChildren().add(getOImage());
                            ticTacToe.add(finalRow, finalCol,'O');
                            NetworkService.getInstance().sendMessage(String.format("TICTACPLAY=%s-%s-%d-%d-%d-%c",
                                    CDataService.getInstance().getUsername(),
                                    toUser,
                                    roomId,
                                    finalRow,
                                    finalCol,
                                    'O'
                            ));
                        }


                        isEnemyTurn = true;
                        turnText.setText("TURN: ENEMY");
                        checkWinner();
                    }
                });
            }
        }

    }

    public void enemyHavePlay(int row ,int col,char val){
        turnText.setText("TURN: YOU");
        isEnemyTurn = false;
        HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
        ticTacToe.add(row, col,val );
        if(val == 'X'){
            p.getChildren().add( getXImage());
        }else{
            p.getChildren().add( getOImage());
        }
        checkWinner();
    }
    private void checkWinner(){

        char status = ticTacToe.findWinner();

        switch (status){
            case 'X':
                if(turn == TURN.X){
                    messageText.setText("You Win!");
                }else{
                    messageText.setText("You Lose!");
                }
                isDone = true;
                break;
            case 'O':
                if(turn == TURN.O){
                    messageText.setText("You Win!");
                }else{
                    messageText.setText("You Lose!");
                }
                isDone = true;
                break;
            case 'D':
                messageText.setText("Draw! Good game :v");
                isDone = true;
                break;
        }

    }

    ImageView getXImage(){
        Image xImage = new Image(getClass().getResource("/images/x.png").toExternalForm());
        ImageView xImageView = new ImageView(xImage);

        return xImageView;
    }
    ImageView getOImage(){
        Image oImage = new Image(getClass().getResource("/images/o.png").toExternalForm());
        ImageView oImageView = new ImageView(oImage);
        return oImageView;
    }

    int getIndexFromRowCol(int row ,int col){
       return (row * 3) + col;
    }

}

package com.client.controller;

import com.client.Model.TicTacToe;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

    enum TURN{
        X,O
    };
    TURN turn;

    TicTacToe ticTacToe;

    @FXML
    private GridPane gridPane;

    public TicTacToeController(String toUser) {
        this.toUser = toUser;
    }

    @FXML
    public void initialize() throws IOException {
        ticTacToe = new TicTacToe();

        turn = TURN.X;

        for(int row = 0; row < 3;row++){
            for(int col = 0; col < 3;col++) {

                HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
                int finalRow = row;
                int finalCol = col;
                p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                        if(p.getChildren().size() > 0){
                            return;
                        }
                        if(turn == TURN.X){
                            p.getChildren().add( getXImage() );
                            ticTacToe.add(finalRow, finalCol,'X' );
                        }
                        else{
                            p.getChildren().add(getOImage());
                            ticTacToe.add(finalRow, finalCol,'O');
                        }

                        // check winner
                        char winner = ticTacToe.findWinner();
                        System.out.println("winner: " + winner);

                        // new turn
                        if(turn == TURN.X){
                            turn = TURN.O;
                        }else{
                            turn = TURN.X;
                        }

                    }
                });
            }
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

package com.client.controller;

import com.client.CDataService;
import com.client.Model.Chess;
import com.client.Model.TicTacToe;
import com.client.Model.Vec2;
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
import java.util.ArrayList;
import java.util.List;

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
                        if(isEnemyTurn || isDone){
                            return;
                        }
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
                            resetColor();
                            p.setStyle(p.getStyle() +";-fx-background-color:#138076;-fx-border-color:#000");
                            selectedBox = p;
                            selectedRow = currentRow;
                            selectedCol = currentCol;

                            //highlight Valid move
                            highlightValidMove(selectedRow,selectedCol);
                            return;
                        }
                        if(selectedBox != null && turn == TURN.BLACK && chess.isBlackPiece(currentRow, currentCol)){
                            resetColor();
                            p.setStyle(p.getStyle() +";-fx-background-color:#138076;-fx-border-color:#000");
                            selectedBox = p;
                            selectedRow = currentRow;
                            selectedCol = currentCol;

                            //highlight Valid move
                            highlightValidMove(selectedRow,selectedCol);
                            return;
                        }

                        if(selectedBox == null){
                            resetColor();
                            p.setStyle(p.getStyle() +";-fx-background-color:#138076;-fx-border-color:#000");
                            selectedBox = p;
                            selectedRow = currentRow;
                            selectedCol = currentCol;

                            //highlight Valid move
                            highlightValidMove(selectedRow,selectedCol);

                        }
                        else{
                            // check valid move
                            List<Vec2> validMoves = findValidMoves(selectedRow,selectedCol);
                            boolean isValidMove = false;
                            for(Vec2 v : validMoves){
                                if(v.row == currentRow && v.col == currentCol){
                                    isValidMove = true;
                                }
                            }
                            if(!isValidMove){
                                return;
                            }

                            ImageView chessPieceImage = (ImageView) selectedBox.getChildren().get(0);

                            selectedBox.getChildren().clear();
                            p.getChildren().clear();
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

                            checkWinner();
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
        toBox.getChildren().clear();
        toBox.getChildren().add(chessPieceImage);

        chess.move(row,col,toRow,toCol);

        checkWinner();
    }


    private void resetColor(){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8;col++){
                HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
                p.setStyle(p.getStyle().replace(";-fx-background-color:#138076;-fx-border-color:#000",""));
            }
        }
    }

    public void loadGame(Chess chess,String playFirst,String waitFor){
        this.chess = chess;

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8;col++) {
                HBox p = (HBox) gridPane.getChildren().get(getIndexFromRowCol(row, col));
                ImageView chessPieceImage = getChessPieceImage(chess.get(row,col));
                p.getChildren().clear();
                if(chessPieceImage == null){
                    continue;
                }
                chessPieceImage.setFitWidth(40);
                chessPieceImage.setFitHeight(40);
                p.getChildren().add(chessPieceImage);
            }
        }


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

        if(waitFor.equals(CDataService.getInstance().getUsername())){
            isEnemyTurn = false;
            turnText.setText("TURN: YOU");
        }
        else{
            isEnemyTurn = true;
            turnText.setText("TURN: ENEMY");
        }

    }


    private void checkWinner(){

        char status = chess.findWinner();

        switch (status){
            case 'B':
                if(turn == TURN.BLACK){
                    messageText.setText("You Win!");
                }else{
                    messageText.setText("You Lose!");
                }
                isDone = true;
                turnText.setText("GAME OVER!");
                break;
            case 'W':
                if(turn == TURN.WHITE){
                    messageText.setText("You Win!");
                }else{
                    messageText.setText("You Lose!");
                }
                isDone = true;
                turnText.setText("GAME OVER!");
                break;
        }

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

    void highlightValidMove(int row ,int col){
        List<Vec2> valids = null;
        switch ( chess.get(row,col) ){
            case 'a':
                valids = findRookValidMoves(row,col);
                break;
            case 'b':
                valids = findKnightValidMoves(row,col);
                break;
            case 'c':
                valids = findBiShopValidMoves(row,col);
                break;

            case 'i':
                valids = findRookValidMoves(row,col);
                break;
            case 'j':
                valids = findKnightValidMoves(row,col);
                break;
            case 'k':
                valids = findBiShopValidMoves(row,col);
                break;

            case 'd':
                valids = findKingValidMoves(row,col);
                break;
            case 'e':
                valids = findQueenValidMoves(row,col);
                break;
//
            case 'l':
                valids = findKingValidMoves(row,col);
                break;
            case 'm':
                valids = findQueenValidMoves(row,col);
                break;
//
            case 'q':
                valids = findPawnValidMoves(row,col);
                break;
            case 'r':
                valids = findPawnValidMoves(row,col);
                break;
        }

        if(valids != null){
            for(Vec2 v : valids){
                highLightBox(v.row,v.col);
            }
        }
    }

    List<Vec2> findValidMoves(int row ,int col){
        switch ( chess.get(row,col) ){
            case 'a':
                return findRookValidMoves(row,col);
            case 'b':
                return findKnightValidMoves(row,col);
            case 'c':
                return findBiShopValidMoves(row,col);

            case 'i':
                return findRookValidMoves(row,col);
            case 'j':
                return findKnightValidMoves(row,col);
            case 'k':
                return findBiShopValidMoves(row,col);
//
            case 'd':
                return findKingValidMoves(row,col);
            case 'e':
                return findQueenValidMoves(row,col);
//
            case 'l':
                return findKingValidMoves(row,col);
            case 'm':
                return findQueenValidMoves(row,col);
//
            case 'q':
                return findPawnValidMoves(row,col);
            case 'r':
                return findPawnValidMoves(row,col);
        }
        return null;
    }

    HBox getBoxAt(int row,int col){
        HBox p = (HBox) gridPane.getChildren().get( getIndexFromRowCol(row,col) );
        return p;
    }

    boolean emptyBox(int row,int col){
        return getBoxAt(row,col).getChildren().isEmpty();
    }

    void highLightBox(int row,int col){
        getBoxAt(row,col).setStyle( getBoxAt(row,col).getStyle() +";-fx-background-color:#138076;-fx-border-color:#000");
    }

    //////////////////////

    List<Vec2> findKingValidMoves(int row,int col){
        List<Vec2> valids = new ArrayList<>();
        valids.add(new Vec2(row,col));

        int[] dirRow = new int[]{ 1,-1,1,-1,1,-1,0,0 };
        int[] dirCol = new int[]{ -1,-1,1,1,0,0,1,-1 };
        int[] blocks = new int[]{0,0,0,0,0,0,0,0};

        int length = 1;
        for(int i = 0; i < dirRow.length;i++){
            int newRow = row + dirRow[i] * length;
            int newCol = col + dirCol[i] * length;

            if(blocks[i] == 2){
                continue;
            }

            if(newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <=7  ){
                if( emptyBox(newRow,newCol) ){
                    valids.add(new Vec2(newRow,newCol));
                }else{

                    if(turn == TURN.BLACK && chess.isBlackPiece(newRow,newCol)){
                        blocks[i] = 2;
                    }
                    else if(turn == TURN.WHITE && chess.isWhitePiece(newRow,newCol)){
                        blocks[i] = 2;
                    }
                    else{
                        blocks[i] = 2;
                        valids.add(new Vec2(newRow,newCol));
                    }
                }
            }else{
                blocks[i] = 2;
            }
        }
        return valids;
    }


    List<Vec2> findBiShopValidMoves(int row,int col){
        List<Vec2> valids = new ArrayList<>();
        valids.add(new Vec2(row,col));

        int[] dirRow = new int[]{ 1,-1,1,-1 };
        int[] dirCol = new int[]{ -1,-1,1,1};
        int[] blocks = new int[]{0,0,0,0};

        int length = 1;
        while (!allDirBlocked(blocks)){

            for(int i = 0; i < dirRow.length;i++){
                int newRow = row + dirRow[i] * length;
                int newCol = col + dirCol[i] * length;

                if(blocks[i] == 2){
                    continue;
                }

                if(newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <=7  ){
                    if( emptyBox(newRow,newCol) ){
                        valids.add(new Vec2(newRow,newCol));
                    }else{

                        if(turn == TURN.BLACK && chess.isBlackPiece(newRow,newCol)){
                            blocks[i] = 2;
                        }
                        else if(turn == TURN.WHITE && chess.isWhitePiece(newRow,newCol)){
                            blocks[i] = 2;
                        }
                        else{
                            blocks[i] = 2;
                            valids.add(new Vec2(newRow,newCol));
                        }
                    }
                }else{
                    blocks[i] = 2;
                }
            }
            length++;

        }
        return valids;
    }



    List<Vec2> findRookValidMoves(int row,int col){
        List<Vec2> valids = new ArrayList<>();
        valids.add(new Vec2(row,col));

        int[] dirRow = new int[]{ 1,-1,0,0 };
        int[] dirCol = new int[]{ 0,0,1,-1 };
        int[] blocks = new int[]{0,0,0,0};

        int length = 1;
        while (!allDirBlocked(blocks)){

            for(int i = 0; i < dirRow.length;i++){
                int newRow = row + dirRow[i] * length;
                int newCol = col + dirCol[i] * length;

                if(blocks[i] == 2){
                    continue;
                }

                if(newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <=7  ){
                    if( emptyBox(newRow,newCol) ){
                        valids.add(new Vec2(newRow,newCol));
                    }else{

                        if(turn == TURN.BLACK && chess.isBlackPiece(newRow,newCol)){
                            blocks[i] = 2;
                        }
                        else if(turn == TURN.WHITE && chess.isWhitePiece(newRow,newCol)){
                            blocks[i] = 2;
                        }
                        else{
                            blocks[i] = 2;
                            valids.add(new Vec2(newRow,newCol));
                        }
                    }
                }else{
                    blocks[i] = 2;
                }
            }
            length++;

        }
        return valids;
    }

    List<Vec2> findQueenValidMoves(int row,int col){
        List<Vec2> valids = new ArrayList<>();
        valids.add(new Vec2(row,col));

        int[] dirRow = new int[]{ 1,-1,1,-1,1,-1,0,0 };
        int[] dirCol = new int[]{ -1,-1,1,1,0,0,1,-1 };
        int[] blocks = new int[]{0,0,0,0,0,0,0,0};

        int length = 1;
        while (!allDirBlocked(blocks)){

            for(int i = 0; i < dirRow.length;i++){
                int newRow = row + dirRow[i] * length;
                int newCol = col + dirCol[i] * length;

                if(blocks[i] == 2){
                    continue;
                }

                if(newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <=7  ){
                    if( emptyBox(newRow,newCol) ){
                        valids.add(new Vec2(newRow,newCol));
                    }else{

                        if(turn == TURN.BLACK && chess.isBlackPiece(newRow,newCol)){
                            blocks[i] = 2;
                        }
                        else if(turn == TURN.WHITE && chess.isWhitePiece(newRow,newCol)){
                            blocks[i] = 2;
                        }
                        else{
                            blocks[i] = 2;
                            valids.add(new Vec2(newRow,newCol));
                        }
                    }
                }else{
                    blocks[i] = 2;
                }
            }
            length++;

        }
        return valids;
    }

    boolean allDirBlocked(int[] blocks){
        for(int b : blocks){
            if(b != 2){
                return false;
            }
        }
        return true;
    }

    List<Vec2> findKnightValidMoves(int row,int col){
        List<Vec2> valids = new ArrayList<>();
        valids.add(new Vec2(row,col));

        List<Vec2> possiblePos = new ArrayList<>();
        possiblePos.add(new Vec2(row-2,col-1));
        possiblePos.add(new Vec2(row-2,col+1));
        possiblePos.add(new Vec2(row+2,col-1));
        possiblePos.add(new Vec2( row+2,col+1));
        possiblePos.add(new Vec2( row-1,col-2));
        possiblePos.add(new Vec2( row+1,col-2));
        possiblePos.add(new Vec2(  row-1,col+2));
        possiblePos.add(new Vec2(  row+1,col+2));

        for(Vec2 p : possiblePos){

            if(p.row >= 0 && p.row <=7 && p.col >= 0 && p.col <= 7){

                if(turn == TURN.BLACK && chess.isBlackPiece(p.row,p.col)){
                    continue;
                }
                if(turn == TURN.WHITE && chess.isWhitePiece(p.row,p.col)){
                    continue;
                }

                valids.add(p);
            }
        }

        return valids;
    }

    List<Vec2> findPawnValidMoves(int row,int col){
        List<Vec2> valids = new ArrayList<>();
        valids.add(new Vec2(row,col));

        if(turn == TURN.BLACK && row == 1){
            valids.add(new Vec2(row+1,col));
            if(emptyBox(row+2,col)){
                valids.add(new Vec2(row + 2,col));
            }
        }
        if(turn == TURN.WHITE && row == 6){
            valids.add(new Vec2(row-1,col));
            if(emptyBox(row-2,col)){
                valids.add(new Vec2(row-2,col));
            }
        }
        if(turn == TURN.BLACK && emptyBox(row+1,col)){
            valids.add(new Vec2(row+1,col));
        }
        if(turn == TURN.WHITE && emptyBox(row-1,col)){
            valids.add(new Vec2(row-1,col));
        }

        if(turn == TURN.BLACK  && col+1 >= 0 && col+1<=7 &&  chess.isWhitePiece(row+1,col+1) && !emptyBox(row+1,col+1)){
            valids.add(new Vec2(row+1,col+1));
        }
        if(turn == TURN.BLACK && col-1 >= 0 && col-1<=7 && chess.isWhitePiece(row+1,col-1) && !emptyBox(row+1,col-1) ){
            valids.add(new Vec2(row+1,col-1));
        }

        if(turn == TURN.WHITE  && col+1 >= 0 && col+1<=7 && chess.isBlackPiece(row-1,col+1) && !emptyBox(row-1,col+1)){
            valids.add(new Vec2(row-1,col+1));
        }
        if(turn == TURN.WHITE && col-1 >= 0 && col-1<=7 && chess.isBlackPiece(row-1,col-1) && !emptyBox(row-1,col-1) ){
            valids.add(new Vec2(row-1,col-1));
        }

        return valids;
    }

}

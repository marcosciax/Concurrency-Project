package ChessGame.Controller;

import ChessGame.Models.Pieces.BlackOnes.*;
import ChessGame.Models.Pieces.Piece;
import ChessGame.Models.Pieces.WhiteOnes.*;
import ChessGame.Models.Spot;
import javafx.scene.layout.Pane;

import javax.lang.model.type.NullType;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Responsible for movements of pieces
 */
public class MovePiece {

    private final int tile_size=135;
    double mouseAnchorX;
    double mouseAnchorY;
    double xloc;
    double yloc;
    public static boolean gameOver =false;


    /**
     * Redirect Pieces to their respective Functions
     * @param piece for movement of that piece
     * @param board in which piece is going to move
     */
    public void move(Piece piece, Pane board){
        if(piece instanceof Pawn || piece instanceof W_Pawn)
            pawnMovement(piece,board);
        if(piece instanceof Knight || piece instanceof W_Knight)
            knightMovement(piece,board);
        if(piece instanceof King || piece instanceof W_King)
            kingMovement(piece,board);
        if(piece instanceof Bishop || piece instanceof W_Bishop)
            bishopMovement(piece, board);
        if(piece instanceof Rook || piece instanceof W_Rook)
            rookMovement(piece,board);
        if(piece instanceof Queen || piece instanceof W_Queen)
            queenMovement(piece, board);

    }
    public void check(Pane board , Piece piece){
        if(piece instanceof King)
            if(CheckWin.CheckKing(board,(King)piece)) {
                gameOver=true;
                System.out.println("White Wins");
            }
        if(piece instanceof W_King)
            if(CheckWin.CheckW_King(board,(W_King) piece)) {
                gameOver=true;
                System.out.println("Black wins");
            }
    }

    /**
     * Responsible for Movement of Queen
     * @param piece for movement of that piece
     * @param board in which piece is going to move
     */
    public void queenMovement(Piece piece, Pane board){
        /**
         * When Mouse is Pressed Takes the Initial position of Piece and store it in xloc and yloc
         */
        piece.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
            xloc = mouseEvent.getSceneX();
            yloc = mouseEvent.getSceneY();
        });

        /**
         * Responsible for Dragging Pieces as it changes the Position of Piece as the Mouse is moving
         */
        piece.setOnMouseDragged(mouseEvent -> {
            piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

        /**
         * Responsible for Landing at a new Spot
         * Checks if the new Spot is Compatible for the movement or not
         */
        piece.setOnMouseReleased(mouseEvent -> {
            double diffX= mouseEvent.getSceneX()-xloc;
            double diffY= mouseEvent.getSceneY()-yloc;

            double lowerlimit=50,higherlimit=170;
            boolean notRightPlace=false;
            boolean movedRight=false,movedLeft=false,movedUp=false,movedDown=false,movedUpRight=false,movedUpLeft=false,movedDownRight=false,movedDownLeft=false;

            for(int i=1 ; i < 8 ; i++) {
                notRightPlace=false;
                if(diffX>lowerlimit && diffX<higherlimit && diffY < lowerlimit && diffY > (lowerlimit*-1)){ // move right
                    piece.setLayoutX(xloc+(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc-mouseAnchorY);
                    movedRight=true;
                    break;
                }else if(diffX<(lowerlimit*-1) && diffX > (higherlimit*-1) && diffY < lowerlimit && diffY > (lowerlimit*-1)){ // move left
                    piece.setLayoutX(xloc-(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc-mouseAnchorY);
                    movedLeft=true;
                    break;
                }else if(diffY > lowerlimit && diffY < higherlimit && diffX < lowerlimit && diffX > (lowerlimit*-1)){ // move down
                    piece.setLayoutX(xloc-mouseAnchorX);
                    piece.setLayoutY(yloc+(tile_size*i)-mouseAnchorY);
                    movedDown=true;
                    break;
                }else if(diffY < (lowerlimit*-1) && diffY > (higherlimit*-1) && diffX < lowerlimit && diffX > (lowerlimit*-1)){ // move up
                    piece.setLayoutX(xloc-mouseAnchorX);
                    piece.setLayoutY(yloc-(tile_size*i)-mouseAnchorY);
                    movedUp=true;
                    break;
                }else if(diffX>lowerlimit && diffX < higherlimit && diffX > (lowerlimit*-1) && diffY>lowerlimit && diffY < higherlimit && diffY > (lowerlimit*-1)){ // down and right
                    piece.setLayoutX(xloc+(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc+(tile_size*i)-mouseAnchorY);
                    movedDownRight=true;
                    break;
                }else if(diffX>lowerlimit && diffX < higherlimit && diffX > (lowerlimit*-1) && diffY<(lowerlimit*-1) && diffY > (higherlimit*-1) && diffY < lowerlimit){ // up and right
                    piece.setLayoutX(xloc+(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc-(tile_size*i)-mouseAnchorY);
                    movedUpRight=true;
                    break;
                }else if(diffX<(lowerlimit*-1) && diffX > (higherlimit*-1) && diffX < lowerlimit && diffY>lowerlimit && diffY < higherlimit && diffY > (lowerlimit*-1)){ // down and left
                    piece.setLayoutX(xloc-(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc+(tile_size*i)-mouseAnchorY);
                    movedDownLeft=true;
                    break;
                }else if(diffX<(lowerlimit*-1) && diffX > (higherlimit*-1) && diffX < lowerlimit && diffY<(lowerlimit*-1) && diffY > (higherlimit*-1) && diffY < lowerlimit){ // up and left
                    piece.setLayoutX(xloc-(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc-(tile_size*i)-mouseAnchorY);
                    movedUpLeft=true;
                    break;
                }else{
                    notRightPlace=true;
                }

                lowerlimit = higherlimit;
                higherlimit += tile_size;
            }
            if(notRightPlace){
                piece.setLayoutX(xloc - mouseAnchorX);
                piece.setLayoutY(yloc - mouseAnchorY);
            }


            for(int i=0 ; i < 8 ; i++){
                for(int j=0 ; j < 8 ; j++){
                    if(BoardController.spots[i][j].getLayoutX()==piece.getLayoutX() && BoardController.spots[i][j].getLayoutY()==piece.getLayoutY()){
                        if(BoardController.spots[i][j].getPiece()!=null)
                            if(piece.isWhite()==BoardController.spots[i][j].getPiece().isWhite()){
                            piece.setLayoutX(xloc - mouseAnchorX);
                            piece.setLayoutY(yloc - mouseAnchorY);
                            return;
                            }
                        if(!BoardController.spots[i][j].isEmpty() && BoardController.spots[i][j].getPiece()!=piece){
                            board.getChildren().remove(BoardController.spots[i][j].getPiece());
                        }else if(BoardController.spots[i][j].isEmpty()){
                            BoardController.spots[i][j].setEmpty(false);
                        }
                        piece.getSpot().setEmpty(true);
                        try {
                            piece.getSpot().setPiece(null);
                        }catch (NullPointerException e){
                            System.out.println();
                        }
                        BoardController.spots[i][j].setPiece(piece);
                    }
                }
            }

            check(board,BoardController.whitePieces[7]);
            check(board,BoardController.blackPieces[7]);
        });
    }
    /**
     * Responsible for Movement of Rook
     * @param piece for movement of that piece
     * @param board in which piece is going to move
     */
    public void rookMovement(Piece piece, Pane board){
        piece.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
            xloc = mouseEvent.getSceneX();
            yloc = mouseEvent.getSceneY();
        });

        piece.setOnMouseDragged(mouseEvent -> {
            piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

        piece.setOnMouseReleased(mouseEvent -> {
            double diffX= mouseEvent.getSceneX()-xloc;
            double diffY= mouseEvent.getSceneY()-yloc;

            double lowerlimit=50,higherlimit=170;
            boolean notRightPlace=false;

            for(int i=1 ; i < 8 ; i++) {
                notRightPlace=false;

                if(diffX>lowerlimit && diffX<higherlimit && diffY < lowerlimit && diffY > (lowerlimit*-1)){ // move right
                    piece.setLayoutX(xloc+(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc-mouseAnchorY);
                    break;
                }else if(diffX<(lowerlimit*-1) && diffX > (higherlimit*-1) && diffY < lowerlimit && diffY > (lowerlimit*-1)){ // move left
                    piece.setLayoutX(xloc-(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc-mouseAnchorY);
                    break;
                }else if(diffY > lowerlimit && diffY < higherlimit && diffX < lowerlimit && diffX > (lowerlimit*-1)){ // move down
                    piece.setLayoutX(xloc-mouseAnchorX);
                    piece.setLayoutY(yloc+(tile_size*i)-mouseAnchorY);
                    break;
                }else if(diffY < (lowerlimit*-1) && diffY > (higherlimit*-1) && diffX < lowerlimit && diffX > (lowerlimit*-1)){ // move up
                    piece.setLayoutX(xloc-mouseAnchorX);
                    piece.setLayoutY(yloc-(tile_size*i)-mouseAnchorY);
                    break;
                }else {
                    notRightPlace=true;
                }
                lowerlimit = higherlimit;
                higherlimit += tile_size;
            }
            if(notRightPlace){
                piece.setLayoutX(xloc - mouseAnchorX);
                piece.setLayoutY(yloc - mouseAnchorY);
            }

            for(int i=0 ; i < 8 ; i++){
                for(int j=0 ; j < 8 ; j++){
                    if(BoardController.spots[i][j].getLayoutX()==piece.getLayoutX() && BoardController.spots[i][j].getLayoutY()==piece.getLayoutY()){
                        if(BoardController.spots[i][j].getPiece()!=null)
                            if(piece.isWhite()==BoardController.spots[i][j].getPiece().isWhite()){
                                piece.setLayoutX(xloc - mouseAnchorX);
                                piece.setLayoutY(yloc - mouseAnchorY);
                                return;
                            }
                        if(!BoardController.spots[i][j].isEmpty() && BoardController.spots[i][j].getPiece()!=piece){
                            board.getChildren().remove(BoardController.spots[i][j].getPiece());
                        }else if(BoardController.spots[i][j].isEmpty()){
                            BoardController.spots[i][j].setEmpty(false);
                        }
                        piece.getSpot().setEmpty(true);
                        try {
                            piece.getSpot().setPiece(null);
                        }catch (NullPointerException e){
                            System.out.println();
                        }
                        BoardController.spots[i][j].setPiece(piece);
                    }
                }
            }
            check(board,BoardController.whitePieces[7]);
            check(board,BoardController.blackPieces[7]);
        });
    }
    /**
     * Responsible for Movement of Bishop
     * @param piece for movement of that piece
     * @param board in which piece is going to move
     */
    public void bishopMovement(Piece piece, Pane board){
        piece.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
            xloc = mouseEvent.getSceneX();
            yloc = mouseEvent.getSceneY();
        });

        piece.setOnMouseDragged(mouseEvent -> {
            piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

        piece.setOnMouseReleased(mouseEvent -> {
            double diffX= mouseEvent.getSceneX()-xloc;
            double diffY= mouseEvent.getSceneY()-yloc;

            int lowerlimit=50,higherlimit=170;
            boolean notRightPlace=false;

            for(int i=1 ; i < 8 ; i++) {
                notRightPlace=false;

                if(diffX>lowerlimit && diffX < higherlimit && diffX > (lowerlimit*-1) && diffY>lowerlimit && diffY < higherlimit && diffY > (lowerlimit*-1)){ // down and right
                    piece.setLayoutX(xloc+(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc+(tile_size*i)-mouseAnchorY);
                    break;
                }else if(diffX>lowerlimit && diffX < higherlimit && diffX > (lowerlimit*-1) && diffY<(lowerlimit*-1) && diffY > (higherlimit*-1) && diffY < lowerlimit){ // up and right
                    piece.setLayoutX(xloc+(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc-(tile_size*i)-mouseAnchorY);
                    break;
                }else if(diffX<(lowerlimit*-1) && diffX > (higherlimit*-1) && diffX < lowerlimit && diffY>lowerlimit && diffY < higherlimit && diffY > (lowerlimit*-1)){ // down and left
                    piece.setLayoutX(xloc-(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc+(tile_size*i)-mouseAnchorY);
                    break;
                }else if(diffX<(lowerlimit*-1) && diffX > (higherlimit*-1) && diffX < lowerlimit && diffY<(lowerlimit*-1) && diffY > (higherlimit*-1) && diffY < lowerlimit){ // up and left
                    piece.setLayoutX(xloc-(tile_size*i)-mouseAnchorX);
                    piece.setLayoutY(yloc-(tile_size*i)-mouseAnchorY);
                    break;
                }else {
                    notRightPlace=true;
                }
                lowerlimit = higherlimit;
                higherlimit += tile_size ;
            }
            if(notRightPlace){
                piece.setLayoutX(xloc - mouseAnchorX);
                piece.setLayoutY(yloc - mouseAnchorY);
            }

            for(int i=0 ; i < 8 ; i++){
                for(int j=0 ; j < 8 ; j++){
                    if(BoardController.spots[i][j].getLayoutX()==piece.getLayoutX() && BoardController.spots[i][j].getLayoutY()==piece.getLayoutY()){
                        if(BoardController.spots[i][j].getPiece()!=null)
                            if(piece.isWhite()==BoardController.spots[i][j].getPiece().isWhite()){
                                piece.setLayoutX(xloc - mouseAnchorX);
                                piece.setLayoutY(yloc - mouseAnchorY);
                                return;
                            }
                        if(!BoardController.spots[i][j].isEmpty() && BoardController.spots[i][j].getPiece()!=piece){
                            board.getChildren().remove(BoardController.spots[i][j].getPiece());
                        }else if(BoardController.spots[i][j].isEmpty()){
                            BoardController.spots[i][j].setEmpty(false);
                        }
                        piece.getSpot().setEmpty(true);
                        try {
                            piece.getSpot().setPiece(null);
                        }catch (NullPointerException e){
                            System.out.println();
                        }
                        BoardController.spots[i][j].setPiece(piece);
                    }
                }
            }
            check(board,BoardController.whitePieces[7]);
            check(board,BoardController.blackPieces[7]);
        });
    }

    /**
     * Responsible for Movement of King
     * @param piece for movement of that piece
     * @param board in which piece is going to move
     */
    public void kingMovement(Piece piece, Pane board){
        piece.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
            xloc = mouseEvent.getSceneX();
            yloc = mouseEvent.getSceneY();
        });

        piece.setOnMouseDragged(mouseEvent -> {
            piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

        piece.setOnMouseReleased(mouseEvent -> {
            double diffX= mouseEvent.getSceneX()-xloc;
            double diffY= mouseEvent.getSceneY()-yloc;

            int lowerlimit=50,higherlimit=170;

            if(diffX>lowerlimit && diffX<higherlimit && diffY < lowerlimit && diffY > (lowerlimit*-1)){ // move right
                piece.setLayoutX(xloc+tile_size-mouseAnchorX);
                piece.setLayoutY(yloc-mouseAnchorY);
            }else if(diffX<(lowerlimit*-1) && diffX > (higherlimit*-1) && diffY < lowerlimit && diffY > (lowerlimit*-1)){ // move left
                piece.setLayoutX(xloc-tile_size-mouseAnchorX);
                piece.setLayoutY(yloc-mouseAnchorY);
            }else if(diffY > lowerlimit && diffY < higherlimit && diffX < lowerlimit && diffX > (lowerlimit*-1)){ // move down
                piece.setLayoutX(xloc-mouseAnchorX);
                piece.setLayoutY(yloc+tile_size-mouseAnchorY);
            }else if(diffY < (lowerlimit*-1) && diffY > (higherlimit*-1) && diffX < lowerlimit && diffX > (lowerlimit*-1)){ // move up
                piece.setLayoutX(xloc-mouseAnchorX);
                piece.setLayoutY(yloc-tile_size-mouseAnchorY);
            }else if(diffX>lowerlimit && diffX < higherlimit && diffX > (lowerlimit*-1) && diffY>lowerlimit && diffY < higherlimit && diffY > (lowerlimit*-1)){ // down and right
                piece.setLayoutX(xloc+tile_size-mouseAnchorX);
                piece.setLayoutY(yloc+tile_size-mouseAnchorY);
            }else if(diffX>lowerlimit && diffX < higherlimit && diffX > (lowerlimit*-1) && diffY<(lowerlimit*-1) && diffY > (higherlimit*-1) && diffY < lowerlimit){ // up and right
                piece.setLayoutX(xloc+tile_size-mouseAnchorX);
                piece.setLayoutY(yloc-tile_size-mouseAnchorY);
            }else if(diffX<(lowerlimit*-1) && diffX > (higherlimit*-1) && diffX < lowerlimit && diffY>lowerlimit && diffY < higherlimit && diffY > (lowerlimit*-1)){ // down and left
                piece.setLayoutX(xloc-tile_size-mouseAnchorX);
                piece.setLayoutY(yloc+tile_size-mouseAnchorY);
            }else if(diffX<(lowerlimit*-1) && diffX > (higherlimit*-1) && diffX < lowerlimit && diffY<(lowerlimit*-1) && diffY > (higherlimit*-1) && diffY < lowerlimit){ // up and left
                piece.setLayoutX(xloc-tile_size-mouseAnchorX);
                piece.setLayoutY(yloc-tile_size-mouseAnchorY);
            }
            else{
                piece.setLayoutX(xloc-mouseAnchorX);
                piece.setLayoutY(yloc-mouseAnchorY);
            }

            for(int i=0 ; i < 8 ; i++){
                for(int j=0 ; j < 8 ; j++){
                    if(BoardController.spots[i][j].getLayoutX()==piece.getLayoutX() && BoardController.spots[i][j].getLayoutY()==piece.getLayoutY()){
                        if(BoardController.spots[i][j].getPiece()!=null)
                            if(piece.isWhite()==BoardController.spots[i][j].getPiece().isWhite()){
                                piece.setLayoutX(xloc - mouseAnchorX);
                                piece.setLayoutY(yloc - mouseAnchorY);
                                return;
                            }
                        if(!BoardController.spots[i][j].isEmpty() && BoardController.spots[i][j].getPiece()!=piece){
                            board.getChildren().remove(BoardController.spots[i][j].getPiece());
                        }else if(BoardController.spots[i][j].isEmpty()){
                            BoardController.spots[i][j].setEmpty(false);
                        }
                        piece.getSpot().setEmpty(true);
                        try {
                            piece.getSpot().setPiece(null);
                        }catch (NullPointerException e){
                            System.out.println();
                        }
                        BoardController.spots[i][j].setPiece(piece);
                    }
                }
            }
            check(board,BoardController.whitePieces[7]);
            check(board,BoardController.blackPieces[7]);
        });
    }

    /**
     * Responsible for Movement of Knight
     * @param piece for movement of that piece
     * @param board in which piece is going to move
     */
    public void knightMovement(Piece piece, Pane board){
        piece.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
            xloc = mouseEvent.getSceneX();
            yloc = mouseEvent.getSceneY();
        });

        piece.setOnMouseDragged(mouseEvent -> {
            piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

        piece.setOnMouseReleased(mouseEvent -> {
            double diffX= mouseEvent.getSceneX()-xloc;
            double diffY= mouseEvent.getSceneY()-yloc;

            int lowerlimit=50,higherlimit=170;

            if(diffX > lowerlimit && diffX < higherlimit && diffY < (higherlimit+tile_size) && diffY > higherlimit){
                piece.setLayoutX(xloc + tile_size - mouseAnchorX);
                piece.setLayoutY(yloc+ (tile_size*2)  -mouseAnchorY);
            }else if(diffX < (lowerlimit*-1) && diffX > (higherlimit*-1) && diffY < (higherlimit+tile_size) && diffY > higherlimit){
                piece.setLayoutX(xloc - tile_size - mouseAnchorX);
                piece.setLayoutY(yloc + (tile_size*2) - mouseAnchorY);
            }else if(diffX > lowerlimit && diffX < higherlimit && diffY > ((higherlimit+tile_size)*-1) && diffY < (higherlimit*-1)){
                piece.setLayoutX(xloc + tile_size - mouseAnchorX);
                piece.setLayoutY(yloc - (tile_size*2) - mouseAnchorY);
            }else if(diffX < (lowerlimit*-1) && diffX > (higherlimit*-1) && diffY > ((higherlimit+tile_size)*-1) && diffY < (higherlimit*-1)){
                piece.setLayoutX(xloc - tile_size - mouseAnchorX);
                piece.setLayoutY(yloc - (tile_size*2) - mouseAnchorY);
            }else if(diffX > higherlimit && diffX < (higherlimit+tile_size) && diffY < higherlimit && diffY > lowerlimit){
                piece.setLayoutX(xloc + (tile_size*2) - mouseAnchorX);
                piece.setLayoutY(yloc + tile_size - mouseAnchorY);
            }else if(diffX > higherlimit && diffX < (higherlimit+tile_size) && diffY > (higherlimit*-1) && diffY < (lowerlimit*-1)){
                piece.setLayoutX(xloc + (tile_size*2) - mouseAnchorX);
                piece.setLayoutY(yloc - tile_size - mouseAnchorY);
            }else if(diffX < (higherlimit*-1) && diffX > ((higherlimit+tile_size)*-1) && diffY < higherlimit && diffY > lowerlimit){
                piece.setLayoutX(xloc - (tile_size*2) - mouseAnchorX);
                piece.setLayoutY(yloc + tile_size - mouseAnchorY);
            }else if(diffX < (higherlimit*-1) && diffX > ((higherlimit+tile_size)*-1) && diffY > (higherlimit*-1) && diffY < (lowerlimit*-1)){
                piece.setLayoutX(xloc - (tile_size*2) - mouseAnchorX);
                piece.setLayoutY(yloc - tile_size - mouseAnchorY);
            }
            else{
                piece.setLayoutX(xloc - mouseAnchorX);
                piece.setLayoutY(yloc - mouseAnchorY);
            }

            for(int i=0 ; i < 8 ; i++){
                for(int j=0 ; j < 8 ; j++){
                    if(BoardController.spots[i][j].getLayoutX()==piece.getLayoutX() && BoardController.spots[i][j].getLayoutY()==piece.getLayoutY()){
                        if(BoardController.spots[i][j].getPiece()!=null)
                            if(piece.isWhite()==BoardController.spots[i][j].getPiece().isWhite()){
                                piece.setLayoutX(xloc - mouseAnchorX);
                                piece.setLayoutY(yloc - mouseAnchorY);
                                return;
                            }
                        if(!BoardController.spots[i][j].isEmpty() && BoardController.spots[i][j].getPiece()!=piece){
                            board.getChildren().remove(BoardController.spots[i][j].getPiece());
                        }else if(BoardController.spots[i][j].isEmpty()){
                            BoardController.spots[i][j].setEmpty(false);
                        }
                        piece.getSpot().setEmpty(true);
                        try {
                            piece.getSpot().setPiece(null);
                        }catch (NullPointerException e){
                            System.out.println();
                        }
                        BoardController.spots[i][j].setPiece(piece);
                    }
                }
            }
            check(board,BoardController.whitePieces[7]);
            check(board,BoardController.blackPieces[7]);
        });
    }
    /**
     * Responsible for Movement of Pawn
     * @param piece for movement of that piece
     * @param board in which piece is going to move
     */
    public void pawnMovement(Piece piece, Pane board){

        AtomicBoolean canMoveDiagonal_L= new AtomicBoolean(false);
        AtomicBoolean canMoveDiagonal_R= new AtomicBoolean(false);


        piece.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
            xloc = mouseEvent.getSceneX();
            yloc = mouseEvent.getSceneY();
        });

        piece.setOnMouseDragged(mouseEvent -> {
            piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

        piece.setOnMouseReleased(mouseEvent -> {
            double diffX= mouseEvent.getSceneX()-xloc;
            double diffY= mouseEvent.getSceneY()-yloc;

            int lowerlimit=50,higherlimit=170;

            piece.setLayoutX(xloc-mouseAnchorX);
            if(piece.isWhite()) {
                if(piece.getRow_spot_location()==6){
                    boolean notRightPlace=false;
                    for(int i=1 ; i < 3  ; i++){
                        notRightPlace=false;
                        if (diffY < (lowerlimit * -1) && diffY > (higherlimit * -1) && diffX < lowerlimit && diffX > (lowerlimit * -1)) { // move up
                            piece.setLayoutY(yloc - (tile_size*i) - mouseAnchorY);
                            break;
                        } else {
                            notRightPlace=true;
                        }
                        lowerlimit=higherlimit;
                        higherlimit+=tile_size;
                    }
                    if(notRightPlace)
                        piece.setLayoutY(yloc - mouseAnchorY);
                }else {
                    if (diffY < (lowerlimit * -1) && diffY > (higherlimit * -1) && diffX < lowerlimit && diffX > (lowerlimit * -1)) { // move up
                        piece.setLayoutY(yloc - tile_size - mouseAnchorY);
                    } else {
                        piece.setLayoutY(yloc - mouseAnchorY);
                    }
                }
            }else{
                if(piece.getRow_spot_location()==1){
                    boolean notRightPlace=false;
                    for(int i=1 ; i < 3  ; i++){
                        notRightPlace=false;
                        if (diffY > lowerlimit && diffY < higherlimit && diffX < lowerlimit && diffX > (lowerlimit * -1)) { // move down
                            piece.setLayoutY(yloc + (tile_size*i) - mouseAnchorY);
                            break;
                        } else {
                            notRightPlace=true;
                        }
                        lowerlimit=higherlimit;
                        higherlimit+=tile_size;
                    }
                    if(notRightPlace)
                        piece.setLayoutY(yloc - mouseAnchorY);
                }
                else{
                    if (diffY > lowerlimit && diffY < higherlimit && diffX < lowerlimit && diffX > (lowerlimit * -1)) // move down
                        piece.setLayoutY(yloc + tile_size - mouseAnchorY);
                    else
                        piece.setLayoutY(yloc - mouseAnchorY);
                }
            }

            for(int i=0 ; i < 8 ; i++){
                for(int j=0 ; j < 8 ; j++){
                    if(BoardController.spots[i][j].getLayoutX()==piece.getLayoutX() && BoardController.spots[i][j].getLayoutY()==piece.getLayoutY()){
                        if(BoardController.spots[i][j].getPiece()!=null)
                            if(piece.isWhite()==BoardController.spots[i][j].getPiece().isWhite()){
                                piece.setLayoutX(xloc - mouseAnchorX);
                                piece.setLayoutY(yloc - mouseAnchorY);
                                return;
                            }
                        if(!BoardController.spots[i][j].isEmpty() && BoardController.spots[i][j].getPiece()!=piece){
                            board.getChildren().remove(BoardController.spots[i][j].getPiece());
                        }else if(BoardController.spots[i][j].isEmpty()){
                            BoardController.spots[i][j].setEmpty(false);
                        }
                        piece.getSpot().setEmpty(true);
                        try {
                            piece.getSpot().setPiece(null);
                        }catch (NullPointerException e){
                            System.out.println();
                        }
                        BoardController.spots[i][j].setPiece(piece);
                    }
                }
            }
            check(board,BoardController.whitePieces[7]);
            check(board,BoardController.blackPieces[7]);
        });
    }

}

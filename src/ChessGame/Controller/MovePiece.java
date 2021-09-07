package ChessGame.Controller;

import ChessGame.Models.Pieces.BlackOnes.*;
import ChessGame.Models.Pieces.Piece;
import ChessGame.Models.Pieces.WhiteOnes.*;
import javafx.scene.layout.Pane;

import java.util.concurrent.atomic.AtomicBoolean;

public class MovePiece {

    private final int tile_size=135;
    double mouseAnchorX;
    double mouseAnchorY;
    double xloc;
    double yloc;


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

    public void queenMovement(Piece piece, Pane board){
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
                }else if(diffX>lowerlimit && diffX < higherlimit && diffX > (lowerlimit*-1) && diffY>lowerlimit && diffY < higherlimit && diffY > (lowerlimit*-1)){ // down and right
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
        });
    }

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
        });
    }

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
        });
    }

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

        });
    }

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

        });
    }

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
                if(piece.getY_spot_location()==6){
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
                if(piece.getY_spot_location()==1){
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

        });
    }

}

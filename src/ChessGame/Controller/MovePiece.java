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

            if((diffY<50&&diffY>0)||diffY>170 && (diffX>170 || diffX<50)) {
                piece.setLayoutX(xloc - mouseAnchorX);
                piece.setLayoutY(yloc - mouseAnchorY);
            }else{
                if (diffY<0) {
                    if(diffY<-50 && diffY>-170 && diffX<50){
                        if(piece.isWhite())
                            piece.setLayoutY(yloc + tile_size - mouseAnchorY);
                        else
                            piece.setLayoutY(yloc - tile_size - mouseAnchorY);
                        if(diffX<0)
                            piece.setLayoutX(xloc-tile_size-mouseAnchorX);
                        else
                            piece.setLayoutX(xloc-mouseAnchorX);
                    }else if(diffY<-50 && diffY>-170 && diffX>50){
                        if(piece.isWhite())
                            piece.setLayoutY(yloc + tile_size - mouseAnchorY);
                        else
                            piece.setLayoutY(yloc - tile_size - mouseAnchorY);
                        piece.setLayoutX(xloc+tile_size-mouseAnchorX);
                    }else if(diffY>-50 && diffX>50 && diffX<170){
                        piece.setLayoutX(xloc+tile_size-mouseAnchorX);
                        piece.setLayoutY(yloc - mouseAnchorY);
                    }
                }
                if(diffY>50 && diffY<170 && diffX<50){
                    if(piece.isWhite())
                        piece.setLayoutY(yloc - tile_size - mouseAnchorY);
                    else
                        piece.setLayoutY(yloc + tile_size - mouseAnchorY);
                    if(diffX<0)
                        piece.setLayoutX(xloc-tile_size-mouseAnchorX);
                    else
                        piece.setLayoutX(xloc-mouseAnchorX);
                }else if(diffY>50 && diffY<170 && diffX>50){
                    if(piece.isWhite())
                        piece.setLayoutY(yloc - tile_size - mouseAnchorY);
                    else
                        piece.setLayoutY(yloc + tile_size - mouseAnchorY);
                    piece.setLayoutX(xloc+tile_size-mouseAnchorX);
                }else if(diffY<50 && diffX>50 && diffX<170){
                    piece.setLayoutX(xloc+tile_size-mouseAnchorX);
                    piece.setLayoutY(yloc - mouseAnchorY);
                }
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
//            System.out.println(xloc+" "+yloc);
        });

        piece.setOnMouseDragged(mouseEvent -> {
            piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

        piece.setOnMouseReleased(mouseEvent -> {


//            System.out.println(BoardController.spots[piece.getX_spot_location()+1][piece.getY_spot_location()+1].isEmpty());
            System.out.println("hwite : " + piece.isWhite());
            if(piece.getX_spot_location()<7 && piece.getX_spot_location()>0)
                if(piece.isWhite()){
                    if(!BoardController.spots[piece.getX_spot_location()+1][piece.getY_spot_location()-1].isEmpty()){
                        System.out.println(BoardController.spots[piece.getX_spot_location()+1][piece.getY_spot_location()-1].isEmpty());
                        System.out.println("X + " +( piece.getX_spot_location()+1));
                        System.out.println("Y + " +( piece.getY_spot_location()+1));
                        canMoveDiagonal_R.set(true);
                    }
                    if(!BoardController.spots[piece.getX_spot_location()-1][piece.getY_spot_location()-1].isEmpty()){
                        canMoveDiagonal_L.set(true);
                    }
                }else{
                    if(!BoardController.spots[piece.getX_spot_location()-1][piece.getY_spot_location()+1].isEmpty()){
                        canMoveDiagonal_R.set(true);
                    }
                    if(!BoardController.spots[piece.getX_spot_location()+1][piece.getY_spot_location()+1].isEmpty()){
                        canMoveDiagonal_L.set(true);
                    }
                    System.out.println("X + " +( piece.getX_spot_location()+1));
                    System.out.println("Y + " +( piece.getY_spot_location()+1));
                }
            System.out.println("Right : " + canMoveDiagonal_R);
            System.out.println("Left : " + canMoveDiagonal_L);

            double diffX= mouseEvent.getSceneX()-xloc;
            double diffY= mouseEvent.getSceneY()-yloc;
//            System.out.println("\n"+mouseEvent.getSceneX()+"\n"+mouseEvent.getSceneY()+"\n");
            if(piece.isWhite())
                diffY*=-1;

//            System.out.println("Diff is : " + diffX+" " + diffY);

            int limit=170;
            if((piece.getX_spot_location()==1 && !piece.isWhite())|| (piece.getX_spot_location()==6 && piece.isWhite()))
                limit*=2;



            if(diffY<50||diffY>limit) {
                piece.setLayoutX(xloc-mouseAnchorX);
                piece.setLayoutY(yloc-mouseAnchorY);
            }else {
                if(piece.isWhite()) {
                    if((piece.getX_spot_location()==1 && !piece.isWhite() && diffY>170)|| (piece.getX_spot_location()==6 && piece.isWhite()&& diffY>170))
                        piece.setLayoutY(yloc - (tile_size*2) - mouseAnchorY);
                    else
                        piece.setLayoutY(yloc - tile_size - mouseAnchorY);
                }
                else {
                    if ((piece.getX_spot_location() == 1 && !piece.isWhite()&& diffY>170) || (piece.getX_spot_location() == 6 && piece.isWhite()&& diffY>170))
                        piece.setLayoutY(yloc + (tile_size*2) - mouseAnchorY);
                    else
                        piece.setLayoutY(yloc + tile_size - mouseAnchorY);
                }
                if(canMoveDiagonal_R.get() && diffX>70)
                    piece.setLayoutX(xloc+tile_size-mouseAnchorX);
                else if(canMoveDiagonal_L.get() && diffX<-70)
                    piece.setLayoutX(xloc-tile_size-mouseAnchorX);
                else if((!canMoveDiagonal_R.get()&&diffX>70 )|| (!canMoveDiagonal_L.get()&&diffX<-70)){
                    piece.setLayoutX(xloc-mouseAnchorX);
                    piece.setLayoutY(yloc-mouseAnchorY);
                }
                else
                    piece.setLayoutX(xloc-mouseAnchorX);
            }

            for(int i=0 ; i < 8 ; i++){
                for(int j=0 ; j < 8 ; j++){
                    if(BoardController.spots[i][j].getX_position()==piece.getLayoutX() && BoardController.spots[i][j].getY_position()==piece.getLayoutY()){
                        piece.setSpot(BoardController.spots[i][j]);
                        System.out.println("Spot x : " + i + "  y : " + j);
                        if(BoardController.spots[i][j].getPiece()!=piece)
                            board.getChildren().remove(BoardController.spots[i][j].getPiece());
                        BoardController.spots[i][j].setPiece(piece);
                    }
                }
            }
        });
    }

}

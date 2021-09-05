package ChessGame.Controller;

import ChessGame.Models.Pieces.BlackOnes.Pawn;
import ChessGame.Models.Pieces.Piece;

public class MovePiece {

    double mouseAnchorX;
    double mouseAnchorY;
    double xloc;
    double yloc;


    public void move(Piece piece){
        if(piece instanceof Pawn)
            pawnMovement((Pawn) piece);
    }

    public void pawnMovement(Pawn piece){
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
            double diffX= mouseEvent.getSceneX()-xloc;
            double diffY= mouseEvent.getSceneY()-yloc;
//            System.out.println("\n"+mouseEvent.getSceneX()+"\n"+mouseEvent.getSceneY()+"\n");
            if(piece.isWhite())
                diffY*=-1;

//            System.out.println("Diff is : " + diffX+" " + diffY);

            if(diffY<50||diffY>170) {
                piece.setLayoutX(xloc-mouseAnchorX);
                piece.setLayoutY(yloc-mouseAnchorY);
            }else {
                if(piece.isWhite())
                    piece.setLayoutY(yloc-135-mouseAnchorY);
                else
                    piece.setLayoutY(yloc+135-mouseAnchorY);
            }
        });
    }

}

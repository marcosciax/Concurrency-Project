package ChessGame.Controller;

import ChessGame.Models.Pieces.Piece;
import account_management.Models.Account;
import javafx.scene.layout.GridPane;

public class MovePiece {

    double mouseAnchorX;
    double mouseAnchorY;
    double xloc;
    double yloc;


    public void move(Piece piece){
        piece.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
            xloc = mouseEvent.getSceneX();
            yloc = mouseEvent.getSceneY();
            System.out.println(xloc+" "+yloc);
        });

        piece.setOnMouseDragged(mouseEvent -> {

            piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);

//            System.out.println(mouseEvent.getSceneX()+" "+mouseEvent.getSceneY());
        });

        piece.setOnMouseReleased(mouseEvent -> {
//            System.out.println("sajdjasld");
            double diffX= mouseEvent.getSceneX()-xloc;
            double diffY= mouseEvent.getSceneY()-yloc;
            System.out.println("\n"+mouseEvent.getSceneX()+"\n"+mouseEvent.getSceneY()+"\n");
            if(diffX<0)
                diffX*=-1;
            if(diffY<0)
                diffY*=-1;
            System.out.println("Diff is : " + diffX+" " + diffY);
            if(((diffX<(diffX+(double)135/2) && diffX>0 )|| diffX>(diffX+(135*2))) ||(( diffY<(diffY+(double) 135/2) && diffY>0) || diffY>(diffY+(135*2)))) {
                System.out.println("In here");
                System.out.println("\bRelocate to  : " + xloc + " " + yloc);
//                piece.relocate(xloc, yloc);
                piece.setLayoutX(xloc-mouseAnchorX);
                piece.setLayoutY(yloc-mouseAnchorY);
            }
//            if(piece.getLayoutX() >= (sq.getLayoutX()) && piece.getLayoutX() <= (sq.getLayoutX()+101) && piece.getLayoutY() >= sq.getLayoutY() && piece.getLayoutY() < (sq.getLayoutY()+110) ){
//                piece.relocate(xloc,yloc);

//                System.out.println("in here");
//            }
        });
    }

}

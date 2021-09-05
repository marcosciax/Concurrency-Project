package ChessGame.Controller;

import ChessGame.Models.Pieces.Piece;
import account_management.Models.Account;
import javafx.scene.layout.GridPane;

public class MovePiece {

    private int x;
    private int y;
//    private Piece piece;
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
            System.out.println(mouseAnchorX+" "+mouseAnchorY);
        });

        piece.setOnMouseDragged(mouseEvent -> {

            piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);

            System.out.println(mouseEvent.getSceneX()+" "+mouseEvent.getSceneY());
        });

        piece.setOnMouseReleased(mouseEvent -> {
//            System.out.println("sajdjasld");


//            if(piece.getLayoutX() >= (sq.getLayoutX()) && piece.getLayoutX() <= (sq.getLayoutX()+101) && piece.getLayoutY() >= sq.getLayoutY() && piece.getLayoutY() < (sq.getLayoutY()+110) ){
//                piece.relocate(xloc,yloc);
//                System.out.println("\n"+mouseAnchorX+"\n"+mouseAnchorY+"\n");
//                System.out.println("in here");
//            }
        });
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
//
//    public Piece getPiece() {
//        return piece;
//    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
//
//    public void setPiece(Piece piece) {
//        this.piece = piece;
//    }
}

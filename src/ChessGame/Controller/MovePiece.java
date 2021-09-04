package ChessGame.Controller;

import ChessGame.Models.Pieces.EmptyPiece;
import ChessGame.Models.Pieces.Piece;
import ChessGame.Models.Spot;
import account_management.Models.Account;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MovePiece {

    private int newCol;
    private int newRow;
    private Piece piece;

    public MovePiece(GridPane pane, Piece piece, int newCol , int newRow){
        this.newCol=newCol;
        this.newRow=newRow;
        this.piece = piece;
        int oldrow=piece.getSpot().getX_location(),oldcol=piece.getSpot().getY_location();
            pane.getChildren().remove(piece);
//            pane.add(new EmptyPiece(new Account("", ""), oldrow, oldcol), oldrow, oldcol);
//            pane.getChildren().remove(newRow, newCol);
            pane.add(piece, newRow, newCol);
//        piece.getSpot().setPiece(new EmptyPiece(new Account("",""),oldrow,oldcol));
//        piece.setSpot(BoardController.spots[newRow][newCol]);
//        piece.getSpot().setPiece(piece);
    }


    public int getNewCol() {
        return newCol;
    }

    public int getNewRow() {
        return newRow;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setNewCol(int newCol) {
        this.newCol = newCol;
    }

    public void setNewRow(int newRow) {
        this.newRow = newRow;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}

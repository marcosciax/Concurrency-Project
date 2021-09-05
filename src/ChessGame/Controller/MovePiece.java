package ChessGame.Controller;

import ChessGame.Models.Pieces.EmptyPiece;
import ChessGame.Models.Pieces.Piece;
import ChessGame.Models.Spot;
import account_management.Models.Account;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.XMLFormatter;

public class MovePiece {

    private int x;
    private int y;
    private Piece piece;

    public MovePiece( Piece piece, int x , int y){
        this.x=x;
        this.y=y;
        this.piece = piece;

    }

    public void move(GridPane pane){
        int old_y=piece.getSpot().getY_location(),old_x=piece.getSpot().getX_location();

        pane.getChildren().remove(piece);
        BoardController.spots[old_x][old_y].setPiece(new EmptyPiece(new Account("",""),x,y));
        BoardController.spots[old_x][old_y].setEmpty(true);

        pane.getChildren().remove(BoardController.spots[y][x].getPiece());
        BoardController.spots[x][y].setPiece(piece);
        BoardController.spots[x][y].setEmpty(false);

        pane.add(BoardController.spots[old_x][old_y].getPiece(),old_x,old_y);
//        pane.getChildren().add();
        pane.add(piece, x, y);

        piece.setSpot(BoardController.spots[x][y]);
        piece.setSelected(false);
        BoardController boardController = new BoardController();
        boardController.makeSelectable();
        boardController.makeMovable();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}

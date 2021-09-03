package ChessGame.Models;

import ChessGame.Models.Pieces.Piece;

public class Spot {

    private Piece piece ;
    private boolean isEmpty;
    private int x_location;
    private int y_location;

    public Spot(int x, int y){
        this.x_location=x;
        this.y_location=y;
    }

    public int getX_location() {
        return x_location;
    }

    public int getY_location() {
        return y_location;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}

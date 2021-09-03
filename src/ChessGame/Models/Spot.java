package ChessGame.Models;

import ChessGame.Models.Pieces.Piece;

public class Spot {

    private Piece piece ;
    private boolean isEmpty;

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

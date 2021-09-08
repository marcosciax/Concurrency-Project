package ChessGame.Models;

import ChessGame.Models.Pieces.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Spot extends Rectangle {

    private Piece piece ;
    private boolean isEmpty;
    private final int row_number;
    private final int column_number;
    private final double x_position;
    private final double y_position;

    public Spot(double size, int col, int row, double x_position, double y_position,Color color){
        super.setWidth(size);
        super.setHeight(size);
        super.setFill(color);
        super.setStroke(Color.BLACK);
        super.setLayoutX(x_position);
        super.setLayoutY(y_position);
        this.row_number =row;
        this.column_number =col;
        this.x_position = x_position;
        this.y_position = y_position;
        isEmpty=true;
    }

    public int getRow_number() {
        return row_number;
    }

    public int getColumn_number() {
        return column_number;
    }

    public double getX_position() {
        return x_position;
    }

    public double getY_position() {
        return y_position;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        piece.setSpot(this);
        this.setEmpty(false);
        piece.setLayoutX(x_position);
        piece.setLayoutY(y_position);
        piece.setRow_spot_location(row_number);
        piece.setColumn_spot_location(column_number);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}

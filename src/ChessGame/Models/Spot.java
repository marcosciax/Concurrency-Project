package ChessGame.Models;

import ChessGame.Models.Pieces.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Spot extends Rectangle {

    private Piece piece ;
    private boolean isEmpty;
    private final int x_location;
    private final int y_location;
    private final double x_position;
    private final double y_position;

    public Spot(double size, int x, int y, double x_position, double y_position,Color color){
        super.setWidth(size);
        super.setHeight(size);
        super.setFill(color);
        super.setStroke(Color.BLACK);
        super.setLayoutX(x_position);
        super.setLayoutY(y_position);
        this.x_location=x;
        this.y_location=y;
        this.x_position = x_position;
        this.y_position = y_position;
        isEmpty=true;
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

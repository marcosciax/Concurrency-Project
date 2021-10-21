package Checkers.Models;

import Checkers.Controller.BoardController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

/**
 * Spot is a tile in which a piece can be Placed
 * Spot is rectangle(Square)
 */
public class Spot extends Rectangle implements Serializable {

    private final int row_number;
    private final int column_number;
    private final boolean isDark;
    private boolean isEmpty;
    private Piece piece;
    private boolean canTakePiece;
    private int number;
    public final boolean firstColumnSpots;

    public Spot(double xLayout , double yLayout , int column, int row, boolean isDark,int number){
        super.setWidth(135);
        super.setHeight(135);
        super.setLayoutX(xLayout);
        super.setLayoutY(yLayout);
        this.column_number = column;
        this.row_number = row;
        this.isDark=isDark;
        this.number=number;
        this.isEmpty=true;
        this.piece=null;
        if(column_number==0)
            firstColumnSpots=true;
        else
            firstColumnSpots=false;
        super.setStroke(Color.rgb(0,0,0));
        if(this.isDark)
            super.setFill(Color.rgb(67,60,42));
        else
            super.setFill(Color.rgb(203,194,183));

    }

    public Piece getPiece() {
        return piece;
    }

    public int getColumn_number() {
        return column_number;
    }

    public int getRow_number() {
        return row_number;
    }

    public boolean isDark() {
        return isDark;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if(piece!=null) {
            piece.setLayoutX(getLayoutX() + 63);
            piece.setLayoutY(getLayoutY() + 63);
        }
        this.isEmpty=false;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setCanTakePiece(boolean canTakePiece) {
        this.canTakePiece = canTakePiece;
    }

    public boolean isCanTakePiece() {
        return canTakePiece;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

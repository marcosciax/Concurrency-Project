package Checkers.Models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Spot extends Rectangle {

    private final int row_number;
    private final int column_number;
    private final boolean isDark;
    private Piece piece;

    public Spot(double xLayout , double yLayout , int row, int column, boolean isDark){
        super.setWidth(135);
        super.setHeight(135);
        super.setLayoutX(xLayout);
        super.setLayoutY(yLayout);
        this.column_number = column;
        this.row_number = row;
        this.isDark=isDark;
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
    }
}

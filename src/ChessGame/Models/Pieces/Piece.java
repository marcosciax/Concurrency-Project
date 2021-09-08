package ChessGame.Models.Pieces;

import ChessGame.Controller.BoardController;
import ChessGame.Models.Spot;
import account_management.Models.Account;
import javafx.scene.image.ImageView;

public class Piece extends ImageView {

    private Account player;
    private boolean isWhite;
    private boolean isSelected;
    private Spot spot;
    private int row_spot_location;
    private int column_spot_location;

    public Piece(Account player, int col, int row, boolean b){
        isWhite=b;
        isSelected=false;
        this.player = player;
        spot = BoardController.spots[row][col];
        row_spot_location =row;
        column_spot_location =col;
    }

    public int getRow_spot_location() {
        return row_spot_location;
    }

    public int getColumn_spot_location() {
        return column_spot_location;
    }

    public void setRow_spot_location(int row_spot_location) {
        this.row_spot_location = row_spot_location;
    }

    public void setColumn_spot_location(int column_spot_location) {
        this.column_spot_location = column_spot_location;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}


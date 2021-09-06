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
    private int x_spot_location;
    private int y_spot_location;

    public Piece(Account player, int x, int y, boolean b){
        isWhite=b;
        isSelected=false;
        this.player = player;
        spot = BoardController.spots[x][y];
        x_spot_location=x;
        y_spot_location=y;
    }

    public int getX_spot_location() {
        return x_spot_location;
    }

    public int getY_spot_location() {
        return y_spot_location;
    }

    public void setX_spot_location(int x_spot_location) {
        this.x_spot_location = x_spot_location;
    }

    public void setY_spot_location(int y_spot_location) {
        this.y_spot_location = y_spot_location;
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


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

    public Piece(Account player, int x, int y, boolean b){
        isWhite=b;
        isSelected=false;
        this.player = player;
        spot = BoardController.spots[x][y];
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


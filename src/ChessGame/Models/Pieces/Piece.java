package ChessGame.Models.Pieces;

import ChessGame.Controller.BoardController;
import ChessGame.Models.Spot;
import account_management.Models.Account;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece extends ImageView {

    private Account player;
    private boolean isSelected;
    private Spot spot;

    public Piece(Account player, int x , int y){
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}


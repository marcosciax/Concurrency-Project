package ChessGame.Models.Pieces;

import account_management.Models.Account;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece extends ImageView {

    private int columnIndex;
    private int rowIndex;
    private Account player;
    private boolean isSelected;

    public Piece(Account player, int row , int col){
        isSelected=false;
        this.player = player;
        this.rowIndex = row;
        this.columnIndex =  col;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

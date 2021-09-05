package ChessGame.Models.Pieces;

import account_management.Models.Account;
import javafx.scene.image.Image;

public class EmptyPiece extends Piece{
    public EmptyPiece(Account player, int row, int col) {
        super(player, row, col);
        super.setImage(new Image("file:images/emptyBlocks.jpg",100,100,false,false));
    }
}

package ChessGame.Models.Pieces.WhiteOnes;

import ChessGame.Models.Pieces.Piece;
import account_management.Models.Account;
import javafx.scene.image.Image;

public class W_King extends Piece {

    public W_King(Account player, int row, int col) {
        super(player, row, col);
        super.setImage(new Image("file:images/WhiteOnes/King.png"));
    }

}

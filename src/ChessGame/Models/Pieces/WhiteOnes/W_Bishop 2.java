package ChessGame.Models.Pieces.WhiteOnes;

import ChessGame.Models.Pieces.Piece;
import account_management.Models.Account;
import javafx.scene.image.Image;

public class W_Bishop extends Piece {
    public W_Bishop(Account player, int row, int col) {
        super(player, row, col, true);
        super.setImage(new Image("file:images/WhiteOnes/Bishop.png",100,100,false,false));
    }
}

package ChessGame.Models.Pieces.WhiteOnes;

import ChessGame.Models.Pieces.Piece;
import account_management.Models.Account;
import javafx.scene.image.Image;

public class W_Queen extends Piece {
    public W_Queen(Account player, int row, int col) {
        super(player, row, col);
        super.setImage(new Image("file:images/WhiteOnes/Queen.png"));
    }
}

package ChessGame.Models.Pieces.BlackOnes;

import ChessGame.Models.Pieces.Piece;
import account_management.Models.Account;
import javafx.scene.image.Image;

public class Rook extends Piece {
    public Rook(Account player, int row, int col) {
        super(player, row, col);
        super.setImage(new Image("file:images/BlackOnes/rook.png"));
    }
}

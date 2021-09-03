package ChessGame.Models.Pieces.WhiteOnes;

import ChessGame.Models.Pieces.Piece;
import account_management.Models.Account;
import javafx.scene.image.Image;

public class Pawn extends Piece {
    public Pawn(Account player, int row, int col) {
        super(player, row, col);
        super.setImage(new Image("file:images/WhiteOnes/pawn.png"));
    }
}

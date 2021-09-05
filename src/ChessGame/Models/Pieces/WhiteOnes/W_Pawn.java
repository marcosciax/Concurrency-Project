package ChessGame.Models.Pieces.WhiteOnes;

import ChessGame.Models.Pieces.Piece;
import account_management.Models.Account;
import javafx.scene.image.Image;

public class W_Pawn extends Piece {
    public W_Pawn(Account player, int row, int col) {
        super(player, row, col, true);
        super.setImage(new Image("file:images/WhiteOnes/pawn.png",100,100,false,false));
    }
}

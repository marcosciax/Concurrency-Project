package ChessGame.Models.Pieces.BlackOnes;

import ChessGame.Models.Pieces.Piece;
import account_management.Models.Account;
import javafx.scene.image.Image;

public class Knight extends Piece {
    public Knight(Account player, int row, int col) {
        super(player, row, col, false);
        super.setImage(new Image("file:images/BlackOnes/Knight.png",100,100,false,false));
    }
}

package ChessGame.Controller;

import ChessGame.Models.Pieces.BlackOnes.King;
import ChessGame.Models.Pieces.Piece;
import ChessGame.Models.Pieces.WhiteOnes.W_King;
import javafx.scene.layout.Pane;

public class CheckWin {

    public static boolean CheckW_King(Pane board, W_King king){
        return !board.getChildren().contains(king);
    }

    public static boolean CheckKing(Pane board, King king){
        return !board.getChildren().contains(king);
    }

}

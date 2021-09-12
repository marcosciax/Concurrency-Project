package Checkers.Controller;

import Checkers.Models.Piece;
import Checkers.Models.Spot;
import javafx.scene.layout.Pane;

public class CheckWin {

    private static int remaining_pieces;
    private static Piece remaining_piece;

    public static int checkWinner(){
        for(Spot spot : BoardController.spots){
            if(spot.getPiece()!=null){
                remaining_pieces++;
                remaining_piece=spot.getPiece();
            }
        }

        if(remaining_pieces==1) {
            if (remaining_piece.getPlayerAssociated().equals(BoardController.playerOne)) return 1;
            else if (remaining_piece.getPlayerAssociated().equals(BoardController.playerTwo)) return 2;
        }

        return -1;
    }
}

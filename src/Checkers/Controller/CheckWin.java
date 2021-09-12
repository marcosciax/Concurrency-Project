package Checkers.Controller;

import Checkers.Models.Piece;
import Checkers.Models.Spot;
import account_management.Models.Account;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class CheckWin {

    private static boolean isSame;
    private static ArrayList<Piece> remaining_pieces = new ArrayList<>();
    private static Account playerWon;

    public static void checkWinner(){
        for(Spot spot : BoardController.spots){
            if(spot.getPiece()!=null){
                remaining_pieces.add(spot.getPiece());
            }
        }

        for(int i=0 ; i < remaining_pieces.size() ; i++){
            for (Piece remaining_piece : remaining_pieces) {
                if (remaining_pieces.get(i).getPlayerAssociated().equals(remaining_piece.getPlayerAssociated()))
                    isSame = false;
                else {
                    isSame = true;
                    break;
                }
            }
        }

        if(isSame) {
            playerWon = remaining_pieces.get(0).getPlayerAssociated();

            if (playerWon.equals(BoardController.playerOne))
                System.out.println("Player One won");
            else if (playerWon.equals(BoardController.playerTwo))
                System.out.println("Player Two Won");
        }
    }
}

package Checkers.Controller;

import Checkers.Models.Piece;
import Checkers.Models.Spot;
import Win.WinController;
import account_management.Models.Account;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This Class is responsible for Checking When the Game is over and who won
 */
public class CheckWin {

    public static Stage stage;
    private static boolean isSame;
    private static ArrayList<Piece> remaining_pieces = new ArrayList<>();
    private static Account playerWon;

    /**
     * This Method Checks the Winner of the Match and Prints Who won
     * Checks whole board and if only one player pieces are remaining that player wins
     */
    public static void checkWinner(){
        remaining_pieces = new ArrayList<>();
        for(Spot spot : BoardController.spots){
            if(spot.getPiece()!=null){
                remaining_pieces.add(spot.getPiece());
            }
        }

        for(int i=0 ; i < remaining_pieces.size() ; i++){
            for (Piece remaining_piece : remaining_pieces) {
                if (remaining_pieces.get(i).getPlayerAssociated().equals(remaining_piece.getPlayerAssociated()))
                    isSame = true;
                else {
                    isSame = false;
                    break;
                }
            }
            if(!isSame)
                break;
        }

        if(isSame) {
            playerWon = remaining_pieces.get(0).getPlayerAssociated();

            if (playerWon.equals(BoardController.playerOne))
                WinController.winnerName=BoardController.playerOne.getUserName();
            else if (playerWon.equals(BoardController.playerTwo))
                WinController.winnerName=BoardController.playerTwo.getUserName();

            Parent root = null;
            try {
                root = FXMLLoader.load(CheckWin.class.getResource("/Win/Win.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.close();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}

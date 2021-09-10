package Checkers.Controller;

import Checkers.Models.BoardInfo;
import Checkers.Models.Piece;
import Checkers.Models.Spot;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Move {

     public void movePiece(Piece piece){

         piece.setOnMousePressed(MouseEvent -> {

             System.out.println(piece.getSpot().getRow_number()+ " " + piece.getSpot().getColumn_number());
             ArrayList<Spot> availableSpotsL = getSingleMoves(piece.getSpot());
//             ArrayList<Spot> availableSpotsR = getMovableSpotsRight(piece.getSpot());

             for(Spot spot : availableSpotsL)
                 spot.setFill(Color.RED);
//             BoardController.spots[2][4].setFill(Color.RED);
//             for(Spot spot : availableSpotsR)
//                 spot.setFill(Color.RED);

         });

     }

     public void getSpotInfo(Spot spot){
         spot.setOnMousePressed(mouseEvent ->{
             System.out.println(spot.getRow_number() + " " + spot.getColumn_number());
         });
     }

     public ArrayList<Spot> getSingleMoves(Spot spot) {
         ArrayList<Spot> availableSpots = new ArrayList<>();
         int row_number = spot.getRow_number();
         int col_number = spot.getColumn_number();

         int checkRow = spot.getPiece().getPlayerAssociated().equals(BoardController.playerOne) ? -1 : 1;

         if (row_number == 0)
             return availableSpots;

         Spot spotToBeAdded=null;

         for(int i=0 ; i < 64 ;i++){
             if(BoardController.spots[i].getRow_number()==row_number+checkRow && BoardController.spots[i].getColumn_number()==col_number+1) {
                 spotToBeAdded = BoardController.spots[i];
                 break;
             }
         }

         if (checkEmpty(spotToBeAdded))
             availableSpots.add(spotToBeAdded);

         for(int i=0 ; i < 64 ;i++){
             if(BoardController.spots[i].getRow_number()==row_number+checkRow && BoardController.spots[i].getColumn_number()==col_number-1) {
                 spotToBeAdded = BoardController.spots[i];
                 break;
             }
         }

         if (checkEmpty(spotToBeAdded))
             availableSpots.add(spotToBeAdded);

         return availableSpots;
     }

     public ArrayList<Spot> getMovableSpotsRight(Spot spot){

        ArrayList<Spot> availableSpots = new ArrayList<>();
        int row_number = spot.getRow_number();
        int col_number = spot.getColumn_number();

        if(row_number==0 || row_number == 7 || col_number == 0 || col_number==7)
            return availableSpots;

//        if(checkEmpty(row_number-1,col_number+1))
//            availableSpots.add(BoardController.spots[row_number-1][col_number+1]);
//        else
//            getMovableSpotsRight(BoardController.spots[row_number-1][col_number+1]);

         return availableSpots;

     }

     public ArrayList<Spot> getMovableSpotsLeft(Spot spot){
         ArrayList<Spot> availableSpots = new ArrayList<>();
         int row_number = spot.getRow_number();
         int col_number = spot.getColumn_number();

         if(row_number==0 || row_number == 7 || col_number == 0 || col_number==7)
             return availableSpots;

//         if(checkEmpty(row_number-1,col_number-1))
//             availableSpots.add(BoardController.spots[row_number-1][col_number-1]);
//         else
//             getMovableSpotsLeft(BoardController.spots[row_number-1][col_number-1]);

         return availableSpots;
     }

     public boolean checkEmpty(Spot spot){
         System.out.println(spot.getRow_number() + "  " + spot.getColumn_number());
         System.out.println(spot.getPiece() == null);
         return spot.getPiece() == null;
//         return true;
     }

}

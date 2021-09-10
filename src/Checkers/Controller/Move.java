package Checkers.Controller;

import Checkers.Models.BoardInfo;
import Checkers.Models.Piece;
import Checkers.Models.Spot;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Move implements Initializable {

    ArrayList<Spot> killableMoves = new ArrayList<>();

    int checkRow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        killableMoves = new ArrayList<>();
    }

    public void movePiece(Piece piece){

         piece.setOnMousePressed(MouseEvent -> {

             System.out.println(piece.getSpot().getRow_number()+ " " + piece.getSpot().getColumn_number());
             ArrayList<Spot> singleAvailableMoves = getSingleMoves(piece.getSpot());
             getKillableMovesInRight(piece.getSpot(),true);
             getKillableMovesInLeft(piece.getSpot(),true);
//             ArrayList<Spot> availableSpotsR = getMovableSpotsRight(piece.getSpot());


             for(Spot spot : singleAvailableMoves)
                 spot.setFill(Color.RED);
             for(Spot spot : killableMoves)
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

         if (row_number == 0 && checkRow==-1)
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

     public void getKillableMovesInRight(Spot spot,boolean firstTime){
         int row_number = spot.getRow_number();
         int col_number = spot.getColumn_number();

         if (firstTime)
             checkRow = spot.getPiece().getPlayerAssociated().equals(BoardController.playerOne) ? -1 : 1;

         if(spot.getRow_number()==0 && checkRow==-1)
             return;
         if(spot.getRow_number()==7 && checkRow==1)
             return;

         Spot spotToBeAdded = getRequiredSpot(row_number,col_number,checkRow,1);

         if(!checkEmpty(spotToBeAdded)){
             spotToBeAdded = getRequiredSpot(row_number+checkRow , col_number+1, checkRow,1);
             if(checkEmpty(spotToBeAdded)) {
                 killableMoves.add(spotToBeAdded);
                 getKillableMovesInRight(spotToBeAdded,false);
             }
         }
     }

    public void getKillableMovesInLeft(Spot spot,boolean firstTime){
        int row_number = spot.getRow_number();
        int col_number = spot.getColumn_number();

        if (firstTime)
            checkRow = spot.getPiece().getPlayerAssociated().equals(BoardController.playerOne) ? -1 : 1;

        if(spot.getRow_number()==0 && checkRow==-1)
            return;
        if(spot.getRow_number()==7 && checkRow==1)
            return;

        Spot spotToBeAdded = getRequiredSpot(row_number,col_number,checkRow,-1);

        if(!checkEmpty(spotToBeAdded)){
            spotToBeAdded = getRequiredSpot(row_number+checkRow , col_number-1, checkRow,-1);
            if(checkEmpty(spotToBeAdded)) {
                killableMoves.add(spotToBeAdded);
                getKillableMovesInLeft(spotToBeAdded,false);
            }
        }
    }

     public Spot getRequiredSpot(int row_number, int col_number, int checkRow , int col_direction){
         Spot spotToBeAdded=null;

         for(int i=0 ; i < 64 ;i++){
             if(BoardController.spots[i].getRow_number()==row_number+checkRow && BoardController.spots[i].getColumn_number()==col_number+col_direction) {
                 spotToBeAdded = BoardController.spots[i];
                 break;
             }
         }

         return spotToBeAdded;
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

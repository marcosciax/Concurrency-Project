package Checkers.Controller;

import Checkers.Models.Piece;
import Checkers.Models.Spot;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Move implements Initializable {

    private double mouseAnchorX;
    private double mouseAnchorY;
    private double original_piece_location_x;
    private double original_piece_location_y;
    private ArrayList<Spot> killableMoves = new ArrayList<>();
    private ArrayList<Spot> singleAvailableMoves = new ArrayList<>();
    private ArrayList<Piece> piecesToBeKilled = new ArrayList<>();
    private Pane board;
    private int checkRow;
    private Piece piece;
    private boolean successfulTurn;

    public Move(Pane board){
        this.board=board;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        killableMoves = new ArrayList<>();
        successfulTurn=false;
    }

    public void movePiece(Piece piece){

         piece.setOnMousePressed(MouseEvent -> {

             mouseAnchorX = MouseEvent.getX();
             mouseAnchorY = MouseEvent.getY();
             original_piece_location_x = MouseEvent.getSceneX();
             original_piece_location_y = MouseEvent.getSceneY();

            this.piece = piece;
             System.out.println(piece.getSpot().getRow_number()+ " " + piece.getSpot().getColumn_number());
             singleAvailableMoves = getSingleMoves(piece.getSpot());
             getKillableMovesInRight(piece.getSpot(),true);
             getKillableMovesInLeft(piece.getSpot(),true);

             CheckWin.checkWinner();

             for(Spot spot : singleAvailableMoves)
                 spot.setFill(Color.RED);
             for(Spot spot : killableMoves)
                 spot.setFill(Color.RED);

         });

         piece.setOnMouseDragged(mouseEvent -> {
             piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
             piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
         });

         piece.setOnMouseReleased(mouseEvent -> {
             boolean moved = false;

             int i=1;
             for(Spot spot : killableMoves){
                 double leftLayout = spot.getLayoutX()-135;
                 double rightLayout = spot.getLayoutX();
                 double topLayout = spot.getLayoutY()-135;
                 double bottomLayout = spot.getLayoutY();

                 System.out.println(leftLayout +" " + rightLayout +" " + bottomLayout + " " + topLayout);
                 System.out.println(mouseEvent.getSceneX()-100);
                 System.out.println(mouseEvent.getSceneY()-100);

                 if(mouseEvent.getSceneX()-100>leftLayout && mouseEvent.getSceneX()-100 < rightLayout && mouseEvent.getSceneY()-100 < bottomLayout && mouseEvent.getSceneY()-100 > topLayout){
                     moved=true;
                     piece.getSpot().setPiece(null);
                     piece.getSpot().setEmpty(true);
                     piece.setSpot(spot);
                     for(int k=0 ; k < i ; k++) {
                         piecesToBeKilled.get(k).getSpot().setPiece(null);
                         piecesToBeKilled.get(k).getSpot().setEmpty(true);
                         board.getChildren().remove(piecesToBeKilled.get(k));
                         successfulTurn=true;
                     }
                 }
                 if(!moved){
                     piece.setLayoutX(original_piece_location_x -mouseAnchorX);
                     piece.setLayoutY(original_piece_location_y -mouseAnchorY);
                 }

                 i++;
             }
             for(Spot spot : singleAvailableMoves){
                 double leftLayout = spot.getLayoutX()-135;
                 double rightLayout = spot.getLayoutX();
                 double topLayout = spot.getLayoutY()-135;
                 double bottomLayout = spot.getLayoutY();

                 System.out.println(leftLayout +" " + rightLayout +" " + bottomLayout + " " + topLayout);
                 System.out.println(mouseEvent.getSceneX()-100);
                 System.out.println(mouseEvent.getSceneY()-100);

                 if(mouseEvent.getSceneX()-100>leftLayout && mouseEvent.getSceneX()-100 < rightLayout && mouseEvent.getSceneY()-100 < bottomLayout && mouseEvent.getSceneY()-100 > topLayout){
                     moved=true;
                     piece.getSpot().setEmpty(true);
                     piece.getSpot().setPiece(null);
                     piece.setSpot(spot);
                     successfulTurn=true;
                 }
             }

             if(!moved){
                 piece.setLayoutX(original_piece_location_x -mouseAnchorX);
                 piece.setLayoutY(original_piece_location_y -mouseAnchorY);
             }


             for(Spot spot  : killableMoves)
                 spot.setFill(Color.rgb(67,60,42));
             for(Spot spot : singleAvailableMoves)
                 spot.setFill(Color.rgb(67,60,42));

             if(successfulTurn) {
                 BoardController boardController = new BoardController();
                 boardController.changePlayerTurn();
             }

             killableMoves = new ArrayList<>();
             singleAvailableMoves = new ArrayList<>();
             piecesToBeKilled = new ArrayList<>();
             successfulTurn=false;

         });

         piece.setOnMouseExited(mouseEvent -> {

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

         if (spotToBeAdded!=null && checkEmpty(spotToBeAdded))
             availableSpots.add(spotToBeAdded);

         for(int i=0 ; i < 64 ;i++){
             if(BoardController.spots[i].getRow_number()==row_number+checkRow && BoardController.spots[i].getColumn_number()==col_number-1) {
                 spotToBeAdded = BoardController.spots[i];
                 break;
             }
         }

         if (spotToBeAdded!=null &&checkEmpty(spotToBeAdded))
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

         if(spotToBeAdded==null)
             return;

         if(!checkEmpty(spotToBeAdded)){
             if(spotToBeAdded.getPiece().getPlayerAssociated().equals(piece.getPlayerAssociated()))
                 return;

             if(getRequiredSpot(row_number+checkRow,col_number+1,checkRow,1)!=null && checkEmpty(getRequiredSpot(row_number+checkRow,col_number+1,checkRow,1)) )
                piecesToBeKilled.add(spotToBeAdded.getPiece());
             for(Piece piece : piecesToBeKilled)
                 System.out.println("Spot to be killed row : " + piece.getSpot().getRow_number() + "COl : " + piece.getSpot().getColumn_number());

             spotToBeAdded = getRequiredSpot(row_number+checkRow , col_number+1, checkRow,1);
             if(spotToBeAdded==null)
                 return;
             if(checkEmpty(spotToBeAdded)) {
                 killableMoves.add(spotToBeAdded);
                 getKillableMovesInRight(spotToBeAdded,false);
                 getKillableMovesInLeft(spotToBeAdded,false);
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

        if(spotToBeAdded==null)
            return;

        if(!checkEmpty(spotToBeAdded)){
            if(spotToBeAdded.getPiece().getPlayerAssociated().equals(piece.getPlayerAssociated()))
                return;
            if(getRequiredSpot(row_number+checkRow,col_number-1,checkRow,-1)!=null && checkEmpty(getRequiredSpot(row_number+checkRow,col_number-1,checkRow,-1)))
                piecesToBeKilled.add(spotToBeAdded.getPiece());
            for(Piece piece : piecesToBeKilled)
                System.out.println("Spot to be killed row : " + piece.getSpot().getRow_number() + "COl : " + piece.getSpot().getColumn_number());
            spotToBeAdded = getRequiredSpot(row_number+checkRow , col_number-1, checkRow,-1);
            if(spotToBeAdded==null)
                return;
            if(checkEmpty(spotToBeAdded)) {
                killableMoves.add(spotToBeAdded);
                getKillableMovesInLeft(spotToBeAdded,false);
                getKillableMovesInRight(spotToBeAdded,false);
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


     public boolean checkEmpty(Spot spot){
         System.out.println(spot.getRow_number() + "  " + spot.getColumn_number());
         System.out.println(spot.getPiece() == null);
         return spot.getPiece() == null;
     }


}

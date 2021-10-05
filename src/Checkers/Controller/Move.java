package Checkers.Controller;

import Checkers.Models.BoardInfo;
import Checkers.Models.Piece;
import Checkers.Models.Spot;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import account_management.Models.Account;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.BindException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Move Class is Responsible for Movement of Pieces in board
 */
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
    private int checkKingRow;
    private Piece piece;
    private boolean successfulTurn;

    private Piece piece_to_be_moved;
    private Spot oldSpot;
    private Spot newSpot;

    public Move(Pane board){
        this.board=board;
        Thread receiveChanges = new Thread(() -> {
                if (BoardInfo.getSocketClient() != null) {
                    for(Piece piece : BoardController.playerOnePieces)
                        piece.setDisable(true);
                    while (true) {
                         try {
                            int newSpotLoc=0;
                             Spot spot = (Spot) BoardInfo.getSocketClient().readData();
                             for (int i=0 ; i< BoardController.spots.length ; i++) {
                                if (spot.getRow_number() == BoardController.spots[i].getRow_number() && spot.getColumn_number() == BoardController.spots[i].getColumn_number()) {
                                    BoardController.spots[i].setPiece(spot.getPiece());
                                    newSpotLoc=i;
                                    break;
                                }
                            }
                            int spotloc=0;
                            spot = (Spot) BoardInfo.getSocketClient().readData();
                            for (int i=0 ; i< BoardController.spots.length ; i++) {
                                if (spot.getRow_number() == BoardController.spots[i].getRow_number() && spot.getColumn_number() == BoardController.spots[i].getColumn_number()) {
                                    BoardController.spots[i].setPiece(null);
                                    spotloc=i;
                                    break;
                                }
                            }
                            Piece piece = (Piece) BoardInfo.getSocketClient().readData();
                            for (int i=0 ; i < BoardController.playerOnePieces.length ; i++) {
                                if (BoardController.playerOnePieces[i].getSpot().equals(BoardController.spots[spotloc])) {
                                    BoardController.playerOnePieces[i].setSpot(BoardController.spots[newSpotLoc]);
                                    break;
                                }
                            }
                             ArrayList<Piece> pieces = (ArrayList<Piece>) BoardInfo.getSocketClient().readData();
                             removeThingsFromPane(pieces);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    BoardController boardController = new BoardController();
                                    boardController.changePlayerTurnClient();
                                }
                            });
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (BoardInfo.getSocketServer() != null) {
                    for(Piece piece : BoardController.playerTwoPieces)
                        piece.setDisable(true);
                    while (true) {
                        try {
                            int newSpotLoc=0;
                            Spot spot = (Spot) BoardInfo.getSocketServer().readData();
                            for (int i=0 ; i< BoardController.spots.length ; i++) {
                                if (spot.getRow_number() == BoardController.spots[i].getRow_number() && spot.getColumn_number() == BoardController.spots[i].getColumn_number()) {
                                    BoardController.spots[i].setPiece(spot.getPiece());
                                    newSpotLoc=i;
                                    break;
                                }
                            }
                            int spotloc=0;
                            spot = (Spot) BoardInfo.getSocketServer().readData();
                            for (int i=0 ; i< BoardController.spots.length ; i++) {
                                if (spot.getRow_number() == BoardController.spots[i].getRow_number() && spot.getColumn_number() == BoardController.spots[i].getColumn_number()) {
                                    BoardController.spots[i].setPiece(null);
                                    spotloc=i;
                                    break;
                                }
                            }
                            Piece piece = (Piece) BoardInfo.getSocketServer().readData();
                            for (int i=0 ; i < BoardController.playerTwoPieces.length ; i++) {
                                if (BoardController.playerTwoPieces[i].getSpot().equals(BoardController.spots[spotloc])) {
                                    BoardController.playerTwoPieces[i].setSpot(BoardController.spots[newSpotLoc]);
                                    break;
                                }
                            }
                            ArrayList<Piece> pieces = (ArrayList<Piece>) BoardInfo.getSocketServer().readData();
                            removeThingsFromPane(pieces);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    BoardController boardController = new BoardController();
                                    boardController.changePlayerTurnServer();
                                }
                            });
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
        });
        receiveChanges.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        killableMoves = new ArrayList<>();
        successfulTurn=false;
    }

    /**
     * Responsible for drag and drop of Pieces
     * @param piece is the piece which is selected by user
     */
    public void movePiece(Piece piece){


        /**
         * Called When Mouse in pressed on a pieces
         * Checks which spots are good to move for piece
         * Records the initial piece X And Y Location
         */
         piece.setOnMousePressed(MouseEvent -> {

             mouseAnchorX = MouseEvent.getX();
             mouseAnchorY = MouseEvent.getY();
             original_piece_location_x = MouseEvent.getSceneX();
             original_piece_location_y = MouseEvent.getSceneY();

            this.piece = piece;
             if(piece.getSpot().getRow_number()==0 && piece.getPlayerAssociated().equals(BoardController.playerOne))
                 piece.setKing(true);
             if(piece.getSpot().getRow_number()==7 && piece.getPlayerAssociated().equals(BoardController.playerTwo))
                 piece.setKing(true);

             if(piece.isKing())
                 singleAvailableMoves = kingSingleMoves(piece.getSpot());
             else
                singleAvailableMoves = getSingleMoves(piece.getSpot());

             if(piece.isKing()){
                 getKing_KillableMovesInRight(piece.getSpot(),true);
                 getKing_KillableMovesInLeft(piece.getSpot(),true);
             }else {
                 getKillableMovesInRight(piece.getSpot(), true);
                 getKillableMovesInLeft(piece.getSpot(), true);
             }
             CheckWin.checkWinner();

             for(Spot spot : singleAvailableMoves)
                 spot.setFill(Color.RED);
             for(Spot spot : killableMoves)
                 spot.setFill(Color.RED);

         });

        /**
         * Called when a piece is dragged
         * Responsible for changing the Location of piece while in drag
         */
        piece.setOnMouseDragged(mouseEvent -> {
             piece.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
             piece.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
         });

        /**
         * Called when the mouse is released
         * Checks weather the pieces is move to right position or not
         * if(not) changes the player position to initial position
         */
         piece.setOnMouseReleased(mouseEvent -> {
             boolean moved = false;
             ArrayList<Piece> killablePiecesToSend = new ArrayList<>();

             int i=1;
             for(Spot spot : killableMoves){
                 double leftLayout = spot.getLayoutX()-135;
                 double rightLayout = spot.getLayoutX();
                 double topLayout = spot.getLayoutY()-135;
                 double bottomLayout = spot.getLayoutY();

                 if(mouseEvent.getSceneX()-100>leftLayout && mouseEvent.getSceneX()-100 < rightLayout && mouseEvent.getSceneY()-100 < bottomLayout && mouseEvent.getSceneY()-100 > topLayout){
                     moved=true;
                     oldSpot=piece.getSpot();
                     newSpot=spot;
                     piece.getSpot().setPiece(null);
                     piece.getSpot().setEmpty(true);
                     piece.setSpot(spot);
                     piece_to_be_moved=piece;
                     for(int k=0 ; k < i ; k++) {
                         piecesToBeKilled.get(k).getSpot().setPiece(null);
                         piecesToBeKilled.get(k).getSpot().setEmpty(true);
                         killablePiecesToSend.add(piecesToBeKilled.get(k));
                         board.getChildren().remove(piecesToBeKilled.get(k));
                     }
                     successfulTurn=true;
                     break;
                 }


                 i++;
             }
             if(!moved){
                 piece.setLayoutX(original_piece_location_x -mouseAnchorX);
                 piece.setLayoutY(original_piece_location_y -mouseAnchorY);
             }
             for(Spot spot : singleAvailableMoves){
                 double leftLayout = spot.getLayoutX()-135;
                 double rightLayout = spot.getLayoutX();
                 double topLayout = spot.getLayoutY()-135;
                 double bottomLayout = spot.getLayoutY();

                 if(mouseEvent.getSceneX()-100>leftLayout && mouseEvent.getSceneX()-100 < rightLayout && mouseEvent.getSceneY()-100 < bottomLayout && mouseEvent.getSceneY()-100 > topLayout){
                     moved=true;
                     oldSpot=piece.getSpot();
                     newSpot=spot;
                     piece.getSpot().setEmpty(true);
                     piece.getSpot().setPiece(null);
                     piece.setSpot(spot);
                     piece_to_be_moved=piece;
                     successfulTurn=true;
                     break;
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
                 if(BoardInfo.getSocketClient()!=null) {
                    try {
                        BoardInfo.getSocketClient().sendData(newSpot);
                        BoardInfo.getSocketClient().sendData(oldSpot);
                        BoardInfo.getSocketClient().sendData(piece_to_be_moved);
                        BoardInfo.getSocketClient().sendData(killablePiecesToSend);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(BoardInfo.getSocketServer()!=null){
                    try {
                        BoardInfo.getSocketServer().sendData(newSpot);
                        BoardInfo.getSocketServer().sendData(oldSpot);
                        BoardInfo.getSocketServer().sendData(piece_to_be_moved);
                        BoardInfo.getSocketServer().sendData(killablePiecesToSend);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                 BoardController boardController = new BoardController();

                 if(BoardInfo.getSocketClient()!=null)
                    boardController.changePlayerTurnClient();
                 else
                     boardController.changePlayerTurnServer();
             }

             killableMoves = new ArrayList<>();
             singleAvailableMoves = new ArrayList<>();
             piecesToBeKilled = new ArrayList<>();
             successfulTurn=false;
         });

     }

    /**
     * Returns the spots that are available for piece to move to
     * @param spot that is tile in which piece is initially placed
     * @return ArrayList of spots which are good to move
     */
     public ArrayList<Spot> getSingleMoves(Spot spot) {
         ArrayList<Spot> availableSpots = new ArrayList<>();
         int row_number = spot.getRow_number();
         int col_number = spot.getColumn_number();

         int checkRow = spot.getPiece().getPlayerAssociated().equals(BoardController.playerOne) ? -1 : 1;

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

    /**
     * finds the positions for piece to move while killing a enemy piece in right direction
     * @param spot that is tile in which piece is initially placed
     * @param firstTime that tells weather the function is called first time or not
     */
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

    /**
     * finds the positions for piece to move while killing a enemy piece in right direction
     * @param spot that is tile in which piece is initially placed
     * @param firstTime that tells weather the function is called first time or not
     */
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

    /**
     * Returns the spots that are available for King piece to move to
     * @param spot that is tile in which piece is initially placed
     * @return ArrayList of spots which are good to move
     */
    public ArrayList<Spot> kingSingleMoves(Spot spot){
        ArrayList<Spot> availableSpots = getSingleMoves(spot);

        int row_number = spot.getRow_number();
        int col_number = spot.getColumn_number();

        int checkRow = spot.getPiece().getPlayerAssociated().equals(BoardController.playerOne) ? 1 : -1;

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

    /**
     * Same as Getting killableMoves for normal piece
     * But for king Piece have to look in Both directions(up and down)
     */
    public void getKing_KillableMovesInRight(Spot spot,boolean firstTime){
        getKillableMovesInRight(spot,firstTime);

        int row_number = spot.getRow_number();
        int col_number = spot.getColumn_number();

        if (firstTime)
            checkKingRow = spot.getPiece().getPlayerAssociated().equals(BoardController.playerOne) ? 1 : -1;

        Spot spotToBeAdded = getRequiredSpot(row_number,col_number,checkKingRow,1);

        if(spotToBeAdded==null)
            return;

        if(!checkEmpty(spotToBeAdded)){
            if(spotToBeAdded.getPiece().getPlayerAssociated().equals(piece.getPlayerAssociated()))
                return;

            if(getRequiredSpot(row_number+checkKingRow,col_number+1,checkKingRow,1)!=null && checkEmpty(getRequiredSpot(row_number+checkKingRow,col_number+1,checkKingRow,1)) )
                piecesToBeKilled.add(spotToBeAdded.getPiece());
            for(Piece piece : piecesToBeKilled)

            spotToBeAdded = getRequiredSpot(row_number+checkKingRow , col_number+1, checkKingRow,1);
            if(spotToBeAdded==null)
                return;
            if(checkEmpty(spotToBeAdded)) {
                killableMoves.add(spotToBeAdded);
                getKing_KillableMovesInRight(spotToBeAdded,false);
                getKing_KillableMovesInLeft(spotToBeAdded,false);
            }
        }
    }

    /**
     * Same as Getting killableMoves for normal piece
     * But for king Piece have to look in Both directions(up and down)
     */
    public void getKing_KillableMovesInLeft(Spot spot,boolean firstTime){
        getKillableMovesInLeft(spot,firstTime);

        int row_number = spot.getRow_number();
        int col_number = spot.getColumn_number();

        if (firstTime)
            checkKingRow = spot.getPiece().getPlayerAssociated().equals(BoardController.playerOne) ? 1 : -1;

        Spot spotToBeAdded = getRequiredSpot(row_number,col_number,checkKingRow,-1);

        if(spotToBeAdded==null)
            return;

        if(!checkEmpty(spotToBeAdded)){
            if(spotToBeAdded.getPiece().getPlayerAssociated().equals(piece.getPlayerAssociated()))
                return;
            if(getRequiredSpot(row_number+checkKingRow,col_number-1,checkKingRow,-1)!=null && checkEmpty(getRequiredSpot(row_number+checkKingRow,col_number-1,checkKingRow,-1)))
                piecesToBeKilled.add(spotToBeAdded.getPiece());
            for(Piece piece : piecesToBeKilled)
            spotToBeAdded = getRequiredSpot(row_number+checkKingRow , col_number-1, checkKingRow,-1);
            if(spotToBeAdded==null)
                return;
            if(checkEmpty(spotToBeAdded)) {
                killableMoves.add(spotToBeAdded);
                getKing_KillableMovesInLeft(spotToBeAdded,false);
                getKing_KillableMovesInRight(spotToBeAdded,false);
            }
        }
    }

    /**
     * finds the specific spot that is required
     * @param row_number that tells the row number for the spot to find
     * @param col_number that tells the column number for the spot to find
     * @param checkRow tells weather to find spots in up or down direction
     * @param col_direction tell weather to find spots in right or left direction
     * @return Spot that if found
     */
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


    /**
     * Checks weather the spot is empty or not
     * @param spot that is to be checked
     * @return weather spot is empty or not
     */
     public boolean checkEmpty(Spot spot){
         return spot.getPiece() == null;
     }

     public void removeThingsFromPane(ArrayList<Piece> pieces){
         Platform.runLater(() -> {
             for(Piece piece : pieces) {
                 for (Piece piece1 : BoardController.playerOnePieces)
                     if (piece.getSpot().getRow_number()==(piece1.getSpot().getRow_number()) && piece.getSpot().getColumn_number()==piece1.getSpot().getColumn_number()) {
                         piece1.getSpot().setPiece(null);
                         piece1.getSpot().setEmpty(true);
                         board.getChildren().remove(piece1);
                     }
                 for (Piece piece1 : BoardController.playerTwoPieces)
                     if (piece.getSpot().getRow_number()==(piece1.getSpot().getRow_number()) && piece.getSpot().getColumn_number()==piece1.getSpot().getColumn_number()) {
                         piece1.getSpot().setPiece(null);
                         piece1.getSpot().setEmpty(true);
                         board.getChildren().remove(piece1);
                     }
             }
         });
     }


}

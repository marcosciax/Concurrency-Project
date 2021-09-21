package Checkers.Models;

import account_management.Models.Account;
import javafx.scene.shape.Circle;

import java.io.Serializable;

/**
 * Piece is a circle that can be of different colors to represent different players
 */
public class Piece extends Circle implements Serializable {

    private final Account playerAssociated;
    private boolean isKing;
    private Spot spot;

    public Piece(Spot spot , Account player){
        super.setRadius(50);
        setSpot(spot);
        spot.setPiece(this);
        super.setLayoutX(spot.getLayoutX()+63);
        super.setLayoutY(spot.getLayoutY()+63);
        this.playerAssociated=player;
        isKing=false;
    }

    public Spot getSpot() {
        return spot;
    }

    public Account getPlayerAssociated() {
        return playerAssociated;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
        super.setLayoutX(spot.getLayoutX()+63);
        super.setLayoutY(spot.getLayoutY()+63);
        spot.setPiece(this);
    }

    public void setKing(boolean king) {
        isKing = king;
    }

    public boolean isKing() {
        return isKing;
    }
}

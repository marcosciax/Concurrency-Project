package Checkers.Models;

import account_management.Models.Account;
import javafx.scene.shape.Circle;

public class Piece extends Circle {

    private final Account playerAssociated;
    private Spot spot;

    public Piece(Spot spot , Account player){
        super.setRadius(50);
        this.spot=spot;
        spot.setPiece(this);
        super.setLayoutX(spot.getLayoutX()+63);
        super.setLayoutY(spot.getLayoutY()+63);
        this.playerAssociated=player;
    }

    public Spot getSpot() {
        return spot;
    }

    public Account getPlayerAssociated() {
        return playerAssociated;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }
}

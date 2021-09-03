package ChessGame.Models;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece extends ImageView {

    Image image;
    boolean isSelected;

    public Piece(String address){
        isSelected=false;
        image = new Image(address,70,70,false,false); // ../images/BlackOnes/King.png
        super.setImage(image);
    }
}

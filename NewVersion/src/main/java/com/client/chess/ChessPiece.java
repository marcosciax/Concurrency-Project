package com.client.chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessPiece  {

    ImageView imageView;

    ChessPiece(String imageUrl){
        Image xImage = new Image(getClass().getResource(imageUrl).toExternalForm());
        imageView = new ImageView(xImage);
    }

    public ImageView getImageView(){
        return imageView;
    }

}

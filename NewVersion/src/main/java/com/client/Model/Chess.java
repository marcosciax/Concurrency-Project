package com.client.Model;

public class Chess {

    char[][] grids;

    public Chess(){
        this.grids = new char[][]{
                {'a','b','c','d','e','c','b','a'},
                {'q','q','q','q','q','q','q','q'},
                {'0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0'},
                {'r','r','r','r','r','r','r','r'},
                {'i','j','k','l','m','k','j','i'}
        };
    }

    public Chess(String chars){
        int i = 0;
        this.grids = new char[8][8];
        for(int row = 0 ; row < 8;row++){
            for(int col = 0;col < 8;col++){
                grids[row][col] = chars.charAt(i);
                i++;
            }
        }
    }
    public void move(int row,int col,int toRow,int toCol){
        char temp = grids[row][col];
        grids[row][col] = '0';
        grids[toRow][toCol] = temp;
    }

    public char get(int row ,int col){
        return grids[row][col];
    }

    public boolean isWhitePiece(int row,int col){
        return "rijklm".indexOf( grids[row][col]) != -1;
    }
    public boolean isBlackPiece(int row,int col){
        return "abcdeq".indexOf( grids[row][col]) != -1;
    }

    public char findWinner(){
        boolean foundWhiteKing = false;
        boolean foundBlackKing = false;
        for(int row = 0 ; row < 8;row++){
            for(int col = 0;col < 8;col++){
                if(grids[row][col] == 'd'){
                    foundBlackKing = true;
                }
                if(grids[row][col] == 'l'){
                    foundWhiteKing = true;
                }
            }
        }

        if(foundBlackKing && foundWhiteKing){
            return  'D';
        }
        if(foundBlackKing){
            return 'B';
        }
        if(foundWhiteKing){
            return 'W';
        }
        return 'D';
    }

    public String getStrs(){
        String str = "";
        for(int row = 0; row < 8;row++){
            for(int col = 0; col < 8; col++){
                str += grids[row][col];
            }
        }
        return str;
    }

}

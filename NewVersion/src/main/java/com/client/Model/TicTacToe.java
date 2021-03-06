package com.client.Model;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe {

    char[][] grids;

    public TicTacToe(){
        this.grids = new char[][]{
                {'0','0','0'},
                {'0','0','0'},
                {'0','0','0'}
        };
    }
    public TicTacToe(String chars){
        this.grids = new char[][]{
                {chars.charAt(0),chars.charAt(1),chars.charAt(2)},
                {chars.charAt(3),chars.charAt(4),chars.charAt(5)},
                {chars.charAt(6),chars.charAt(7),chars.charAt(8)}
        };
    }

    // 0 is none][ 1 is x][ 2 is o][ 3 is equal
    public void add(int row, int col, char val){
        grids[row][col] = val;
    }


    public char getAt(int row,int col){
        return grids[row][col];
    }

    public int count(char c){
        int count = 0;
        for(int row = 0; row < 3;row++){
            for(int col = 0; col < 3; col++){
                if(grids[row][col] == c){
                    count++;
                }
            }
        }
        return count;
    }

    public String getStrs(){
        String str = "";
        for(int row = 0; row < 3;row++){
            for(int col = 0; col < 3; col++){
                str += grids[row][col];
            }
        }
        return str;
    }

    public char findWinner()
    {
        char check = ValidateMatrix(grids);
        return check;
    }

    private char ValidateMatrix(char[][] matrix)
    {
        char defaultValue = '0';
        //First row
        if(matrix[0][0] != defaultValue && matrix[0][0] == matrix[0][1] && matrix[0][0] == matrix[0][2])
        {
            return matrix[0][0];
        }

        //Second row
        if (matrix[1][0] != defaultValue &&  matrix[1][0] == matrix[1][1] && matrix[1][0] == matrix[1][2])
        {
            return matrix[1][0];
        }

        //Third row
        if (matrix[2][ 0] != defaultValue && matrix[2][ 0] == matrix[2][ 1] && matrix[2][ 0] == matrix[2][ 2])
        {
            return matrix[2][ 0];
        }

        //First Column
        if (matrix[0][ 0] != defaultValue && matrix[0][ 0] == matrix[1][ 0] && matrix[0][ 0] == matrix[2][ 0])
        {
            return matrix[0][ 0];
        }

        //Second Column
        if (matrix[0][ 1] != defaultValue && matrix[0][ 1] == matrix[1][ 1] && matrix[0][ 1] == matrix[2][ 1])
        {
            return matrix[0][ 1];
        }

        //Third Column
        if (matrix[0][ 2] != defaultValue && matrix[0][ 2] == matrix[1][ 2] && matrix[0][ 2] == matrix[2][ 2])
        {
            return matrix[0][ 2];
        }

        //Diagonal
        if (matrix[0][ 0] != defaultValue && matrix[0][ 0] == matrix[1][ 1] && matrix[0][ 0] == matrix[2][ 2])
        {
            return matrix[0][ 0];
        }

        //Diagonal
        if (matrix[0][ 2] != defaultValue && matrix[0][ 2] == matrix[1][ 1] && matrix[0][ 2] == matrix[2][ 0])
        {
            return matrix[0][ 2];
        }

        //Check if any block is available to play
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if(matrix[i][j] == defaultValue)
                {
                    return 'P';
                }
            }
        }
        return 'D';
    }




}

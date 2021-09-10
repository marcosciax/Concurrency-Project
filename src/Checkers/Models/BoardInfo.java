package Checkers.Models;

import account_management.Models.Account;

import java.util.Random;

public class BoardInfo {

    private int boardId;
    private Account playerOne;
    private Account playerTwo;

    public BoardInfo(Account playerOne , Account playerTwo){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        setBoardId();
    }

    public Account getPlayerOne() {
        return playerOne;
    }

    public Account getPlayerTwo() {
        return playerTwo;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId() {
        Random random = new Random();
        this.boardId = random.nextInt();
    }

    public void setPlayerTwo(Account playerTwo) {
        this.playerTwo = playerTwo;
    }

    public void setPlayerOne(Account playerOne) {
        this.playerOne = playerOne;
    }
}

package ChessGame.Models;

import ChessGame.Controller.BoardController;
import account_management.Models.Account;

import java.util.Random;

public class Chess {

    private int gameId;
    private Account playerOne;
    private Account playerTwo;
    BoardController board;

    public Chess(BoardController board , Account playerOne, Account playerTwo){
        this.board=board;
        this.playerOne=playerOne;
        this.playerTwo=playerTwo;
        setGameId();
    }

    public Account getPlayerOne() {
        return playerOne;
    }

    public Account getPlayerTwo() {
        return playerTwo;
    }

    public BoardController getBoard() {
        return board;
    }

    public int getGameId() {
        return gameId;
    }

    public void setBoard(BoardController board) {
        this.board = board;
    }

    public void setGameId() {
        Random random = new Random();
        this.gameId = random.nextInt(1000);
    }

    public void setPlayerOne(Account playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(Account playerTwo) {
        this.playerTwo = playerTwo;
    }
}

package com.client.Model;

import java.util.Random;
import java.util.UUID;

public class TicTacToeRoom {

    private String fromUser;
    private String toUser;
    private TicTacToe ticTacToe;
    private String status;
    private String winner;
    private int id;
    private String playFirst;

    private static int AUTOID = 0;

    public TicTacToeRoom(String fromUser, String toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.ticTacToe = new TicTacToe();
        this.status = "PLAYING";
        id  = AUTOID;
        AUTOID++;
        Random r = new Random();
        if(r.nextInt(10) % 2 == 0){
            playFirst = fromUser;
        }
        else{
            playFirst = toUser;
        }
    }

    public String getEnemy(String user){
        if(user.equals(fromUser)){
            return toUser;
        }
        if(user.equals(toUser)){
            return fromUser;
        }
        return "";
    }

    public String getPlayFirst(){
        return playFirst;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public TicTacToe getTicTacToe() {
        return ticTacToe;
    }

    public void setTicTacToe(TicTacToe ticTacToe) {
        this.ticTacToe = ticTacToe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TicTacToeRoom{" +
                "fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", ticTacToe=" + ticTacToe +
                ", status='" + status + '\'' +
                ", winner='" + winner + '\'' +
                ", id=" + id +
                ", playFirst='" + playFirst + '\'' +
                '}';
    }
}

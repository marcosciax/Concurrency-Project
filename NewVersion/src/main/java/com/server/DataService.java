package com.server;

import com.client.Model.ChessRoom;
import com.client.Model.TicTacToe;
import com.client.Model.TicTacToeRoom;

import java.util.ArrayList;
import java.util.List;

public class DataService {

    private static DataService _instance = null;

    public static DataService getInstance()
    {
        if (_instance == null)
            _instance = new DataService();

        return _instance;
    }
    private List<HandleClient> clients = new ArrayList<>();
    private List<TicTacToeRoom> ticTacToesRooms = new ArrayList<>();
    private List<ChessRoom> chessRooms = new ArrayList<>();

    public List<ChessRoom> getChessRooms() {
        return chessRooms;
    }

    public void setChessRooms(List<ChessRoom> chessRooms) {
        this.chessRooms = chessRooms;
    }

    public TicTacToeRoom getTicTacToeRoom(int id){
        for(TicTacToeRoom r : ticTacToesRooms){
            if(r.getId() == id){
                return r;
            }
        }
        return null;
    }
    public ChessRoom getChessRoom(int id){
        for(ChessRoom r : chessRooms){
            if(r.getId() == id){
                return r;
            }
        }
        return null;
    }

    public TicTacToeRoom getTicTacToePlayingRoom(String u1, String u2){
        for(TicTacToeRoom r : ticTacToesRooms){
            if(
                    ( r.getFromUser().equals(u1) || r.getFromUser().equals(u2) )
                &&  ( r.getToUser().equals(u1) || r.getToUser().equals(u2)     )
                &&    r.getStatus().equals("PLAYING")){
                return r;
            }
        }
        return null;
    }

    public ChessRoom getTChessPlayingRoom(String u1, String u2){
        for(ChessRoom r : chessRooms){
            if(
                    ( r.getFromUser().equals(u1) || r.getFromUser().equals(u2) )
                            &&  ( r.getToUser().equals(u1) || r.getToUser().equals(u2)     )
                            &&    r.getStatus().equals("PLAYING")){
                return r;
            }
        }
        return null;
    }


    public List<TicTacToeRoom> getTicTacToesRooms() {
        return ticTacToesRooms;
    }

    public void setTicTacToesRooms(List<TicTacToeRoom> ticTacToesRooms) {
        this.ticTacToesRooms = ticTacToesRooms;
    }

    public List<HandleClient> getClients() {
        return clients;
    }

    public HandleClient getClient(int id){
        for(int i= 0; i < clients.size();i++){
            if(id == clients.get(i).getClientId()){
                return clients.get(i);
            }
        }
        return null;
    }

    public HandleClient getClient(String username){
        for(int i= 0; i < clients.size();i++){
            if(username.equals(clients.get(i).getUsername())){
                return clients.get(i);
            }
        }
        return null;
    }

    public void setClients(List<HandleClient> clients) {
        this.clients = clients;
    }
}

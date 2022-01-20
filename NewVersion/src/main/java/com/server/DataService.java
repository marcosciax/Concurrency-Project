package com.server;

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

    public TicTacToeRoom getTicTacToeRoom(int id){
        for(TicTacToeRoom r : ticTacToesRooms){
            if(r.getId() == id){
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

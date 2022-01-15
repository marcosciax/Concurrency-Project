package com.server;

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

    public void setClients(List<HandleClient> clients) {
        this.clients = clients;
    }
}

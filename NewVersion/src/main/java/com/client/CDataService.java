package com.client;

import com.client.Model.Message;

import java.util.HashMap;
import java.util.List;

public class CDataService {

    private static CDataService _instance = null;

    public static CDataService getInstance()
    {
        if (_instance == null)
            _instance = new CDataService();

        return _instance;
    }

    HashMap<String, List<Message>> messagesMap;
    String username;

    CDataService(){
        messagesMap = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<String, List<Message>> getMessagesMap() {
        return messagesMap;
    }

    public void setMessagesMap(HashMap<String, List<Message>> messagesMap) {
        this.messagesMap = messagesMap;
    }
}

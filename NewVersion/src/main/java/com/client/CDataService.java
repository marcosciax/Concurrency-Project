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

    CDataService(){
        messagesMap = new HashMap<>();
    }

}

package com.client.threads;

import com.client.controller.MainController;
import com.client.NetworkService;
import javafx.application.Platform;

public class WaitForMessageThread extends Thread {
    MainController mainController;

    public WaitForMessageThread(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void run() {

        while (true){
            String message = NetworkService.getInstance().receiveMessage();
            System.out.println("Server: " + message);

            String[] responses = message.split("=");
            if(responses.length < 1){
                continue;
            }
            String command = responses[0];

            if(command.equals("USERS")){
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        mainController.fetchOnlineUsers(message);
                    }
                });
            }

        }

    }
}

package com.client.controller;

import javafx.fxml.FXML;

import java.io.IOException;

public class ChatBoxController {

    String toUser;

    public ChatBoxController(String toUser) {
        this.toUser = toUser;
    }

    @FXML
    public void initialize() throws IOException {

    }
}

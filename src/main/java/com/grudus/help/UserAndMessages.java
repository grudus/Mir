package com.grudus.help;


import com.grudus.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class UserAndMessages {
    private String user;
    private List<Message> messages;

    public UserAndMessages() {
        user = "anonymous";
        messages = new ArrayList<>();
    }

    public UserAndMessages(String user, List<Message> messages) {
        this.user = user;
        this.messages = messages;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "UserAndMessages{" +
                "user='" + user + '\'' +
                ", messages=" + messages +
                '}';
    }
}

package com.grudus.help;


import com.grudus.entities.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAndMessages {
    private String user;
    private List<String> plusMessageIds, minusMessageIds;
    private List<Message> messages;
    private boolean logged = false;

    public UserAndMessages() {
        user = "anonymous";
        messages = new ArrayList<>();
        plusMessageIds = Collections.emptyList();
        minusMessageIds = Collections.emptyList();
    }

    public boolean getLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        logged = logged;
    }

    public UserAndMessages(String user, List<Message> messages, boolean isLogged) {
        this.user = user;
        this.messages = messages;
        this.logged = isLogged;

    }

    public UserAndMessages(String user, List<String> plusMessageIds, List<String> minusMessageIds, List<Message> messages) {
        this.user = user;
        this.plusMessageIds = plusMessageIds;
        this.minusMessageIds = minusMessageIds;
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

    public List<String> getPlusMessageIds() {
        return plusMessageIds;
    }

    public void setPlusMessageIds(List<String> plusMessageIds) {
        this.plusMessageIds = plusMessageIds;
    }

    public List<String> getMinusMessageIds() {
        return minusMessageIds;
    }

    public void setMinusMessageIds(List<String> minusMessageIds) {
        this.minusMessageIds = minusMessageIds;
    }

    @Override
    public String toString() {
        return "UserAndMessages{" +
                "user='" + user + '\'' +
                ", plusMessageIds=" + plusMessageIds +
                ", minusMessageIds=" + minusMessageIds +
                ", messages=" + messages +
                (logged ?", logged=" + logged + '}' : '}');
    }
}

package com.grudus.help;


import com.grudus.entities.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAndMessages {
    private String user;
    private List<String> plusMessageIds, minusMessageIds;
    private List<Message> messages;

    public UserAndMessages() {
        user = "anonymous";
        messages = new ArrayList<>();
        plusMessageIds = Collections.emptyList();
        minusMessageIds = Collections.emptyList();
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
}

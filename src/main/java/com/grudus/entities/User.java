package com.grudus.entities;

import com.grudus.help.MessageHelp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {

    @Field
    private String login, password, email;
    @Field
    private List<String> plusMessageIds, minusMessageIds;


    public User(String login, String password, String email, List<String> plusMessageIds, List<String> minusMessageIds) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.plusMessageIds = plusMessageIds;
        this.minusMessageIds = minusMessageIds;
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        plusMessageIds = new ArrayList<>();
        minusMessageIds = new ArrayList<>();
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void vote(final String id, final int voteType, final boolean isUp) {
        System.out.println("User vote()");
        if (voteType == 1) {
            if (isUp && !plusMessageIds.contains(id)) {
                plusMessageIds.add(id);
            }
            else plusMessageIds.remove(id);
        }
        else {
            if (isUp && !minusMessageIds.contains(id)) minusMessageIds.add(id);
            else minusMessageIds.remove(id);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", plusMessageIds=" + plusMessageIds +
                ", minusMessageIds=" + minusMessageIds +
                '}';
    }


}

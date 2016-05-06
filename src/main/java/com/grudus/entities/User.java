package com.grudus.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grudus.help.MessageHelp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {

    @Field
    @Id
    private String _id;
    @Field
    private String login, email;
    @Field
    @JsonIgnore
    private String password;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (_id != null ? !_id.equals(user._id) : user._id != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (plusMessageIds != null ? !plusMessageIds.equals(user.plusMessageIds) : user.plusMessageIds != null)
            return false;
        return minusMessageIds != null ? minusMessageIds.equals(user.minusMessageIds) : user.minusMessageIds == null;

    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (plusMessageIds != null ? plusMessageIds.hashCode() : 0);
        result = 31 * result + (minusMessageIds != null ? minusMessageIds.hashCode() : 0);
        return result;
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

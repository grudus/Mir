package com.grudus.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "messages")
public class Message {
    @Field
    @Id
    private String _id;

    @Field
    private String message;
    @Field
    private List<String> tags;
    @Field
    private String author;
    @Field
    private LocalDateTime date;
    @Field
    private Integer plus;
    @Field
    private Integer minus;

    public Message(String message, List<String> tags, String author, LocalDateTime date, Integer plus, Integer minus) {
        this.message = message;
        this.tags = tags;
        this.author = author;
        this.date = date;
        this.plus = plus;
        this.minus = minus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getPlus() {
        return plus;
    }

    public void setPlus(Integer plus) {
        this.plus = plus;
    }

    public Integer getMinus() {
        return minus;
    }

    public void setMinus(Integer minus) {
        this.minus = minus;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void vote(final int how) {
        if (how == 0) minus++;
        else plus++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (_id != null ? !_id.equals(message1._id) : message1._id != null) return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;
        if (tags != null ? !tags.equals(message1.tags) : message1.tags != null) return false;
        if (author != null ? !author.equals(message1.author) : message1.author != null) return false;
        if (date != null ? !date.equals(message1.date) : message1.date != null) return false;
        if (plus != null ? !plus.equals(message1.plus) : message1.plus != null) return false;
        return minus != null ? minus.equals(message1.minus) : message1.minus == null;

    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (plus != null ? plus.hashCode() : 0);
        result = 31 * result + (minus != null ? minus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", tags='" + tags + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                ", plus=" + plus +
                ", minus=" + minus +
                '}';
    }

}



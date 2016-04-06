package com.grudus.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document(collection = "messages")
public class Message {
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



package com.grudus.controllers;

import com.grudus.dao.MessageRepository;
import com.grudus.entities.Message;
import com.grudus.help.MessageHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class MessageController {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @RequestMapping(value = "/dupa", method = RequestMethod.GET)
    public List<Message> dupa() {
        return messageRepository.findAll();
    }


    @RequestMapping(value = "/dupa", method = RequestMethod.POST)
    public String atera(@RequestParam String message, @RequestParam String author) {
        System.out.println("wejszlo " + message + " - " + author);
        final LocalDateTime date = LocalDateTime.now();
        final ArrayList<String> tags = MessageHelp.findTags(message);
        final Message newMessage = new Message(message, tags, author, date, 0, 0);

        messageRepository.save(newMessage);
        return "Wyszlo z posta";

    }

}


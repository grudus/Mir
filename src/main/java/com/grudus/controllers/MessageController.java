package com.grudus.controllers;

import com.grudus.dao.MessageRepository;
import com.grudus.entities.Message;
import com.grudus.help.MessageHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MessageController {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping(value = "/dupa", method = RequestMethod.GET, produces = "application/json")
    public List<Message> dupa() {
        return messageRepository.findAll();
    }


    @RequestMapping(value = "/dupa", method = RequestMethod.POST)
    public void atera(String message, String author, HttpServletResponse response) throws IOException {
        System.out.println("No i jak to dziala? " + message + " - " + author);
        final LocalDateTime date = LocalDateTime.now();
        final ArrayList<String> tags = MessageHelp.findTags(message);
        final Message newMessage = new Message(message, tags, author, date, 0, 0);

        messageRepository.save(newMessage);


        response.sendRedirect("http://localhost:8080/dupa");

    }


}

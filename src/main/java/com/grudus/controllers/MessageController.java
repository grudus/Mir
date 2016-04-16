package com.grudus.controllers;

import com.grudus.dao.MessageRepository;
import com.grudus.entities.Message;
import com.grudus.help.Login;
import com.grudus.help.MessageHelp;
import com.grudus.help.UserAndMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class MessageController {

    private final MessageRepository messageRepository;
    private Login loginHelper;

    @Autowired
    @Qualifier("login")
    public void setLoginHelper(Login loginHelper) {
        this.loginHelper = loginHelper;
    }

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @RequestMapping(value = "/dupa", method = RequestMethod.GET)
    public UserAndMessages dupa() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAndMessages toReturn = new UserAndMessages();
        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).forEach(System.out::println);
        if (authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                // In our case user can only be USER or ANONYMOUS
                .anyMatch(n -> n.equals("ROLE_USER"))) {
            toReturn.setUser(((User)authentication.getPrincipal()).getUsername());
        }
        toReturn.setMessages(messageRepository.findAll());
        //TODO change it to something else
        loginHelper.setCurrentState(Login.OK);
        return toReturn;
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


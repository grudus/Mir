package com.grudus.controllers;

import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import com.grudus.entities.Message;
import com.grudus.help.*;
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
    private final UserRepository userRepository;
    private final MongoOperationsImpl mongoOperations;
    private LoginHelp loginHelper;
    private WaitingUsersHelp waitingUsersHelp;
    private MessageHelp messageHelp;

    @Autowired
    @Qualifier("login")
    public void setLoginHelper(LoginHelp loginHelper) {
        this.loginHelper = loginHelper;
    }

    @Autowired
    public MessageController(MessageRepository messageRepository, UserRepository userRepository, MongoOperationsImpl mongoOperations) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.mongoOperations = mongoOperations;
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
            final String userName = ((User)authentication.getPrincipal()).getUsername();
            final com.grudus.entities.User user = userRepository.findByLogin(userName).orElseThrow(() -> new RuntimeException("Can't find the user"));
            toReturn.setUser(userName);
            toReturn.setPlusMessageIds(user.getPlusMessageIds());
            toReturn.setMinusMessageIds(user.getMinusMessageIds());
        }
        toReturn.setMessages(messageRepository.findAll());
        loginHelper.setCurrentState(LoginHelp.OK);
        return toReturn;
    }


    @RequestMapping(value = "/dupa", method = RequestMethod.POST)
    public void saveNewMessage(@RequestParam String message, @RequestParam String author) {
        final LocalDateTime date = LocalDateTime.now();
        final ArrayList<String> tags = MessageHelp.findTags(message);
        final Message newMessage = new Message(message, tags, author, date, 0, 0);

        messageRepository.save(newMessage);
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public void getVote(@RequestParam String id, @RequestParam int vote /*plus=1,minus=0*/, @RequestParam String user) {
        if (!UserHelp.isLogged(user) || (vote != 0 && vote != 1) || messageRepository.findOne(id) == null) {
            System.err.println("Cannot vote");
            return;
        }

        System.out.println(user + " vote " + vote + " to message " + messageRepository.findOne(id).getMessage());
        messageHelp.vote(id, messageRepository, user, userRepository, vote);
    }


    // ________ SETTERS ___________________
    @Autowired
    public void setWaitingUsersHelp(WaitingUsersHelp waitingUsersHelp) {
        this.waitingUsersHelp = waitingUsersHelp;
    }

    @Autowired
    public void setMessageHelp(MessageHelp messageHelp) {
        this.messageHelp = messageHelp;
    }
}


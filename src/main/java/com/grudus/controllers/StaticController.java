package com.grudus.controllers;

import com.grudus.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

@Controller
public class StaticController {

    private final UserRepository userRepository;

    @Autowired
    public StaticController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/dupa", method = RequestMethod.GET, produces = "text/html")
    public String goToIndexHtml() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "text/html")
    public String goToLoginHtml() {
        return "login";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = "text/html")
    public String goToCreateHtml() {return "create";}

    @RequestMapping(value = "/{login}", method = RequestMethod.GET, produces = "text/html")
    public String goToUserHtml(@PathVariable("login") String login) {
        if (!userRepository.findByLogin(login).isPresent())
            return "error";

        return "user";
    }

}

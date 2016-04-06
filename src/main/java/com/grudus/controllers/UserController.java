package com.grudus.controllers;

import com.grudus.dao.UserRepository;
import com.grudus.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/user={login}")
    public User userData(@PathVariable("login") String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Cannot find the user"));
    }
}

package com.grudus.controllers;

import com.grudus.dao.UserRepository;
import com.grudus.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @RequestMapping("/admin")
    public String admin() {
        return "Welcome in the admin panel";
    }

    @RequestMapping("/login")
    public String login() {
        return "You're now in the login page";
    }

    @RequestMapping("/logout")
    public void accessDenied(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        response.sendRedirect("/login?logout");

    }
}

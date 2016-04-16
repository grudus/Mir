package com.grudus.controllers;

import com.grudus.dao.UserRepository;
import com.grudus.entities.User;
import com.grudus.help.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private Login loginHelper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void setLoginHelper(Login loginHelper) {
        this.loginHelper = loginHelper;
    }

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/{user}")
    public User userData(@PathVariable("user") String user) {
        return userRepository.findByLogin(user).orElseThrow(() -> new RuntimeException("Cannot find the user"));
    }

    @RequestMapping("/admin")
    public String admin() {
        return "Welcome in the admin panel";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return loginHelper.getCurrentState();
    }



    @RequestMapping(value = "/checkUsers", method = RequestMethod.GET)
    public String checkUsers(@RequestParam String login) {
        return (userRepository.findByLogin(login).isPresent() ? Login.CREATE_ACCOUNT_LOGIN_TAKEN : Login.OK);
    }

    //TODO CHANGE EVERYTHING!

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void addUser(@RequestParam String login, @RequestParam String password, @RequestParam String email, HttpServletResponse response) throws IOException {
        final User newUser = new User(login, passwordEncoder.encode(password), email);
        userRepository.save(newUser);
        ArrayList<GrantedAuthority> roles = new ArrayList<>(1);
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(newUser.getLogin(), newUser.getPassword(), roles),
                null, roles);
        SecurityContextHolder.getContext().setAuthentication(auth);
        response.sendRedirect("/login");
    }

    @RequestMapping(value = "/err", method = RequestMethod.GET)
    public void err(HttpServletResponse response) throws IOException {
        loginHelper.setCurrentState(Login.ERROR);
        response.sendRedirect("/login?error");
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

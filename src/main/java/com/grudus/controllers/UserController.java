package com.grudus.controllers;

import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import com.grudus.dao.WaitingUserRepository;
import com.grudus.entities.Message;
import com.grudus.entities.User;
import com.grudus.entities.WaitingUser;
import com.grudus.help.EmailSender;
import com.grudus.help.LoginHelp;
import com.grudus.help.UserHelp;
import com.grudus.help.WaitingUsersHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final WaitingUserRepository waitingUserRepository;
    private final MessageRepository messageRepository;

    private WaitingUsersHelp waitingUsersHelp;
    private LoginHelp loginHelper;
    private EmailSender emailSender;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserController(UserRepository userRepository, WaitingUserRepository waitingUserRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.waitingUserRepository = waitingUserRepository;
        this.messageRepository = messageRepository;
        emailSender = new EmailSender();
    }

    @RequestMapping("/{user}")
    public User userData(@PathVariable("user") String user) {
        return userRepository.findByLogin(user).orElseThrow(() -> new RuntimeException("Cannot find the user"));
    }

    @RequestMapping("/{login}/vote={pum}")
    public List<Message> getVotedMessages(@PathVariable String login, @PathVariable String pum) {
        if (!UserHelp.isLogged(login)) {
            System.err.println(login + " isn't logged");
            return null;
        }
        if (!pum.equals("plus") && !pum.equals("minus")) {
            System.err.println("/{user}/vote=[minus|plus]");
            return null;
        }
        return pum.equals("plus") ? userRepository.findByLogin(login).get()
                .getPlusMessageIds()
                .stream()
                .map(n -> messageRepository.findOne(n))
                .collect(Collectors.toList())
                : userRepository.findByLogin(login).get()
                .getMinusMessageIds()
                .stream()
                .map(n -> messageRepository.findOne(n))
                .collect(Collectors.toList());
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
        return (userRepository.findByLogin(login).isPresent() ? LoginHelp.CREATE_ACCOUNT_LOGIN_TAKEN : LoginHelp.OK);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void addUser(@RequestParam String login, @RequestParam String password, @RequestParam String email, HttpServletResponse response) throws IOException {
        System.out.println(login);
        if (!LoginHelp.checkConditionsBeforeCreatingAccount(login, password, email, userRepository)) {
            System.err.println("Cannot create an account!");
            response.sendRedirect("/err");
            return;
        }
        WaitingUser waitingUser = new WaitingUser(login, passwordEncoder.encode(password), email, emailSender.getURL(), LocalDateTime.now());
        waitingUsersHelp.addToQueue(waitingUser, waitingUserRepository);
        try {
            emailSender.send("Hello, " + login + ". To complete your registration click this link: http://localhost:8080/created-" + waitingUser.getKey(),
                    email);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Nie udalo sie wyslac wiadomosci na adres " + email);
            response.sendRedirect("/create");
        }
        finally {response.sendRedirect("/dupa");}
    }

    @RequestMapping(value = "/created-{key}")
    public void newUserConfirmed(HttpServletResponse response, @PathVariable("key") String key) throws IOException {
        WaitingUser waitingUser = waitingUserRepository.findByKey(key).orElseThrow(() -> new RuntimeException("Cannot find the user"));
        final User newUser = WaitingUsersHelp.cantWaitAnyLonger(waitingUser);
        waitingUserRepository.delete(waitingUser);
        System.out.println("mam usera " + newUser);
        userRepository.save(newUser);
        ArrayList<GrantedAuthority> roles = new ArrayList<>(1);
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(newUser.getLogin(), newUser.getPassword(), roles),
                null, roles);
        SecurityContextHolder.getContext().setAuthentication(auth);
        response.sendRedirect("/dupa");
    }

    @RequestMapping(value = "/err", method = RequestMethod.GET)
    public void err(HttpServletResponse response) throws IOException {
        loginHelper.setCurrentState(LoginHelp.ERROR);
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



    // ______________ SETTERS _________________
    @Autowired
    public void setLoginHelper(LoginHelp loginHelper) {
        this.loginHelper = loginHelper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setWaitingUsersHelp(WaitingUsersHelp waitingUsersHelp) {
        this.waitingUsersHelp = waitingUsersHelp;
    }


}

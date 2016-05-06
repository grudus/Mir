package com.grudus.controllers;

import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import com.grudus.dao.WaitingUserRepository;
import com.grudus.entities.Message;
import com.grudus.entities.User;
import com.grudus.entities.WaitingUser;
import com.grudus.error.UserNotFoundException;
import com.grudus.help.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
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


    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public UserAndMessages userData(@PathVariable("login") String login, Principal principal, HttpServletResponse response) throws UserNotFoundException {
        System.out.println("lohin w rest");
        if (!userRepository.findByLogin(login).isPresent()) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            throw new UserNotFoundException(login);
        }
        return new UserAndMessages(login, messageRepository.findByAuthor(login),
                principal != null && principal.getName().equals(login));
    }

    //ta, powinno byc delete
    @RequestMapping(value = "/{login}", method = RequestMethod.POST)
    public void rmMessage(@RequestParam String id, @PathVariable("login") String login, Principal principal) {
        System.out.println("post " + id);
        if (principal == null || !principal.getName().equals(login))
            System.err.println("User isn't online");

        else messageRepository.delete(id);
    }

    @RequestMapping(value = "/{login}/removeAccount", method = RequestMethod.POST)
    public void rmUser(@PathVariable("login") String login, Principal principal) {
        if (principal == null || !principal.getName().equals(login))
            throw new UserNotFoundException(login);
        else {
            userRepository.deleteByLogin(login);
            List<Message> messages = messageRepository.findByAuthor(login);
            messages.forEach(n -> messageRepository.delete(n));
        }
    }

    @RequestMapping("/{login}/vote={pum}")
    public List<Message> getVotedMessages(@PathVariable String login, @PathVariable String pum, Principal principal) {
        if (principal == null || !principal.getName().equals(login)) {
            throw new UserNotFoundException(login);
        }
        if (!pum.equals("plus") && !pum.equals("minus")) {
            System.err.println("/{login}/vote=[minus|plus]");
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
        try {
            emailSender.send("Hello, " + login + ". To complete your registration click this link: http://localhost:8080/created-" + waitingUser.getKey(),
                    email);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Nie udalo sie wyslac wiadomosci na adres " + email);
            response.sendRedirect("/create");
        }
        waitingUsersHelp.addToQueue(waitingUser, waitingUserRepository);
        response.sendRedirect("/dupa");
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

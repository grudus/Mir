package com.grudus.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "bad login")
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super();
        System.err.println("CANNOT FIND THE USER");
    }

    public UserNotFoundException(String login) {
        super("CANNOT FIND THE USER " + login);
        System.err.println("CANNOT FIND THE USER " + login);
    }




}

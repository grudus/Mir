package com.grudus.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @RequestMapping("/dupa")
    public String dupa(HttpServletRequest request) {
        return "duupa " + request.getProtocol();
    }
    
}

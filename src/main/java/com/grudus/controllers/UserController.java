package com.grudus.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @RequestMapping(value = "/dupa", method = RequestMethod.GET, produces = "application/json")
    public String dupa() {
        return "duupa";
    }
}

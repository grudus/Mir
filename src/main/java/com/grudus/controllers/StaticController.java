package com.grudus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StaticController {

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

}

package com.grudus.error;

import com.grudus.controllers.UserController;
import com.grudus.help.UserAndMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice(basePackageClasses = UserController.class)
public class ErrorControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String handleControllerException(HttpServletRequest request, Throwable ex, HttpServletResponse response) throws IOException {
        System.err.println("handleControllerException");
        return "bad";
    }

}


package com.grudus.help;

import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("login")
@Scope(value = "session")
public class Login {
    public static final String OK = "ok";
    public static final String ERROR = "Something was wrong. Please try again";
    public static final String CREATE_ACCOUNT_LOGIN_TAKEN = "Login is already taken";

    private String currentState = OK;
    private String createAccountLoginStatus = OK;


    public Login() {
        System.out.println("Konstruktor Login");
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(final String state) {
        this.currentState = state;
    }

    public String getCreateAccountLoginStatus() {
        return createAccountLoginStatus;
    }

    public void setCreateAccountLoginStatus(String createAccountLoginStatus) {
        this.createAccountLoginStatus = createAccountLoginStatus;
    }
}

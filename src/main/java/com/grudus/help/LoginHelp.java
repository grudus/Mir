package com.grudus.help;

import com.grudus.dao.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("login")
@Scope(value = "session")
public class LoginHelp {
    public static final String OK = "ok";
    public static final String ERROR = "Something was wrong. Please try again";
    public static final String CREATE_ACCOUNT_LOGIN_TAKEN = "LoginHelp is already taken";

    private String currentState = OK;
    private String createAccountLoginStatus = OK;


    public LoginHelp() {
        System.out.println("Konstruktor LoginHelp");
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

    public static boolean checkConditionsBeforeCreatingAccount(String login, String password, String email, UserRepository repository) {
        if (login == null || password == null || email == null || login.isEmpty()) System.err.println("nulle");
        if (password.length() < 8) System.err.println("Haslo");
        if (!EmailValidator.getInstance().isValid(email)) System.err.println("Nie maczes");
        if (repository.findByLogin(login).isPresent()) System.err.println("istnieje");


        return !(login == null || password == null || email == null
        || login.isEmpty() || password.length() < 8
        || !EmailValidator.getInstance().isValid(email)
        || repository.findByLogin(login).isPresent());
    }
}

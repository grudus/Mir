package com.grudus.help;


import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserHelp {


    public static boolean isLogged(final String userName) {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
        && ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername().equals(userName);
    }

}

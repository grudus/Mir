package com.grudus.help;


import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserHelp {

/*

    public static boolean isOnline(final String userName) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            System.err.println("Nima authenticationa");
            return false;
        }
        if (! SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            System.err.println("Nie authenticated");
            return false;
        }
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            System.err.println("anonimowo");
            return false;
        }

        if (! ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername().equals(userName)) {
            System.err.println("Nie equals");
            return false;
        }
        return true;
    }
*/

}

package com.grudus.help;

import com.grudus.dao.WaitingUserRepository;
import com.grudus.entities.User;
import com.grudus.entities.WaitingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WaitingUsersHelp {

    @Autowired
    private MongoOperationsImpl mongoOperations;

    public static User cantWaitAnyLonger(WaitingUser waitingUser) {
        return new User(waitingUser.getLogin(), waitingUser.getPassword(), waitingUser.getEmail());
    }

    public void addToQueue(WaitingUser user, WaitingUserRepository repository) {
        mongoOperations.addUserToQueue(user, repository);
    }
}

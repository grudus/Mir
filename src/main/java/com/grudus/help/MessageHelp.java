package com.grudus.help;


import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MessageHelp {

    private MongoOperationsImpl mongoOperations;

    public static ArrayList<String> findTags(final String message) {
        ArrayList<String> tags = new ArrayList<>();

        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '#') {
                for (int j = ++i; j < message.length(); j++) {
                    if (message.substring(j, j+1).matches("\\s")) {
                        tags.add(message.substring(i, j));
                        break;
                    }
                    if (j == message.length()-1) tags.add(message.substring(i, j+1));
                }
            }
        }

        return tags;
    }

    public void vote(String messageId, MessageRepository messageRepository, String login, UserRepository userRepository, final int plusOrMinus) {
        System.out.println("MessagesHelp vote()");
        mongoOperations.vote(messageId, messageRepository, login, userRepository, plusOrMinus);
    }

    @Autowired
    @Qualifier("mongoOperationsImpl")
    public void setMongoOperations(MongoOperationsImpl mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

}

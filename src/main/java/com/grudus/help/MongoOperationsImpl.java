package com.grudus.help;

import com.grudus.dao.MessageRepository;
import com.grudus.dao.UserRepository;
import com.grudus.dao.WaitingUserRepository;
import com.grudus.entities.Message;
import com.grudus.entities.User;
import com.grudus.entities.WaitingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoOperationsImpl {

    private MongoOperations mongoOperations;

    public void addUserToQueue(WaitingUser user, WaitingUserRepository repository) {
        if (repository.count() > Properties.DATABASE_MAX_LENGTH-1) {
            Query query = new Query();
            query.limit(1);
            query.with(new Sort(Sort.Direction.ASC, "date"));
            repository.delete(mongoOperations.find(query, WaitingUser.class));
        }
        repository.save(user);
    }

    public void vote(String messageId, MessageRepository messageRepository, String login, UserRepository userRepository, final int plusOrMinus) {
        voteUserPart(messageId, login, userRepository, plusOrMinus);
        voteMessagePart(messageId, messageRepository, plusOrMinus);

    }

    private void voteUserPart(String messageId, String login, UserRepository userRepository, final int plusOrMinus) {
        if (!userRepository.findByLogin(login).isPresent()) {
            System.err.println("Can't find the user " + login);
            return;
        }
        User user = userRepository.findByLogin(login).get();
        user.vote(messageId, plusOrMinus, true);
        Query query = new Query();
        query.addCriteria(Criteria.where("login").is(login));
        final List<String> voted;
        //0 is for minus
        if (plusOrMinus == 0) {
            voted = user.getMinusMessageIds();
            mongoOperations.updateFirst(query, Update.update("minusMessageIds", voted), User.class);
        }
        else {
            voted = user.getPlusMessageIds();
            mongoOperations.updateFirst(query, Update.update("plusMessageIds", voted), User.class);
        }
    }

    private void voteMessagePart(String messageId, MessageRepository messageRepository, final int plusOrMinus) {
        if (messageRepository.findOne(messageId) == null) {
            System.err.println("Can't find the message " + messageId);
            return;
        }
        Message message = messageRepository.findOne(messageId);
        message.vote(plusOrMinus);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(messageId));
        final int voted;
        //0 is for minus
        if (plusOrMinus == 0) {
            voted = message.getMinus();
            mongoOperations.updateFirst(query, Update.update("minus", voted), Message.class);
        }
        else {
            voted = message.getPlus();
            mongoOperations.updateFirst(query, Update.update("plus", voted), Message.class);
        }
    }

    @Autowired
    @Qualifier("mongoTemplate")
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}

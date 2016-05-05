package com.grudus.dao;

import com.grudus.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Optional;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    Collection<Message> findByTags(String tag);
    Optional<Message> findByMessage(String message);
    List<Message> findByAuthor(String author);
}

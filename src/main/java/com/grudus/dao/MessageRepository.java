package com.grudus.dao;

import com.grudus.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    Collection<Message> findByTags(String tag);
}

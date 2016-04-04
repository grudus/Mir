package com.grudus.dao;

import com.grudus.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Collection<User> findByLogin(String login);
}

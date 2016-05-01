package com.grudus.dao;

import com.grudus.entities.WaitingUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaitingUserRepository extends MongoRepository<WaitingUser, String> {
    Optional<WaitingUser> findByKey(String key);

}

package com.example.mongodb.repository;

import com.example.mongodb.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByUserName(String name);
    User findByEmail(String email);
}

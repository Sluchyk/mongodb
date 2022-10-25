package com.example.mongodb.repository;

import com.example.mongodb.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book,String> {
    Book findByName(String name);
}

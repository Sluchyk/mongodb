package com.example.mongodb.service;

import com.example.mongodb.entity.Book;
import com.example.mongodb.entity.dto.BookDto;
import com.example.mongodb.entity.dto.BookDtoToSave;

public interface BookService {
    String saveBook(BookDtoToSave bookDtoToSave);
    Iterable<Book> getAll();
    String updateBook(BookDto bookDto);
    String deleteBook(String name);
}
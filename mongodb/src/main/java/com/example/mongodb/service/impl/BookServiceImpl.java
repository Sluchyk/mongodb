package com.example.mongodb.service.impl;


import com.example.mongodb.entity.Book;
import com.example.mongodb.entity.dto.BookDto;
import com.example.mongodb.entity.dto.BookDtoToSave;
import com.example.mongodb.exception.NotFoundBookException;
import com.example.mongodb.exception.NotUniqueBookException;
import com.example.mongodb.repository.BookRepository;
import com.example.mongodb.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    public String saveBook(BookDtoToSave bookDtoToSave) {
        try {
            doesBookIsUnique(bookDtoToSave.getName());
            bookRepository.save(modelMapper.map(bookDtoToSave, Book.class));
            return "new book with name:" + bookDtoToSave.getName() + "was added to library";
        } catch (NotUniqueBookException e) {
            return "book with name: " + bookDtoToSave.getName() + "exists in our library";
        }
    }

    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public String updateBook(BookDto bookDto) {
        try {
            doesBookExist(bookDto.getName());
            Book book=bookRepository.findByName(bookDto.getName());
            long rate = (book.getRate() + bookDto.getRate()) / book.getCount();
            book.setRate(rate);
            book.setCount(book.getCount() + 1);
            bookRepository.save(book);
            return "information about book :" + bookDto.getName() + "was updated";
        } catch (NotFoundBookException e) {
            return "book with " + bookDto.getName() + "doesnt exist";
        }
    }

    @Override
    public String deleteBook(String name) {
        try {
            doesBookExist(name);
            bookRepository.delete(bookRepository.findByName(name));
            return "book:" + name + "was deleted";
        } catch (NotFoundBookException e) {
            return "book with " + name + "doesnt exist";
        }
    }

    private void doesBookExist(String name) throws NotFoundBookException {
        Book book;
        book = bookRepository.findByName(name);
        if (book == null) throw new NotFoundBookException("");
    }

    private void doesBookIsUnique(String name) throws NotUniqueBookException {
        Book book;
        book = bookRepository.findByName(name);
        if (book != null) throw new NotUniqueBookException("");
    }

}


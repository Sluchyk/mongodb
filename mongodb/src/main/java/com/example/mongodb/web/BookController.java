package com.example.mongodb.web;

import com.example.mongodb.entity.dto.BookDto;
import com.example.mongodb.entity.dto.BookDtoToSave;
import com.example.mongodb.service.BookService;
import com.example.mongodb.entity.Book;
import com.example.mongodb.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
private  final BookServiceImpl bookService;
    @PostMapping("/save")
    public String saveBook(@RequestBody BookDtoToSave bookDtoToSave)
    {
        return  bookService.saveBook(bookDtoToSave);
    }

    @GetMapping("/list")
    public  Iterable<Book> getList()
    {
        return  bookService.getAll();
    }
     @DeleteMapping("/delete/{name}")
    public String deleteBook(@PathVariable String name)
     {
        return bookService.deleteBook(name);
     }
     @PutMapping("/rate")
     public String putRate(@RequestBody BookDto bookDto)
     {
          return  bookService.updateBook(bookDto);
     }

}

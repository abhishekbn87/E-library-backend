package com.ebooklibrary.ebooklibrary.controllers;

import com.ebooklibrary.ebooklibrary.Entities.Author;
import com.ebooklibrary.ebooklibrary.Entities.Book;
import org.springframework.web.bind.annotation.*;
import com.ebooklibrary.ebooklibrary.services.BookService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://e-library.in"})
//@RequestMapping("ebook-library")
public class BookController {
    public BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/getBook")
    public Book GetBook(@RequestParam String bookId) throws ExecutionException, InterruptedException {
        return bookService.GetBook(bookId);
    }

    @GetMapping("/getAllBooks")
    public List<Book> GetAllBooks() throws ExecutionException, InterruptedException {
        return bookService.GetAllBooks();
    }

    @GetMapping("/getAuthorMetadata")
    public Author GetAuthorMetadata(@RequestParam String author){
        return bookService.GetAuthorMetadata(author);
    }

    @GetMapping("/test")
    public String test(){
        return "Hello World";
    }

    @PostMapping("/AddBook")
        public String AddBook(@RequestBody Book book) throws ExecutionException, InterruptedException {
            return bookService.AddBook(book);
        }
}

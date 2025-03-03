package library.controller;

import java.util.List;
import library.Book;
import library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class Controller {
    private final BookService bookService;

    @Autowired
    public Controller(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<Book> getBookByName(@RequestParam(name = "name") String name) {
        return bookService.getBookByName(name);
    }
}

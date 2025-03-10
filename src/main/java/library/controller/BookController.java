package library.controller;

import java.util.List;
import library.exception.NotFoundException;
import library.model.Book;
import library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) throws NotFoundException {
        Book book = bookService.getBookById(id);
        if (book == null) {
            throw new NotFoundException("Book with id " + id + "not found.");
        }
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBookByName(@RequestParam(name = "name") String name)
            throws NotFoundException {
        List<Book> bookList = bookService.getBookByName(name);
        if (bookList.isEmpty()) {
            throw new NotFoundException("Book with name " + name + "not found.");
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }
}

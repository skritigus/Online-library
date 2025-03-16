package library.controller;

import java.util.List;

import library.dto.get.AuthorGetDto;
import library.dto.get.BookGetDto;
import library.exception.NotFoundException;
import library.model.Book;
import library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookGetDto> getBookById(@PathVariable Long id) {
        BookGetDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.getBookById(id)
                .orElseThrow(() -> new NotFoundException("Book with id" + id + " not found"));
        updatedBook.setName(book.getName());
        updatedBook.setPageAmount(book.getPageAmount());
        updatedBook.setYear(book.getYear());
        return ResponseEntity.ok(bookService.createBook(updatedBook));
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

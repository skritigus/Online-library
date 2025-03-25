package library.controller;

import jakarta.validation.Valid;
import java.util.List;
import library.dto.create.BookCreateDto;
import library.dto.get.BookGetDto;
import library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/search/author")
    public ResponseEntity<List<BookGetDto>> getBookByAuthor(@RequestParam String name) {
        return ResponseEntity.ok(bookService.getBookByAuthor(name));
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<BookGetDto>> getBookByCategory(@RequestParam String name) {
        return ResponseEntity.ok(bookService.getBookByCategory(name));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookGetDto>> getBookByName(@RequestParam String name) {
        return ResponseEntity.ok(bookService.getBookByName(name));
    }

    @GetMapping
    public ResponseEntity<List<BookGetDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<BookGetDto> createBook(@Valid @RequestBody BookCreateDto book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookGetDto> updateBook(@PathVariable Long id,
                                                 @Valid @RequestBody BookCreateDto book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

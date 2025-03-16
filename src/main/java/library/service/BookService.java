package library.service;

import java.util.List;
import java.util.Optional;

import library.dto.get.AuthorGetDto;
import library.dto.get.BookGetDto;
import library.exception.NotFoundException;
import library.model.Author;
import library.repository.BookRepository;
import library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookGetDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
        return BookGetDto.toDto(book);
    }

    public List<Book> getBookByName(String name)  {
        return bookRepository.findBooksByName(name);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

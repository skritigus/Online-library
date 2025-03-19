package library.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import library.dto.create.AuthorCreateDto;
import library.dto.get.AuthorGetDto;
import library.exception.NotFoundException;
import library.mapper.AuthorMapper;
import library.model.Author;
import library.model.Book;
import library.repository.AuthorRepository;
import library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "Author is not found with id: ";
    private static final String BOOK_NOT_FOUND_MESSAGE = "Book is not found with id: ";

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public AuthorGetDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(AUTHOR_NOT_FOUND_MESSAGE + id));
        return AuthorMapper.toDto(author);
    }

    public List<Author> getAuthorByName(String name)  {
        return authorRepository.findAuthorsByName(name);
    }

    public AuthorGetDto createAuthor(AuthorCreateDto authorDto) {
        Author authorEntity = AuthorMapper.fromDto(authorDto);

        Set<Book> books = new HashSet<>();
        if (authorDto.getBookIds() != null && !authorDto.getBookIds().isEmpty()) {
            for (Long bookId : authorDto.getBookIds()) {
                Book book = bookRepository.findById(bookId)
                        .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_MESSAGE + bookId));
                book.getAuthors().add(authorEntity);
                books.add(book);
            }
        }
        authorEntity.setBooks(books);

        return AuthorMapper.toDto(authorRepository.save(authorEntity));
    }

    public AuthorGetDto updateAuthor(Long id, AuthorCreateDto authorDto) {
        Author authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(AUTHOR_NOT_FOUND_MESSAGE + id));
        authorEntity.setName(authorDto.getName());
        authorEntity.setInfo(authorDto.getInfo());

        Set<Book> books = new HashSet<>();
        if (authorDto.getBookIds() != null && !authorDto.getBookIds().isEmpty()) {
            for (Long bookId : authorDto.getBookIds()) {
                Book book = bookRepository.findById(bookId)
                        .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_MESSAGE + bookId));
                book.getAuthors().add(authorEntity);
                books.add(book);
            }
        }
        authorEntity.setBooks(books);

        return AuthorMapper.toDto(authorRepository.save(authorEntity));
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new NotFoundException(AUTHOR_NOT_FOUND_MESSAGE + id);
        }
        authorRepository.deleteById(id);
    }
}

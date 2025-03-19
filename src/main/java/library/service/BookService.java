package library.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import library.dto.create.BookCreateDto;
import library.dto.get.BookGetDto;
import library.exception.NotFoundException;
import library.mapper.BookMapper;
import library.model.Author;
import library.model.Book;
import library.model.Category;
import library.repository.AuthorRepository;
import library.repository.BookRepository;
import library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "Author is not found with id: ";
    private static final String BOOK_NOT_FOUND_MESSAGE = "Book is not found with id: ";
    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category is not found with id: ";

    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    public BookGetDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_MESSAGE + id));
        return BookMapper.toDto(book);
    }

    public List<Book> getBookByName(String name)  {
        return bookRepository.findBooksByName(name);
    }

    public BookGetDto createBook(BookCreateDto bookDto) {
        Book bookEntity = BookMapper.fromDto(bookDto);

        Set<Author> authors = new HashSet<>();
        if (bookDto.getAuthorIds() != null && !bookDto.getAuthorIds().isEmpty()) {
            authors = bookDto.getAuthorIds().stream()
                    .map(authorId -> authorRepository.findById(authorId)
                    .orElseThrow(() -> new NotFoundException(AUTHOR_NOT_FOUND_MESSAGE + authorId)))
                    .collect(Collectors.toSet());
        }

        Set<Category> categories = new HashSet<>();
        if (bookDto.getCategoryIds() != null && !bookDto.getCategoryIds().isEmpty()) {
            categories = bookDto.getCategoryIds().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                    .orElseThrow(()
                            -> new NotFoundException(CATEGORY_NOT_FOUND_MESSAGE + categoryId)))
                    .collect(Collectors.toSet());
        }
        bookEntity.setCategories(categories);
        bookEntity.setAuthors(authors);

        return BookMapper.toDto(bookRepository.save(bookEntity));
    }

    public BookGetDto updateBook(Long id, BookCreateDto bookDto) {
        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_MESSAGE + id));
        bookEntity.setName(bookDto.getName());
        bookEntity.setYear(bookDto.getYear());
        bookEntity.setPageAmount(bookDto.getPageAmount());

        Set<Author> authors = new HashSet<>();
        Set<Category> categories = new HashSet<>();
        if (bookDto.getAuthorIds() != null && !bookDto.getAuthorIds().isEmpty()) {
            bookDto.getAuthorIds().forEach(authorId -> {
                Author author = authorRepository.findById(authorId)
                        .orElseThrow(()
                                -> new NotFoundException(AUTHOR_NOT_FOUND_MESSAGE + authorId));
                authors.add(author);
            });
        }
        bookEntity.setAuthors(authors);

        if (bookDto.getCategoryIds() != null && !bookDto.getCategoryIds().isEmpty()) {
            bookDto.getCategoryIds().forEach(categoryId -> {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(()
                                -> new NotFoundException(CATEGORY_NOT_FOUND_MESSAGE + categoryId));
                categories.add(category);
            });
        }
        bookEntity.setCategories(categories);

        return BookMapper.toDto(bookRepository.save(bookEntity));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException(BOOK_NOT_FOUND_MESSAGE + id);
        }
        bookRepository.deleteById(id);
    }
}

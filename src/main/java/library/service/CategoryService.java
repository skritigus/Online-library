package library.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import library.dto.create.CategoryCreateDto;
import library.dto.get.CategoryGetDto;
import library.exception.NotFoundException;
import library.mapper.CategoryMapper;
import library.model.Book;
import library.model.Category;
import library.repository.BookRepository;
import library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    public CategoryGetDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));
        return CategoryMapper.toDto(category);
    }

    public CategoryGetDto createCategory(CategoryCreateDto categoryDto) {
        Category categoryEntity = CategoryMapper.fromDto(categoryDto);

        Set<Book> books = new HashSet<>();
        if (categoryDto.getBookIds() != null && !categoryDto.getBookIds().isEmpty()) {
            books = categoryDto.getBookIds().stream().map(bookId -> bookRepository.findById(bookId)
                    .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"))).collect(Collectors.toSet());
        }
        categoryEntity.setBooks(books);

        return CategoryMapper.toDto(categoryRepository.save(categoryEntity));
    }

    public CategoryGetDto updateCategory(Long id, CategoryCreateDto categoryDto) {
        Category categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));

        categoryEntity.setName(categoryDto.getName());

        Set<Book> books = new HashSet<>();
        if (categoryDto.getBookIds() != null && !categoryDto.getBookIds().isEmpty()) {
            for (Long bookId : categoryDto.getBookIds()) {
                Book book = bookRepository.findById(bookId)
                        .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"));
                book.getCategories().add(categoryEntity);
                books.add(book);
            }
        }
        categoryEntity.setBooks(books);

        return CategoryMapper.toDto(categoryRepository.save(categoryEntity));
    }

    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));
        categoryRepository.deleteById(id);
    }
}

package library.service;

import java.util.ArrayList;
import java.util.List;
import library.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final List<Book> bookRepository = new ArrayList<>(
            List.of(
                    Book.builder()
                            .id(0)
                            .name("Название")
                            .author("Автор")
                            .pageAmount(200)
                            .build(),

                    Book.builder()
                            .id(1)
                            .name("Homo Sapiens. Краткая история жизни человечетва")
                            .author("Юваль Ной Харари")
                            .pageAmount(30)
                            .build(),

                    Book.builder()
                            .id(2)
                            .name("Война и мир")
                            .author("Толстой Л.Н.")
                            .pageAmount(10000)
                            .build(),

                    Book.builder()
                            .id(3)
                            .name("Преступление и наказание")
                            .author("Достоевский Ф.М.")
                            .pageAmount(1300)
                            .build()
            )
    );

    public Book getBookById(int id) {
        return bookRepository.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Book> getBookByName(String name)  {
        return bookRepository.stream()
                .filter(book -> book.getName().equals(name))
                .toList();
    }
}

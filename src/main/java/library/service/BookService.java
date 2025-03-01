package library.service;

import java.util.List;
import library.Book;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    Book getBookById(int id);

    List<Book> getBookByName(String name);
}
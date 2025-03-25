package library.repository;

import jakarta.annotation.Nonnull;
import java.util.List;
import library.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Nonnull
    @Override
    @EntityGraph("book_get_all")
    List<Book> findAll();

    @Query("SELECT b FROM Book b, Author a INNER JOIN b.authors LEFT JOIN b.categories "
            + "WHERE a.name = :name ")
    List<Book> findByAuthor(@Param("name") String author);

    @Query(value = "SELECT b.* FROM books b "
            + "LEFT JOIN book_authors ba ON b.id = ba.book_id "
            + "INNER JOIN book_categories bc ON b.id = bc.book_id "
            + "INNER JOIN categories c ON bc.category_id = c.id "
            + "WHERE c.name = :name", nativeQuery = true)
    List<Book> findByCategory(@Param("name") String category);

    @Query("SELECT b FROM Book b, Author a LEFT JOIN b.authors LEFT JOIN b.categories "
            + "WHERE b.name = :name ")
    List<Book> findByName(String name);
}

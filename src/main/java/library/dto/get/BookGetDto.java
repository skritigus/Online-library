package library.dto.get;

import library.model.*;
import library.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BookGetDto {
    private Long id;
    private String name;
    private List<AuthorGetDto> authorsDto;
    private List<CategoryGetDto> categoriesDto;
    private int pageAmount;
    private List<ReviewGetDto> reviewsDto;
    private int year;
    private Double rating;

    public static BookGetDto toDto(Book book) {
        BookGetDto dto = new BookGetDto();

        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setPageAmount(book.getPageAmount());
        dto.setYear(book.getYear());
        dto.setRating(book.getRating());

        List<Author> authors = book.getAuthors();
        List<Category> categories = book.getCategories();
        List<Review> reviews = book.getReviews();
        if (authors != null) {
            dto.setAuthorsDto(book.getAuthors().stream()
                    .map(AuthorGetDto::toDto)
                    .collect(Collectors.toList()));
        }
        if (categories != null) {
            dto.setCategoriesDto(book.getCategories().stream()
                    .map(CategoryGetDto::toDto)
                    .collect(Collectors.toList()));
        }
        if (reviews != null) {
            dto.setReviewsDto(book.getReviews().stream()
                    .map(ReviewGetDto::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}

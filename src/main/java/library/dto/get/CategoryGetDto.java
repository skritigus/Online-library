package library.dto.get;

import library.model.Author;
import library.model.Book;
import library.model.Category;
import library.model.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryGetDto {
    private Long id;
    private String name;

    public static CategoryGetDto toDto(Category category) {
        CategoryGetDto dto = new CategoryGetDto();

        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }
}

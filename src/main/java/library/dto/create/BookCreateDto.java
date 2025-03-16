package library.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import library.dto.get.AuthorGetDto;
import library.dto.get.CategoryGetDto;
import library.dto.get.ReviewGetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookCreateDto {
    @NotBlank(message = "Book's name can't be blank")
    @Size(min = 1, max = 255)
    private String name;
    private List<Long> authorIds;
    private List<Long> categoryIds;
    @NotBlank(message = "Book's page amount can't be blank")
    private int pageAmount;
    private List<Long> reviewIds;
    private int year;
}

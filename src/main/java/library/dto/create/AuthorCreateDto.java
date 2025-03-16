package library.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import library.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorCreateDto {
    @NotBlank(message = "Author's name can't be blank")
    @Size(min = 2, max = 100, message = "Author's name must be between 2 and 100 characters")
    private String name;
    @Size(max = 255, message = "Information about author must below 256 characters")
    private String info;
    private List<Long> bookIds;
}

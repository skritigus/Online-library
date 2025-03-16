package library.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import library.dto.get.BookGetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryCreateDto {
    @NotBlank(message = "Category's name can't be blank")
    @Size(min = 2, max = 100)
    private String name;
    private List<Long> bookIds;
}

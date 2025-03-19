package library.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateDto {
    @NotBlank(message = "Category's name can't be blank")
    @Size(max = 100, message = "Category name must be less than 101 characters")
    private String name;
    private List<Long> bookIds;
}

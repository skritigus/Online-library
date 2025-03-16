package library.dto.create;

import jakarta.validation.constraints.*;
import library.dto.get.BookGetDto;
import library.dto.get.UserGetDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateDto {
    @NotNull(message = "Book ID cannot be null")
    private Long bookId;
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private int rating;
    private String comment;
}

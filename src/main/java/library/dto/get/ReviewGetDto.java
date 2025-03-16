package library.dto.get;

import library.model.Book;
import library.model.Category;
import library.model.Review;
import library.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewGetDto {
    private Long id;
    private UserGetDto userDto;
    private int rating;
    private String comment;

    public static ReviewGetDto toDto(Review review) {
        ReviewGetDto dto = new ReviewGetDto();

        dto.setId(review.getId());
        dto.setUserDto(UserGetDto.toDto(review.getUser()));
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());

        return dto;
    }
}

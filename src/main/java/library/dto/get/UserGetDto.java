package library.dto.get;

import library.model.Review;
import library.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserGetDto {
    private Long id;
    private String name;
    private String email;

    public static UserGetDto toDto(User user) {
        UserGetDto dto = new UserGetDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }
}

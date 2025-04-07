package library.dto.get;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetDto {
    private Long id;
    private String name;
    private String email;
    private List<ReviewGetDto> reviews;
}

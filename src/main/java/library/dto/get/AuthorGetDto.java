package library.dto.get;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorGetDto {
    private Long id;
    private String name;
    private String info;
}

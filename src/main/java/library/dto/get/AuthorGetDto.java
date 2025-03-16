package library.dto.get;

import library.model.Author;
import library.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AuthorGetDto {
    private Long id;
    private String name;
    private String info;


    public static AuthorGetDto toDto(Author author) {
        AuthorGetDto dto = new AuthorGetDto();

        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setInfo(author.getInfo());

        return dto;
    }
}

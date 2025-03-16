package library.dto.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import library.dto.get.ReviewGetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCreateDto {
    @NotBlank(message = "User's name can't be blank")
    @Size(min = 2, max = 100)
    private String name;
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email")
    private String email;
    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must be more than 7 characters")
    private String password;
    private List<Long> reviewIds;
}

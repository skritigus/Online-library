package library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationRequest {
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email")
    private String email;
    @Size(min = 8, message = "Password must be more than 7 characters")
    private String password;
}

package library.dto;

import jakarta.validation.constraints.Email;
<<<<<<< HEAD
=======
import jakarta.validation.constraints.Min;
>>>>>>> 848242c30eda796be130d922754bcd8c76fe0498
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
    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must be more than 7 characters")
    private String password;
}

package pl.cezary.webbanking.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
 
@Setter
@Getter
public class SignupRequest {
    @NotBlank
    @Size(min = 12, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 50, min = 2)
    private String firstName;
    @NotBlank
    @Size(max = 50, min = 2)
    private String lastName;
    
    private Set<String> role;

    @NotBlank
    @Size(min = 16, max = 30)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String password;

}

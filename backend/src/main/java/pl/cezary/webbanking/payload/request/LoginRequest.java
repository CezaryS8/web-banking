package pl.cezary.webbanking.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
	@NotBlank
	@Size(max = 20, min = 8)
	private String username;

	@NotBlank
	@Size(min = 16, max = 30)
	private String password;
}

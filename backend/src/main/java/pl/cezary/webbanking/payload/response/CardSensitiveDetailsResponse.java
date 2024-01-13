package pl.cezary.webbanking.payload.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
public class CardSensitiveDetailsResponse {
    @NotBlank
    @Pattern(regexp = "^[0-9]{16}$")
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "^[0-9]{3}$")
    private String cvc;
}

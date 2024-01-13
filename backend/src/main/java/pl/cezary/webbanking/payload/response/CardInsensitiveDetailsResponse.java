package pl.cezary.webbanking.payload.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardInsensitiveDetailsResponse {

    private Long id;

    @NotBlank
    private String cardType;

    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
    private java.util.Date expirationDate;
}

package pl.cezary.webbanking.payload.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder(toBuilder = true)
public class CreateTransferRequest {
    @NotBlank
    @Pattern(regexp = "^[0-9]{26}$")
    private String fromAccountNumber;
    @NotBlank
    @Pattern(regexp = "^[0-9]{26}$")
    private String toAccountNumber;
    @NotNull
    @DecimalMin(value = "0.1")
    private BigDecimal amount;
    @NotNull
    @Size(max = 100)
    private String title;

    public CreateTransferRequest() {
    }

    public CreateTransferRequest(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String title) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.title = title;
    }

}

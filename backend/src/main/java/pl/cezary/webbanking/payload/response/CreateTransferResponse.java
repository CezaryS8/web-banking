package pl.cezary.webbanking.payload.response;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class CreateTransferResponse {

    private AccountDetails fromAccount;
    private AccountDetails toAccount;

    @NotNull
    @DecimalMin(value = "0.1")
    private BigDecimal amount;

    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transferDate;

    @NotNull
    @Size(max = 100)
    private String title;

    @Data
    @Builder(toBuilder = true)
    public static class AccountDetails {
        @NotBlank
        @Pattern(regexp = "^[0-9]{26}$")
        @Column(unique = true, nullable = false)
        private String accountNumber;

        @NotBlank
        @Size(max = 50)
        private String firstName;

        @NotBlank
        @Size(max = 50)
        private String lastName;
    }
}

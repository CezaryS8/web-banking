package pl.cezary.webbanking.payload.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class AccountDetailsResponse {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
}

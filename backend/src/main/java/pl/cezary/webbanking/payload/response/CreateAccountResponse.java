package pl.cezary.webbanking.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CreateAccountResponse {

    private String accountNumber;


}

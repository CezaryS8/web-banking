package pl.cezary.webbanking.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeRequest {
    @NotBlank
    @Size(max = 6, min = 6)
    private String code;
}
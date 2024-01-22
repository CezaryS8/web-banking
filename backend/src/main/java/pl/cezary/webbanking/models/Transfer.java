package pl.cezary.webbanking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "1000000.0")
    private BigDecimal amount;

    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transferDate;

    @NotNull
    @Size(max = 100, min = 2)
    private String title;

}

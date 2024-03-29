package pl.cezary.webbanking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Size(max = 120)
    private String cardNumber;

    @NotBlank
    @Size(min = 2, max = 20)
    private String cardType;

    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @NotBlank
    @Size(max = 120)
    private String cvc;

    public Card() {
    }

    public Card(Account account, String cardNumber, String cardType, Date expirationDate, String cvc) {
        this.account = account;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
    }

}

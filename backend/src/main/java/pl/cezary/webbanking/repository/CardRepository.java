package pl.cezary.webbanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cezary.webbanking.models.Account;
import pl.cezary.webbanking.models.Card;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByCardNumber(String cardNumber);

    List<Card> findAllByAccountId(Long accountId);
}

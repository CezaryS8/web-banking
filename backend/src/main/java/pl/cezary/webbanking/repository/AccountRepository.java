package pl.cezary.webbanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cezary.webbanking.models.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String toAccountNumber);

    boolean existsByAccountNumber(String accountNumber);

    List<Account> findByUserId(Long userId);
}

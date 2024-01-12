package pl.cezary.webbanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cezary.webbanking.models.Transfer;
import pl.cezary.webbanking.payload.response.CreateTransferResponse;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);

    List<Transfer> findByFromAccountId(Long fromAccountId);

    List<Transfer> findByToAccountId(Long toAccountId);
}

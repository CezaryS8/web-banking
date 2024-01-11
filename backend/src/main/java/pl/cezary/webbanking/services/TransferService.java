package pl.cezary.webbanking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cezary.webbanking.models.Account;
import pl.cezary.webbanking.models.Transfer;
import pl.cezary.webbanking.repository.AccountRepository;
import pl.cezary.webbanking.repository.TransferRepository;

import java.util.Date;
import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transfer> getAllTransfersForAccount(Long accountId) {
        return transferRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
    }

    @Transactional
    public Transfer createTransfer(Transfer transfer) {
        Account fromAccount = accountRepository.findById(transfer.getFromAccount().getId()).orElseThrow(() -> new RuntimeException("Account not found"));
        Account toAccount = accountRepository.findById(transfer.getToAccount().getId()).orElseThrow(() -> new RuntimeException("Account not found"));

        if(fromAccount.getBalance().compareTo(transfer.getFromAccount().getBalance()) < 0) {
            throw new RuntimeException("Not enough money");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.getFromAccount().getBalance()));
        toAccount.setBalance(toAccount.getBalance().add(transfer.getToAccount().getBalance()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        transfer.setTransferDate(new Date());

        return transferRepository.save(transfer);
    }

}

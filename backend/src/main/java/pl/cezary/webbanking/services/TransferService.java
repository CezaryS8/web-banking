package pl.cezary.webbanking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cezary.webbanking.models.Account;
import pl.cezary.webbanking.models.Transfer;
import pl.cezary.webbanking.payload.request.CreateTransferRequest;
import pl.cezary.webbanking.payload.response.CreateTransferResponse;
import pl.cezary.webbanking.repository.AccountRepository;
import pl.cezary.webbanking.repository.TransferRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public CreateTransferResponse createTransfer(CreateTransferRequest transfer) {

        Account fromAccount = accountRepository.findByAccountNumber(transfer.getFromAccountNumber()).orElseThrow(() -> new RuntimeException("Account not found"));
        Account toAccount = accountRepository.findByAccountNumber(transfer.getToAccountNumber()).orElseThrow(() -> new RuntimeException("Account not found"));

        if(fromAccount.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new RuntimeException("Not enough money");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transfer.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transfer newTransfer = Transfer.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(transfer.getAmount())
                .title(transfer.getTitle())
                .transferDate(LocalDateTime.now())
                .build();

        transferRepository.save(newTransfer);

        return CreateTransferResponse.builder()
                .fromAccount(CreateTransferResponse.AccountDetails.builder()
                        .firstName(fromAccount.getUser().getFirstName())
                        .lastName(fromAccount.getUser().getLastName())
                        .accountNumber(fromAccount.getAccountNumber())
                        .build())
                .toAccount(CreateTransferResponse.AccountDetails.builder()
                        .firstName(toAccount.getUser().getFirstName())
                        .lastName(toAccount.getUser().getLastName())
                        .accountNumber(toAccount.getAccountNumber())
                        .build())
                .amount(transfer.getAmount())
                .transferDate(newTransfer.getTransferDate())
                .title(newTransfer.getTitle())
                .build();
    }

    public List<CreateTransferResponse> getAllTransfersForAccount( Long accountId ) {
        List<Transfer> transfers = transferRepository.findByFromAccountIdOrToAccountId(accountId, accountId);

        return transfers
                .stream()
                .map(transfer -> CreateTransferResponse.builder()
                        .fromAccount(CreateTransferResponse.AccountDetails.builder()
                                .firstName(transfer.getFromAccount().getUser().getFirstName())
                                .lastName(transfer.getFromAccount().getUser().getLastName())
                                .accountNumber(transfer.getFromAccount().getAccountNumber())
                                .build())
                        .toAccount(CreateTransferResponse.AccountDetails.builder()
                                .firstName(transfer.getToAccount().getUser().getFirstName())
                                .lastName(transfer.getToAccount().getUser().getLastName())
                                .accountNumber(transfer.getToAccount().getAccountNumber())
                                .build())
                        .amount(transfer.getAmount())
                        .transferDate(transfer.getTransferDate())
                        .title(transfer.getTitle())
                        .build())
                .toList();
    }

    public List<CreateTransferResponse> getAllTransfersFromAccount( Long accountId) {
        List<Transfer> transfers = transferRepository.findByFromAccountId(accountId);

        return transfers
                .stream()
                .map(transfer -> CreateTransferResponse.builder()
                        .fromAccount(CreateTransferResponse.AccountDetails.builder()
                                .firstName(transfer.getFromAccount().getUser().getFirstName())
                                .lastName(transfer.getFromAccount().getUser().getLastName())
                                .accountNumber(transfer.getFromAccount().getAccountNumber())
                                .build())
                        .toAccount(CreateTransferResponse.AccountDetails.builder()
                                .firstName(transfer.getToAccount().getUser().getFirstName())
                                .lastName(transfer.getToAccount().getUser().getLastName())
                                .accountNumber(transfer.getToAccount().getAccountNumber())
                                .build())
                        .amount(transfer.getAmount())
                        .transferDate(transfer.getTransferDate())
                        .title(transfer.getTitle())
                        .build())
                .toList();
    }

    public List<CreateTransferResponse> getAllTransfersToAccount( Long accountId) {
        List<Transfer> transfers = transferRepository.findByToAccountId(accountId);

        return transfers
                .stream()
                .map(transfer -> CreateTransferResponse.builder()
                        .fromAccount(CreateTransferResponse.AccountDetails.builder()
                                .firstName(transfer.getFromAccount().getUser().getFirstName())
                                .lastName(transfer.getFromAccount().getUser().getLastName())
                                .accountNumber(transfer.getFromAccount().getAccountNumber())
                                .build())
                        .toAccount(CreateTransferResponse.AccountDetails.builder()
                                .firstName(transfer.getToAccount().getUser().getFirstName())
                                .lastName(transfer.getToAccount().getUser().getLastName())
                                .accountNumber(transfer.getToAccount().getAccountNumber())
                                .build())
                        .amount(transfer.getAmount())
                        .transferDate(transfer.getTransferDate())
                        .title(transfer.getTitle())
                        .build())
                .toList();
    }

}

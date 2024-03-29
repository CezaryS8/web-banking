package pl.cezary.webbanking.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cezary.webbanking.models.Account;
import pl.cezary.webbanking.models.User;
import pl.cezary.webbanking.payload.response.AccountDetailsResponse;
import pl.cezary.webbanking.payload.response.CreateAccountResponse;
import pl.cezary.webbanking.repository.AccountRepository;
import pl.cezary.webbanking.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")); // Replace with appropriate exception handling

        String accountNumber = generateUniqueAccountNumber();
        Account newAccount = Account.builder()
                .user(user)
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .build();

        accountRepository.save(newAccount);
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = generateRandomAccountNumber();
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private String generateRandomAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder(26);
        for (int i = 0; i < 26; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    public List<AccountDetailsResponse> getAllAccountsForUser(Long userId) {

        List<Account> accounts = accountRepository.findAllByUserId(userId);

        return accounts.stream()
                .map(account -> AccountDetailsResponse.builder()
                        .id(account.getId())
                        .accountNumber(account.getAccountNumber())
                        .balance(account.getBalance())
                        .build())
                .toList();
    }


    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public void deleteAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.delete(account);
    }

    public boolean checkIfAccountBelongsToUser(Long userId, Long accountId) {
        return accountRepository.findById(accountId).get().getUser().getId().equals(userId);
    }

    public boolean checkIfAccountBelongsToUser(Long userId, String fromAccountNumber) {
        return accountRepository.findByAccountNumber(fromAccountNumber).get().getUser().getId().equals(userId);
    }

}

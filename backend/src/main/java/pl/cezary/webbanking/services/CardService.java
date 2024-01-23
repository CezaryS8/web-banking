package pl.cezary.webbanking.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cezary.webbanking.models.Account;
import pl.cezary.webbanking.models.Card;
import pl.cezary.webbanking.payload.response.CardInsensitiveDetailsResponse;
import pl.cezary.webbanking.payload.response.CardSensitiveDetailsResponse;
import pl.cezary.webbanking.repository.AccountRepository;
import pl.cezary.webbanking.repository.CardRepository;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final encryptionService encryptionService;

    public CardSensitiveDetailsResponse getCardSensitiveDetails(Long cardId) {
        log.info("cardSensitiveDetailsResponse1: " );
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        log.info("cardSensitiveDetailsResponse2: " );
        String cardNumber = encryptionService.decrypt(card.getCardNumber());
        String cvc = encryptionService.decrypt(card.getCvc());
        log.info("cardSensitiveDetailsResponse3: " );
        return CardSensitiveDetailsResponse.builder()
                .cardNumber(cardNumber)
                .cvc(cvc)
                .build();
    }

    public CardInsensitiveDetailsResponse getCardInsensitiveDetails(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        return CardInsensitiveDetailsResponse.builder()
                .id(card.getId())
                .cardType(card.getCardType())
                .expirationDate(card.getExpirationDate())
                .build();
    }

    @Transactional
    public Card createCard(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        String cardNumber = encryptionService.encrypt(generateUniqueCardNumber());
        String cardType = generateCardType();
        Date expirationDate = generateExpirationDate();
        String cvc = encryptionService.encrypt(generateCVC());

        Card newCard = new Card(account, cardNumber, cardType, expirationDate, cvc);

        return cardRepository.save(newCard);
    }
    private String generateUniqueCardNumber() {
        String cardNumber;
        Random random = new Random();
        do {
            StringBuilder sb = new StringBuilder(16);
            for (int i = 0; i < 16; i++) {
                sb.append(random.nextInt(10));
            }
            cardNumber = sb.toString();
        } while (cardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    private String generateCardType() {
        return "VISA";
    }

    private Date generateExpirationDate() {
        // Generate a date 3 years from now
        return new Date(System.currentTimeMillis() + 3L * 365 * 24 * 60 * 60 * 1000);
    }

    private String generateCVC() {
        Random random = new Random();
        StringBuilder cvc = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            cvc.append(random.nextInt(10));
        }
        return cvc.toString();
    }

    public boolean checkIfCardBelongsToAccount(Long userId, Long accountId) {
        return accountRepository.existsByIdAndUserId(accountId, userId);
    }

    public List<CardInsensitiveDetailsResponse> getCards(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return cardRepository.findAllByAccountId(accountId).stream()
                .map(card -> CardInsensitiveDetailsResponse.builder()
                        .id(card.getId())
                        .cardType(card.getCardType())
                        .expirationDate(card.getExpirationDate())
                        .build())
                .toList();
    }
}

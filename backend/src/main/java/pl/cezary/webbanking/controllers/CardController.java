package pl.cezary.webbanking.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.cezary.webbanking.payload.request.CodeRequest;
import pl.cezary.webbanking.payload.response.CardInsensitiveDetailsResponse;
import pl.cezary.webbanking.payload.response.CardSensitiveDetailsResponse;
import pl.cezary.webbanking.payload.response.MessageResponse;
import pl.cezary.webbanking.security.OneTimeCodeManager;
import pl.cezary.webbanking.security.services.UserDetailsImpl;
import pl.cezary.webbanking.services.AccountService;
import pl.cezary.webbanking.services.CardService;

import java.util.List;

@CrossOrigin(origins = "https://localhost", allowCredentials = "true")
@RestController
@RequestMapping("/api/card")
@AllArgsConstructor
@Slf4j
public class CardController {

    private final CardService cardService;
    private final AccountService accountService;

    @PostMapping("/byadmin/create/account/{accountId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCardByAdmin(@PathVariable Long accountId) {
        try {
            cardService.createCard(accountId);
            return ResponseEntity.ok("Card created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Card not created");
        }
    }

    @GetMapping("/account/{accountId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CardInsensitiveDetailsResponse>> getCards(@PathVariable Long accountId) {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                if(!accountService.checkIfAccountBelongsToUser(userId, accountId)) {
                    return ResponseEntity.badRequest().body(null);
                }
                List<CardInsensitiveDetailsResponse> cards = cardService.getCards(accountId);
                return ResponseEntity.ok(cards);
            }
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/create/account/{accountId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createCard( @PathVariable Long accountId ) {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                if(!accountService.checkIfAccountBelongsToUser(userId, accountId)) {
                    return ResponseEntity.badRequest().body(null);
                }
                cardService.createCard(accountId);
                return ResponseEntity.ok("Card created");
            }
            return ResponseEntity.badRequest().body("Card not created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Card not created");
        }
    }

    @PostMapping("/request-sensitive/account/{accountId}/card/{cardId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> requestCodeForSensitiveDetails(@PathVariable Long accountId, @PathVariable Long cardId) {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                if(!accountService.checkIfAccountBelongsToUser(userId, accountId)) {
                    return ResponseEntity.badRequest().body(null);
                }
                if(!cardService.checkIfCardBelongsToAccount(userId, accountId)) {
                    return ResponseEntity.badRequest().body(null);
                }
                String code = OneTimeCodeManager.generateOneTimeCode();
                OneTimeCodeManager.saveCodeForUser(userId, accountId, cardId, code);
                System.out.println("One-Time Code (For testing purposes): " + code); // For real app, send this code to user's email or phone
                return ResponseEntity.ok(new MessageResponse("Code sent to your email or phone"));
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Code not sent"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Code not sent"));
        }
    }
    @PostMapping("/sensitive/account/{accountId}/card/{cardId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CardSensitiveDetailsResponse> getCardSensitiveDetails(@PathVariable Long accountId, @PathVariable Long cardId, @Valid @RequestBody CodeRequest code) {
        log.info("Code: " + code.getCode());
        try {
            log.info(" Try Code: " + code.getCode());
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                log.info("IF Code: " + code.getCode());
                System.out.println(code);
                Long userId = ((UserDetailsImpl) principle).getId();
                if(!accountService.checkIfAccountBelongsToUser(userId, accountId)) {
                    log.info("1: " + code.getCode());
                    return ResponseEntity.badRequest().body(null);
                }
                if(!cardService.checkIfCardBelongsToAccount(userId, accountId)) {
                    log.info("2: " + code.getCode());
                    return ResponseEntity.badRequest().body(null);
                }
                if (!OneTimeCodeManager.isCodeValid( userId, accountId, cardId, code.getCode())) {
                    log.info("3: " + code.getCode());
                    return ResponseEntity.badRequest().body(null);
                }
                log.info("4: " + code.getCode());
                CardSensitiveDetailsResponse cardSensitiveDetailsResponse = cardService.getCardSensitiveDetails(cardId);
                log.info("cardSensitiveDetailsResponse: " + cardSensitiveDetailsResponse);
                return ResponseEntity.ok(cardSensitiveDetailsResponse);
            }
            log.info("cardSensitiveDetailsResponse: error" );
            return ResponseEntity.badRequest().body(null);

        } catch (Exception e) {
            log.info("cardSensitiveDetailsResponse: zlapalem gowno ", e );
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/insensitive/account/{accountId}/card/{cardId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CardInsensitiveDetailsResponse> getCardInsensitiveDetails(@PathVariable Long accountId, @PathVariable Long cardId) {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                if(!accountService.checkIfAccountBelongsToUser(userId, accountId)) {
                    return ResponseEntity.badRequest().body(null);
                }
                if(!cardService.checkIfCardBelongsToAccount(userId, accountId)) {
                    return ResponseEntity.badRequest().body(null);
                }
                CardInsensitiveDetailsResponse cardInsensitiveDetailsResponse = cardService.getCardInsensitiveDetails(cardId);
                return ResponseEntity.ok(cardInsensitiveDetailsResponse);
            }
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}

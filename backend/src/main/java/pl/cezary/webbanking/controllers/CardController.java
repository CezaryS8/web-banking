package pl.cezary.webbanking.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.cezary.webbanking.payload.response.CardInsensitiveDetailsResponse;
import pl.cezary.webbanking.payload.response.CardSensitiveDetailsResponse;
import pl.cezary.webbanking.security.services.UserDetailsImpl;
import pl.cezary.webbanking.services.AccountService;
import pl.cezary.webbanking.services.CardService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/card")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;
    private final AccountService accountService;

    @PostMapping("/create/account/{accountId}")
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

    @GetMapping("/sensitive/account/{accountId}/card/{cardId}")
    public ResponseEntity<CardSensitiveDetailsResponse> getCardSensitiveDetails(@PathVariable Long accountId, @PathVariable Long cardId) {
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

                CardSensitiveDetailsResponse cardSensitiveDetailsResponse = cardService.getCardSensitiveDetails(cardId);
                return ResponseEntity.ok(cardSensitiveDetailsResponse);
            }
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/insensitive/account/{accountId}/card/{cardId}")
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

package pl.cezary.webbanking.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.cezary.webbanking.payload.response.AccountDetailsResponse;
import pl.cezary.webbanking.security.services.UserDetailsImpl;
import pl.cezary.webbanking.services.AccountService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createAccount() {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                accountService.createAccount(userId);
                return ResponseEntity.ok("Account created");
            }
            return ResponseEntity.badRequest().body("Account not created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Account not created");
        }
    }

    @PostMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAccountForUser(@PathVariable Long userId) {
        try {
            accountService.createAccount(userId);
            return ResponseEntity.ok("Account created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Account not created");
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountDetailsResponse>> getAllAccounts() {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                return ResponseEntity.ok(accountService.getAllAccountsForUser(userId));
            }
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}

package pl.cezary.webbanking.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.cezary.webbanking.security.services.UserDetailsImpl;
import pl.cezary.webbanking.services.AccountService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    @PostMapping
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

}

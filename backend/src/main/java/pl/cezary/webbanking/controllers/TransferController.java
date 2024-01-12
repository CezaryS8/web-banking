package pl.cezary.webbanking.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.cezary.webbanking.payload.request.CreateTransferRequest;
import pl.cezary.webbanking.payload.response.CreateTransferResponse;
import pl.cezary.webbanking.security.services.UserDetailsImpl;
import pl.cezary.webbanking.services.TransferService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransferService transferService;

    @GetMapping("/test")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Transfer Content.");
    }

    @GetMapping("/account/{accountId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CreateTransferResponse>> getAllTransfersByAccountId(@PathVariable Long accountId) {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                if(!transferService.checkIfAccountBelongsToUser(userId, accountId)) {
                    return ResponseEntity.badRequest().body(null);
                }

                List<CreateTransferResponse> transfers = transferService.getAllTransfersForAccount(accountId);
                return ResponseEntity.ok(transfers);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/account/{accountId}/from")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CreateTransferResponse>> getAllTransfersByFromAccountId(@PathVariable Long accountId) {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                if(!transferService.checkIfAccountBelongsToUser(userId, accountId)) {
                    return ResponseEntity.badRequest().body(null);
                }

                List<CreateTransferResponse> transfers = transferService.getAllTransfersFromAccount(accountId);
                return ResponseEntity.ok(transfers);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/account/{accountId}/to")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CreateTransferResponse>> getAllTransfersByToAccountId(@PathVariable Long accountId) {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                if(!transferService.checkIfAccountBelongsToUser(userId, accountId)) {
                    return ResponseEntity.badRequest().body(null);
                }

                List<CreateTransferResponse> transfers = transferService.getAllTransfersToAccount(accountId);
                return ResponseEntity.ok(transfers);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CreateTransferResponse> createTransfer(@Valid @RequestBody CreateTransferRequest transfer) {
        try {
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle.toString() != "anonymousUser") {
                Long userId = ((UserDetailsImpl) principle).getId();
                if(!transferService.checkIfAccountBelongsToUser(userId, transfer.getFromAccountNumber())) {
                    return ResponseEntity.badRequest().body(null);
                }

                CreateTransferResponse newTransferResponse = transferService.createTransfer(transfer);
                return ResponseEntity.ok(newTransferResponse);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}

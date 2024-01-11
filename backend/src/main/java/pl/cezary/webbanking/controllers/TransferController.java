package pl.cezary.webbanking.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.cezary.webbanking.models.Transfer;
import pl.cezary.webbanking.repository.AccountRepository;
import pl.cezary.webbanking.services.TransferService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping("/test")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Transfer Content.");
    }

    @GetMapping("/account/{accountId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Transfer>> getAllTransfersByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(transferService.getAllTransfersForAccount(accountId));
    }
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Transfer> createTransfer(@Valid @RequestBody Transfer transfer) {
        try {
            Transfer newTransfer = transferService.createTransfer(transfer);
            return ResponseEntity.ok(newTransfer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

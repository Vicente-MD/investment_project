package br.com.goldinvesting.infrastructure.adapters.in.api;

import br.com.goldinvesting.application.dto.CheckingAccountDTO;
import br.com.goldinvesting.application.dto.converter.CheckingAccountConverter;
import br.com.goldinvesting.application.ports.in.CheckingAccountUseCase;
import br.com.goldinvesting.domain.model.CheckingAccount;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/checking-accounts")
@RequiredArgsConstructor
public class CheckingAccountController {
    private final CheckingAccountUseCase checkingAccountUseCase;

    @PostMapping("/{userId}")
    public ResponseEntity<CheckingAccountDTO> createCheckingAccount(@RequestBody CheckingAccountDTO checkingAccountDTO, @PathVariable long userId) {
        var createdCheckingAccount = checkingAccountUseCase.createCheckingAccount(checkingAccountDTO, userId);
        return ResponseEntity.ok(createdCheckingAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckingAccountDTO> getCheckingAccountById(@PathVariable long id) {
        CheckingAccount checkingAccount = checkingAccountUseCase.getCheckingAccountById(id);
        return ResponseEntity.ok(CheckingAccountConverter.toDTO(checkingAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckingAccount(@PathVariable long id) {
        checkingAccountUseCase.deleteCheckingAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<CheckingAccountDTO>> getCheckingAccounts(@PathVariable Long userId) {
        var checkingAccounts = checkingAccountUseCase.getCheckingAccounts(userId);
        return ResponseEntity.ok(checkingAccounts);
    }

    @PostMapping("/conclude/{id}")
    public ResponseEntity<Void> concludeCheckingAccount(@PathVariable long id) {
        checkingAccountUseCase.concludeCheckingAccount(id);
        return ResponseEntity.noContent().build();
    }
}

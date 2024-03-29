package br.com.goldinvesting.checkingaccount.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goldinvesting.checkingaccount.model.CheckingAccount;
import br.com.goldinvesting.checkingaccount.model.CheckingAccountDTO;
import br.com.goldinvesting.checkingaccount.service.CheckingAccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/checkingAccount")
@AllArgsConstructor
public class CheckingAccountController {

    private final CheckingAccountService checkingAccountService;
    
    @PostMapping
    public ResponseEntity<Long> createCheckingAccount(@RequestBody CheckingAccountDTO checkingAccount) {
        return ResponseEntity.created(URI.create("/api/checkingAccount/" + checkingAccountService.createCheckingAccount(checkingAccount).getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckingAccount> getCheckingAccountById(@PathVariable("id") long id) {
        return ResponseEntity.ok(checkingAccountService.getCheckingAccountById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckingAccount(@PathVariable("id") long id) {
        checkingAccountService.deleteCheckingAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CheckingAccount>> getCheckingAccounts() {
        return ResponseEntity.ok(checkingAccountService.getCheckingAccounts());
    }

    @PostMapping("/conclude/{id}")
    public ResponseEntity<Void> concludeCheckingAccount(@PathVariable("id") long id) {
        checkingAccountService.concludeCheckingAccount(id);
        return ResponseEntity.ok().build();
    }

}
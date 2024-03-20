package br.com.goldinvesting.checkingaccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goldinvesting.checkingaccount.model.CheckingAccount;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
    
}

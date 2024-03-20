package br.com.goldinvesting.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goldinvesting.wallet.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    
}

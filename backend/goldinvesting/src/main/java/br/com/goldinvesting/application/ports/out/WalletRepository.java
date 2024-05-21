package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}

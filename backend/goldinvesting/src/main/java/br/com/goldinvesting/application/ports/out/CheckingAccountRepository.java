package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
}

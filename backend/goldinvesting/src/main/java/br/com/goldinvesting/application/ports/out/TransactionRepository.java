package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    void setStatus(Long transactionId, Long statusId);
}

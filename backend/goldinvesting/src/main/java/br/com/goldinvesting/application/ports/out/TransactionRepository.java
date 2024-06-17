package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.Transaction;
import br.com.goldinvesting.domain.model.Wallet;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Modifying
    @Query(value="UPDATE transaction set transaction.status_id = ?2 WHERE transaction.id = ?1", nativeQuery=true)
    void setStatus(Long id,  Long status_id);
}

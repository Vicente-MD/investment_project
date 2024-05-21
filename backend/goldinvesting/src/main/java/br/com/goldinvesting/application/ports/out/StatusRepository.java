package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByStatus(String status);
}

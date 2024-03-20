package br.com.goldinvesting.status.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goldinvesting.status.model.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
    public Optional<Status> findByStatus(String status);
}

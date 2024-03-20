package br.com.goldinvesting.treasurydirect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goldinvesting.treasurydirect.model.TreasuryDirect;

public interface TreasuryDirectRepository extends JpaRepository<TreasuryDirect, Long> {
}

package br.com.goldinvesting.application.ports.out;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goldinvesting.domain.model.FixedIncome;

public interface FixedIncomeRepository extends JpaRepository<FixedIncome, Long> {
}

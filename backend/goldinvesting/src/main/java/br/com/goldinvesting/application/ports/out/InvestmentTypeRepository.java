package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.InvestmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvestmentTypeRepository extends JpaRepository<InvestmentType, Long> {
    Optional<InvestmentType> findByInvestmentType(String investmentType);
}

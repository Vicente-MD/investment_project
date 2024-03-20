package br.com.goldinvesting.investmenttype.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goldinvesting.investmenttype.model.InvestmentType;

public interface InvestmentTypeRepository extends JpaRepository<InvestmentType, Long> {
    public Optional<InvestmentType> findByInvestmentType(String investmentType);
}

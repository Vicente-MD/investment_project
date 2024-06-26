package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.Stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}

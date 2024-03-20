package br.com.goldinvesting.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.goldinvesting.stock.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    
}

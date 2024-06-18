package br.com.goldinvesting.application.ports.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goldinvesting.domain.model.StockData;

public interface StockDataRepository extends JpaRepository<StockData, Long> {
    List<StockData> findByStockSymbolId(Long stockSymbolId);
}

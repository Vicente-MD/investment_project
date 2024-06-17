package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.StockSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockSymbolRepository extends JpaRepository<StockSymbol, Long> {
    @Query("SELECT b FROM StockSymbol b WHERE LOWER(b.ticker) LIKE LOWER(CONCAT('%', :inputText, '%'))")
    List<StockSymbol> findByTickerContainingIgnoreCase(@Param("inputText") String inputText);
}

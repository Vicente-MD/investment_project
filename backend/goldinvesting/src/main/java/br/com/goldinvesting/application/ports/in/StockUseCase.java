package br.com.goldinvesting.application.ports.in;

import br.com.goldinvesting.application.dto.StockDTO;

import java.util.List;

public interface StockUseCase {
    StockDTO createStock(StockDTO StockDTO, long userId);
    StockDTO getStockById(long id);
    void deleteStock(long id);
    List<StockDTO> getStocks();
    void sellStock(long id);
}

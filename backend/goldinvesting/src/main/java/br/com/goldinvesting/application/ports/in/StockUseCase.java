package br.com.goldinvesting.application.ports.in;

import br.com.goldinvesting.application.dto.StockDTO;

import java.util.List;

public interface StockUseCase {
    StockDTO createStock(StockDTO StockDTO);
    StockDTO getStockById(long id);
    void deleteStock(long id);
    List<StockDTO> getStocks();
}

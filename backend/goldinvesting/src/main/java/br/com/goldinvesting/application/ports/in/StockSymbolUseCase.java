package br.com.goldinvesting.application.ports.in;

import br.com.goldinvesting.application.dto.StockSymbolDTO;

import java.util.List;

public interface StockSymbolUseCase {
    List<StockSymbolDTO> getStockSymbolByText(String inputText);
}

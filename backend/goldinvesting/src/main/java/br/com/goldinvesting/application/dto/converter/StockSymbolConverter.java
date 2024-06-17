package br.com.goldinvesting.application.dto.converter;

import br.com.goldinvesting.application.dto.StockSymbolDTO;
import br.com.goldinvesting.domain.model.StockSymbol;

public class StockSymbolConverter {

    public static StockSymbolDTO toDTO(StockSymbol stockSymbol) {
        if (stockSymbol == null) {
            return null;
        }
        return new StockSymbolDTO(
                stockSymbol.getId(),
                stockSymbol.getTicker(),
                stockSymbol.getCompanyName()
        );
    }

    public static StockSymbol toEntity(StockSymbolDTO stockSymbolDTO) {
        if (stockSymbolDTO == null) {
            return null;
        }
        return new StockSymbol(
                stockSymbolDTO.getId(),
                stockSymbolDTO.getTicker(),
                stockSymbolDTO.getCompanyName(),
                null
        );
    }
}

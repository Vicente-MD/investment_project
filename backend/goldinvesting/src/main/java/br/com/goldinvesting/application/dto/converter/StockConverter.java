package br.com.goldinvesting.application.dto.converter;

import br.com.goldinvesting.application.dto.StockDTO;
import br.com.goldinvesting.domain.model.Stock;

public class StockConverter {

    public static StockDTO toDTO(Stock stock) {
        if (stock == null) {
            return null;
        }

        return new StockDTO(
                stock.getId(),
                stock.getStockSymbol(),
                stock.getBroker(),
                stock.getQuantity(),
                stock.getInitialInvestmentValue(),
                stock.getPurchaseDate(),
                stock.getPurchasePrice(),
                null
        );
    }

    public static Stock toEntity(StockDTO stockDTO) {
        if (stockDTO == null) {
            return null;
        }

        Stock stock = new Stock();
        stock.setId(stockDTO.getId());
        stock.setStockSymbol(stockDTO.getStockSymbol());
        stock.setBroker(stockDTO.getBroker());
        stock.setQuantity(stockDTO.getQuantity());
        stock.setInitialInvestmentValue(stockDTO.getInitialInvestmentValue());
        stock.setPurchaseDate(stockDTO.getPurchaseDate());
        stock.setPurchasePrice(stockDTO.getPurchasePrice());

        return stock;
    }
}
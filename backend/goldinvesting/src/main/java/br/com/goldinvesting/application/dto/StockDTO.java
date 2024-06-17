package br.com.goldinvesting.application.dto;

import br.com.goldinvesting.domain.model.Broker;
import br.com.goldinvesting.domain.model.StockSymbol;
import br.com.goldinvesting.domain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private Long id;
    private StockSymbol stockSymbol;
    private Broker broker;
    private double quantity;
    private double initialInvestmentValue;
    private LocalDate purchaseDate;
    private double purchasePrice;
    private Transaction transaction;
}

package br.com.goldinvesting.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity representing a stock symbol (e.g., AAPL for Apple).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockSymbol {

    /**
     * Unique identifier for the stock symbol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ticker symbol of the stock (e.g., AAPL).
     * This field is mandatory and must be between 1 and 100 characters.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String ticker;

    /**
     * Name of the company associated with the stock.
     * This field is mandatory and must be between 1 and 100 characters.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String companyName;

    /**
     * List of stocks associated with this stock symbol.
     * This field is ignored in JSON serialization.
     */
    @OneToMany(mappedBy = "stockSymbol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Stock> stocks;
}

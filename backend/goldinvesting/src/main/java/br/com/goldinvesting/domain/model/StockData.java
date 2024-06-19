package br.com.goldinvesting.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing stock data for a specific date.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockData {

    /**
     * Unique identifier for the stock data.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The stock symbol associated with this stock data.
     * This field is mandatory and should not be null.
     */
    @ManyToOne
    @NotNull
    private StockSymbol stockSymbol;

    /**
     * The date for this stock data.
     */
    private LocalDate date;

    /**
     * The value of the stock on the specified date.
     */
    private double value;
}

package br.com.goldinvesting.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity representing a Stock.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock extends Investment  {

    /**
     * The StockSymbol associated with this stock.
     * This field is mandatory and should not be null.
     */
    @ManyToOne
    @NotNull
    @JsonIgnore
    private StockSymbol stockSymbol;

    /**
     * The Broker associated with this stock.
     * This field is mandatory and should not be null.
     */
    @ManyToOne
    @NotNull
    @JsonIgnore
    private Broker broker;

    /**
     * The quantity of the stock.
     * This field must be greater than 0.0.
     */
    @DecimalMin(value = "0.0", inclusive = false)
    private double quantity;

    /**
     * The initial investment value of the stock.
     * This field must be greater than 0.0.
     */
    @DecimalMin(value = "0.0", inclusive = false)
    private double initialInvestmentValue;

    /**
     * The date the stock was purchased.
     * This field is mandatory and must be a date in the past or present.
     */
    @NotNull
    @PastOrPresent
    private LocalDate purchaseDate;

    /**
     * The purchase price of the stock.
     * This field must be greater than 0.0.
     */
    @DecimalMin(value = "0.0", inclusive = false)
    private double purchasePrice;
}


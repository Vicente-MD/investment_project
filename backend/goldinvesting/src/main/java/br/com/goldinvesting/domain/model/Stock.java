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

// ação

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock extends Investment  {
    @ManyToOne
    @NotNull
    @JsonIgnore
    private StockSymbol stockSymbol;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Broker broker;

    @DecimalMin(value = "0.0", inclusive = false)
    private double quantity;

    @DecimalMin(value = "0.0", inclusive = false)
    private double initialInvestmentValue;

    @NotNull
    @PastOrPresent
    private LocalDate purchaseDate;

    @DecimalMin(value = "0.0", inclusive = false)
    private double purchasePrice;
}

package br.com.goldinvesting.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private StockDB stock;

    @ManyToOne
    @NotNull
    private Broker broker;

    @DecimalMin(value = "0.0", inclusive = false)
    private double quantity;

    @DecimalMin(value = "0.0", inclusive = false)
    private double initialValue;

    @NotNull
    @PastOrPresent
    private LocalDate initialDate;

    @DecimalMin(value = "0.0", inclusive = false)
    private double price;
}

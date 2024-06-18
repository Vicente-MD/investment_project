package br.com.goldinvesting.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// renda fixa

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FixedIncome extends Investment {
    @NotNull
    @Size(min = 1, max = 100)
    private String paper;

    @NotNull
    @Size(min = 1, max = 100)
    private String issuer;

    @DecimalMin(value = "0.0", inclusive = false)
    private double yieldRate;

    @NotNull
    @PastOrPresent
    private LocalDate initialDate;

    @NotNull
    private LocalDate finalDate;

    @DecimalMin(value = "0.0", inclusive = false)
    private double initialValue;

    @ManyToOne
    @NotNull
    private Broker broker;
}

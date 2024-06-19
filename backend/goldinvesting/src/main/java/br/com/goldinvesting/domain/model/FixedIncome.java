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

/**
 * Entity representing a fixed income investment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FixedIncome extends Investment {

    /**
     * The paper (security) associated with the fixed income investment.
     * This field is mandatory and must be between 1 and 100 characters.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String paper;

    /**
     * The issuer of the fixed income investment.
     * This field is mandatory and must be between 1 and 100 characters.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String issuer;

    /**
     * The yield rate of the fixed income investment.
     * This field must be greater than 0.0.
     */
    @DecimalMin(value = "0.0", inclusive = false)
    private double yieldRate;

    /**
     * The initial date of the fixed income investment.
     * This field is mandatory and must be a date in the past or present.
     */
    @NotNull
    @PastOrPresent
    private LocalDate initialDate;

    /**
     * The final date of the fixed income investment.
     * This field is mandatory.
     */
    @NotNull
    private LocalDate finalDate;

    /**
     * The initial value of the fixed income investment.
     * This field must be greater than 0.0.
     */
    @DecimalMin(value = "0.0", inclusive = false)
    private double initialValue;

    /**
     * The broker associated with the fixed income investment.
     * This field is mandatory and should not be null.
     */
    @ManyToOne
    @NotNull
    private Broker broker;
}

package br.com.goldinvesting.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity representing a checking account investment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CheckingAccount extends Investment {

    /**
     * Title of the checking account.
     * This field is mandatory and must be between 1 and 100 characters.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    /**
     * Yield rate of the checking account.
     * This field must be greater than 0.0.
     */
    @DecimalMin(value = "0.0", inclusive = false)
    private double yieldRate;

    /**
     * Initial date of the checking account.
     * This field is mandatory and must be a date in the past or present.
     */
    @NotNull
    @PastOrPresent
    private LocalDate initialDate;

    /**
     * Initial value of the checking account.
     * This field must be greater than 0.0.
     */
    @DecimalMin(value = "0.0", inclusive = false)
    private double initialValue;

    /**
     * Broker associated with the checking account.
     * This field is mandatory and should not be null.
     */
    @ManyToOne
    @NotNull
    private Broker broker;
}

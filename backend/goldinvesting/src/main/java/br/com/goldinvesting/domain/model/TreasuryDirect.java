package br.com.goldinvesting.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TreasuryDirect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

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

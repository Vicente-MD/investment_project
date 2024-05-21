package br.com.goldinvesting.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonInclude()
    @Transient
    private Investment investment;

    @ManyToOne
    @NotNull
    private InvestmentType investmentType;

    @ManyToOne
    @NotNull
    private Wallet wallet;

    @ManyToOne
    @NotNull
    private Status status;
}

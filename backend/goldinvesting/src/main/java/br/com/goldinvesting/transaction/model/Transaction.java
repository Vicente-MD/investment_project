package br.com.goldinvesting.transaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

import br.com.goldinvesting.investmenttype.model.InvestmentType;
import br.com.goldinvesting.status.model.Status;
import br.com.goldinvesting.wallet.model.Wallet;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private long id;

    @JsonInclude()
    @Transient
    private FeignInvestment investment;

    @ManyToOne
    private InvestmentType investmentType;

    @ManyToOne
    private Wallet wallet;

    @ManyToOne
    private Status status;
}

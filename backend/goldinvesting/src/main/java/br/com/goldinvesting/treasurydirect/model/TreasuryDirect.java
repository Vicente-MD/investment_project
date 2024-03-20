package br.com.goldinvesting.treasurydirect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import br.com.goldinvesting.broker.model.Broker;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class TreasuryDirect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String issuer;

    private double yieldRate;

    private String initialDate;

    private String finalDate;

    private double initialValue;

    @ManyToOne
    private Broker broker;
}

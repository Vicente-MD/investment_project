package br.com.goldinvesting.stock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import br.com.goldinvesting.broker.model.Broker;
import br.com.goldinvesting.stockdb.model.StockDB;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private StockDB stock;

    @ManyToOne
    private Broker broker;

    private double quantity;

    private double initialValue;

    private String initialDate;

    private double price;

}

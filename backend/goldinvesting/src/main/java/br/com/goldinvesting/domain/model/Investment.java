package br.com.goldinvesting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Investment {
    private long id;
    private String title;
    private double yieldRate;
    private String initialDate;
    private double initialValue;
    private Broker broker;
    private String paper;
    private String issuer;
    private String finalDate;
    private StockDB stock;
    private double quantity;
    private double price;
}

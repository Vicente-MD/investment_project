package br.com.goldinvesting.stockdb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StockDB {
    @Id
    private String id;
    private String name;
}

package br.com.goldinvesting.stock.model;

import br.com.goldinvesting.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockDTO {
    private Stock stock;
    private User user;
}

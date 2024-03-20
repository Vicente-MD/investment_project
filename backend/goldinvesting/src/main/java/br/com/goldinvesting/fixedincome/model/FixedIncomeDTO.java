package br.com.goldinvesting.fixedincome.model;

import br.com.goldinvesting.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixedIncomeDTO {
    private FixedIncomeModel fixedIncome;
    private User user;
}

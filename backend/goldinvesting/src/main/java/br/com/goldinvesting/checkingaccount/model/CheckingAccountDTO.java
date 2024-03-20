package br.com.goldinvesting.checkingaccount.model;

import br.com.goldinvesting.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckingAccountDTO {
    private CheckingAccount checkingAccount;
    private User user;
}

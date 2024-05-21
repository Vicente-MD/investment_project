package br.com.goldinvesting.application.ports.in;

import br.com.goldinvesting.application.dto.CheckingAccountDTO;
import br.com.goldinvesting.domain.model.CheckingAccount;

import java.util.List;

public interface CheckingAccountUseCase {
    CheckingAccount createCheckingAccount(CheckingAccountDTO checkingAccountDTO);
    CheckingAccount getCheckingAccountById(long id);
    void deleteCheckingAccount(long id);
    List<CheckingAccount> getCheckingAccounts();
    void concludeCheckingAccount(long id);
}

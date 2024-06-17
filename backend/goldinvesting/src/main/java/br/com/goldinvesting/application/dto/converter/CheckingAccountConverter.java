package br.com.goldinvesting.application.dto.converter;

import br.com.goldinvesting.application.dto.CheckingAccountDTO;
import br.com.goldinvesting.domain.model.CheckingAccount;

public class CheckingAccountConverter {

    public static CheckingAccountDTO toDTO(CheckingAccount checkingAccount) {
        if (checkingAccount == null) {
            return null;
        }
        return new CheckingAccountDTO(
                checkingAccount.getId(),
                checkingAccount.getTitle(),
                checkingAccount.getYieldRate(),
                checkingAccount.getInitialDate(),
                checkingAccount.getInitialValue(),
                checkingAccount.getBroker(),
                null
        );
    }

    public static CheckingAccount toEntity(CheckingAccountDTO checkingAccountDTO) {
        if (checkingAccountDTO == null) {
            return null;
        }
        var checkingAccount = new CheckingAccount();
        checkingAccount.setId(checkingAccountDTO.getId());
        checkingAccount.setTitle(checkingAccountDTO.getTitle());
        checkingAccount.setYieldRate(checkingAccountDTO.getYieldRate());
        checkingAccount.setInitialDate(checkingAccountDTO.getInitialDate());
        checkingAccount.setInitialValue(checkingAccountDTO.getInitialValue());
        checkingAccount.setBroker(checkingAccountDTO.getBroker());

        return checkingAccount;
    }
}

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
        return new CheckingAccount(
                checkingAccountDTO.getId(),
                checkingAccountDTO.getTitle(),
                checkingAccountDTO.getYieldRate(),
                checkingAccountDTO.getInitialDate(),
                checkingAccountDTO.getInitialValue(),
                checkingAccountDTO.getBroker()
        );
    }
}

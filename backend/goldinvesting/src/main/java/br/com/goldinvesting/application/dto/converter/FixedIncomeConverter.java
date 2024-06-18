package br.com.goldinvesting.application.dto.converter;

import br.com.goldinvesting.application.dto.FixedIncomeDTO;
import br.com.goldinvesting.domain.model.FixedIncome;

public class FixedIncomeConverter {

    public static FixedIncomeDTO toDTO(FixedIncome fixedIncome) {
        return new FixedIncomeDTO(
                fixedIncome.getId(),
                fixedIncome.getPaper(),
                fixedIncome.getIssuer(),
                fixedIncome.getYieldRate(),
                fixedIncome.getInitialDate(),
                fixedIncome.getFinalDate(),
                fixedIncome.getInitialValue(),
                fixedIncome.getBroker());
    }

    public static FixedIncome toEntity(FixedIncomeDTO fixedIncomeDTO) {
        return new FixedIncome(
                fixedIncomeDTO.getPaper(),
                fixedIncomeDTO.getIssuer(),
                fixedIncomeDTO.getYieldRate(),
                fixedIncomeDTO.getInitialDate(),
                fixedIncomeDTO.getFinalDate(),
                fixedIncomeDTO.getInitialValue(),
                fixedIncomeDTO.getBroker());
    }
}

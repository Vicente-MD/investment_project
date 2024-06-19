package br.com.goldinvesting.application.ports.in;

import br.com.goldinvesting.application.dto.FixedIncomeDTO;

import java.util.List;

public interface FixedIncomeUseCase {
    FixedIncomeDTO createFixedIncome(FixedIncomeDTO FixedIncomeDTO, long userId);
    FixedIncomeDTO getFixedIncomeById(long id);
    void deleteFixedIncome(long id);
    List<FixedIncomeDTO> getFixedIncomes(long userId);
}

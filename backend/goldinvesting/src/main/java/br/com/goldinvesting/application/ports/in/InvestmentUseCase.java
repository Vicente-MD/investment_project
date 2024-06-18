package br.com.goldinvesting.application.ports.in;

import br.com.goldinvesting.application.dto.InvestmentDTO;
import br.com.goldinvesting.application.dto.InvestmentDataDTO;
import br.com.goldinvesting.domain.model.CheckingAccount;
import br.com.goldinvesting.domain.model.FixedIncome;
import br.com.goldinvesting.domain.model.Stock;
import br.com.goldinvesting.domain.model.Transaction;

import java.util.List;

public interface InvestmentUseCase {
    List<InvestmentDTO> getInvestments(Long userId);
    List<InvestmentDataDTO> getStockHistory(Stock stock, List<InvestmentDataDTO> generalHistory, Transaction transaction);
    List<InvestmentDataDTO> getFixedIncomeHistory(FixedIncome fixedIncome, List<InvestmentDataDTO> generalHistory, Transaction transaction);
    List<InvestmentDataDTO> getCheckingAccountHistory(CheckingAccount checkingAccount, List<InvestmentDataDTO> generalHistory, Transaction transaction);
    List<InvestmentDataDTO> getAllInvestmentHistories(Long userId);
}

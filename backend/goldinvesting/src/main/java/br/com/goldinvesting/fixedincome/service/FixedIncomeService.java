package br.com.goldinvesting.fixedincome.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.goldinvesting.fixedincome.model.FixedIncomeDTO;
import br.com.goldinvesting.fixedincome.model.FixedIncomeModel;
import br.com.goldinvesting.fixedincome.repository.FixedIncomeRepository;
import br.com.goldinvesting.investmenttype.repository.InvestmentTypeRepository;
import br.com.goldinvesting.status.repository.StatusRepository;
import br.com.goldinvesting.transaction.model.FeignInvestment;
import br.com.goldinvesting.transaction.model.Transaction;
import br.com.goldinvesting.transaction.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FixedIncomeService {
    private final FixedIncomeRepository fixedIncomeRepository;
    private final TransactionRepository transactionRepository;
    private final InvestmentTypeRepository investmentTypeRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public FixedIncomeModel createFixedIncome(FixedIncomeDTO fixedIncomeDTO) {
        var fixedIncome = fixedIncomeRepository.save(fixedIncomeDTO.getFixedIncome());
        var investment = FeignInvestment.builder()
                .id(fixedIncome.getId())
                .broker(fixedIncome.getBroker())
                .paper(fixedIncome.getPaper())
                .issuer(fixedIncome.getIssuer())
                .initialValue(fixedIncome.getInitialValue())
                .initialDate(fixedIncome.getInitialDate())
                .finalDate(fixedIncome.getFinalDate())
                .yieldRate(fixedIncome.getYieldRate()).build();
        var investmentType = investmentTypeRepository.findByInvestmentType("FIXED_INCOME").get();
        var status = statusRepository.findByStatus("ACTIVE").get();
        var wallet = fixedIncomeDTO.getUser().getWallet();
        var id = "" + investmentType.getId() + investment.getId();
        var transaction = new Transaction(Long.parseLong(id), investment, investmentType, wallet, status);
        transactionRepository.save(transaction);

        return fixedIncome;
    }

    @Transactional
    public FixedIncomeModel getFixedIncomeById(long id) {
        var fixedIncome = fixedIncomeRepository.findById(id);
        if (fixedIncome.isPresent())
            return fixedIncome.get();
        return null;
    }

    @Transactional
    public void deleteFixedIncome(long id) {
        fixedIncomeRepository.deleteById(id);
    }

    @Transactional
    public List<FixedIncomeModel> getFixedIncomes() {
        return fixedIncomeRepository.findAll();
    }
}


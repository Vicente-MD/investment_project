package br.com.goldinvesting.checkingaccount.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.goldinvesting.checkingaccount.model.CheckingAccount;
import br.com.goldinvesting.checkingaccount.model.CheckingAccountDTO;
import br.com.goldinvesting.checkingaccount.repository.CheckingAccountRepository;
import br.com.goldinvesting.investmenttype.repository.InvestmentTypeRepository;
import br.com.goldinvesting.status.repository.StatusRepository;
import br.com.goldinvesting.transaction.model.FeignInvestment;
import br.com.goldinvesting.transaction.model.Transaction;
import br.com.goldinvesting.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckingAccountService {
    private final CheckingAccountRepository checkingAccountRepository;
    private final TransactionRepository transactionRepository;
    private final InvestmentTypeRepository investmentTypeRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public CheckingAccount createCheckingAccount(CheckingAccountDTO checkingAccountDTO) {
        var checkingAccount = checkingAccountRepository.save(checkingAccountDTO.getCheckingAccount());
        var investment = FeignInvestment.builder()
                .id(checkingAccount.getId())
                .broker(checkingAccount.getBroker())
                .initialValue(checkingAccount.getInitialValue())
                .initialDate(checkingAccount.getInitialDate())
                .yieldRate(checkingAccount.getYieldRate())
                .title(checkingAccount.getTitle()).build();
        var investmentType = investmentTypeRepository.findByInvestmentType("CHECKING_ACCOUNT").get();
        var status = statusRepository.findByStatus("ACTIVE").get();
        var wallet = checkingAccountDTO.getUser().getWallet();
        var id = "" + investmentType.getId() + investment.getId();
        var transaction = new Transaction(Long.parseLong(id), investment, investmentType, wallet, status);
        transactionRepository.save(transaction);

        return checkingAccount;
    }

    @Transactional
    public CheckingAccount getCheckingAccountById(long id) {
        var checkingAccount = checkingAccountRepository.findById(id);
        if (checkingAccount.isPresent())
            return checkingAccount.get();
        return null;
    }

    @Transactional
    public void deleteCheckingAccount(long id) {
        checkingAccountRepository.deleteById(id);
    }

    @Transactional
    public List<CheckingAccount> getCheckingAccounts() {
        return checkingAccountRepository.findAll();
    }

    @Transactional
    public void concludeCheckingAccount(long id) {
        var investmentType = investmentTypeRepository.findByInvestmentType("CHECKING_ACCOUNT").get();
        var idTransaction = "" + investmentType.getId() + id;
        transactionRepository.setStatus(Long.parseLong(idTransaction), Long.valueOf(2));
    }
}

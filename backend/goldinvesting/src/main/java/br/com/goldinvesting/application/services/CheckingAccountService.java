package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.dto.CheckingAccountDTO;
import br.com.goldinvesting.application.dto.converter.CheckingAccountConverter;
import br.com.goldinvesting.application.ports.in.CheckingAccountUseCase;
import br.com.goldinvesting.application.ports.out.CheckingAccountRepository;
import br.com.goldinvesting.domain.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckingAccountService implements CheckingAccountUseCase {
    private final CheckingAccountRepository checkingAccountRepository;
//    private final TransactionRepository transactionRepository;
//    private final InvestmentTypeRepository investmentTypeRepository;
//    private final StatusRepository statusRepository;

    @Transactional
    @Override
    public CheckingAccount createCheckingAccount(CheckingAccountDTO checkingAccountDTO) {
        CheckingAccount checkingAccount = CheckingAccountConverter.toEntity(checkingAccountDTO);
        CheckingAccount savedCheckingAccount = checkingAccountRepository.save(checkingAccount);

//        Investment investment = new Investment(
//                savedCheckingAccount.getId(),
//                savedCheckingAccount.getTitle(),
//                savedCheckingAccount.getYieldRate(),
//                savedCheckingAccount.getInitialDate().toString(),
//                savedCheckingAccount.getInitialValue(),
//                savedCheckingAccount.getBroker(),
//                null,
//                null,
//                null,
//                null,
//                0,
//                0
//        );


//        InvestmentType investmentType = investmentTypeRepository.findByInvestmentType("CHECKING_ACCOUNT")
//                .orElseThrow(() -> new IllegalStateException("Investment type CHECKING_ACCOUNT not found"));
//        Status status = statusRepository.findByStatus("ACTIVE")
//                .orElseThrow(() -> new IllegalStateException("Status ACTIVE not found"));
//
//        String id = investmentType.getId() + String.valueOf(investment.getId());
//        Transaction transaction = new Transaction(Long.parseLong(id), investment, investmentType, checkingAccountDTO.getUser().getWallet(), status);
//        transactionRepository.save(transaction);

        return savedCheckingAccount;
    }

    @Transactional
    @Override
    public CheckingAccount getCheckingAccountById(long id) {
        return checkingAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Checking account not found"));
    }

    @Transactional
    @Override
    public void deleteCheckingAccount(long id) {
        checkingAccountRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<CheckingAccount> getCheckingAccounts() {
        return checkingAccountRepository.findAll();
    }

    @Transactional
    @Override
    public void concludeCheckingAccount(long id) {
//        transactionRepository.setStatus(Status.SOLD, 2L);
    }
}

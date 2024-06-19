package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.dto.CheckingAccountDTO;
import br.com.goldinvesting.application.dto.converter.CheckingAccountConverter;
import br.com.goldinvesting.application.ports.in.CheckingAccountUseCase;
import br.com.goldinvesting.application.ports.out.BrokerRepository;
import br.com.goldinvesting.application.ports.out.CheckingAccountRepository;
import br.com.goldinvesting.application.ports.out.TransactionRepository;
import br.com.goldinvesting.application.ports.out.UserRepository;
import br.com.goldinvesting.domain.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckingAccountService implements CheckingAccountUseCase {
    private final CheckingAccountRepository checkingAccountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BrokerRepository brokerRepository;

    @Transactional
    @Override
    public CheckingAccountDTO createCheckingAccount(CheckingAccountDTO checkingAccountDTO, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Broker broker = brokerRepository.findById(checkingAccountDTO.getBroker().getId())
                .orElseThrow(() -> new IllegalArgumentException("Broker not found"));

        CheckingAccount checkingAccount = CheckingAccountConverter.toEntity(checkingAccountDTO);
        checkingAccount.setBroker(broker);
        checkingAccount.setInvestmentType(InvestmentType.STOCK);

        CheckingAccount checkingAccountSaved = checkingAccountRepository.save(checkingAccount);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setStatus(Status.ACTIVE);
        transaction.setInvestment(checkingAccount);

        transactionRepository.save(transaction);

        return CheckingAccountConverter.toDTO(checkingAccountSaved);
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
        var transaction = transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transaction.setStatus(Status.CONCLUDED);
        transactionRepository.save(transaction);
    }
}

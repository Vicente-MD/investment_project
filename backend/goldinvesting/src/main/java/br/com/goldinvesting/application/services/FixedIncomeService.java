package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.ports.in.FixedIncomeUseCase;
import br.com.goldinvesting.application.dto.FixedIncomeDTO;
import br.com.goldinvesting.application.dto.converter.FixedIncomeConverter;
import br.com.goldinvesting.application.ports.out.BrokerRepository;
import br.com.goldinvesting.application.ports.out.FixedIncomeRepository;
import br.com.goldinvesting.application.ports.out.FixedIncomeSymbolRepository;
import br.com.goldinvesting.application.ports.out.TransactionRepository;
import br.com.goldinvesting.application.ports.out.UserRepository;
import br.com.goldinvesting.domain.model.Broker;
import br.com.goldinvesting.domain.model.InvestmentType;
import br.com.goldinvesting.domain.model.Status;
import br.com.goldinvesting.domain.model.FixedIncome;
import br.com.goldinvesting.domain.model.Transaction;
import br.com.goldinvesting.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FixedIncomeService implements FixedIncomeUseCase {

    private final FixedIncomeRepository fixedIncomeRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BrokerRepository brokerRepository;

    @Override
    public FixedIncomeDTO createFixedIncome(FixedIncomeDTO fixedIncomeDTO, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Broker broker = brokerRepository.findById(fixedIncomeDTO.getBroker().getId()).orElseThrow(() -> new IllegalArgumentException("Broker not found"));

        FixedIncome fixedIncome = FixedIncomeConverter.toEntity(fixedIncomeDTO);
        fixedIncome.setBroker(broker);
        fixedIncome.setInvestmentType(InvestmentType.FIXED_INCOME);
        
        FixedIncome fixedIncomeSaved = fixedIncomeRepository.save(fixedIncome);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setStatus(Status.ACTIVE);
        transaction.setInvestment(fixedIncome);

        transactionRepository.save(transaction);

        return FixedIncomeConverter.toDTO(fixedIncomeSaved);
    }

    @Override
    public FixedIncomeDTO getFixedIncomeById(long id) {
        return fixedIncomeRepository.findById(id)
                .map(FixedIncomeConverter::toDTO)
                .orElse(null);
    }

    @Override
    public void deleteFixedIncome(long id) {
        fixedIncomeRepository.deleteById(id);
    }

    @Override
    public List<FixedIncomeDTO> getFixedIncomes() {
        return fixedIncomeRepository.findAll().stream()
                .map(FixedIncomeConverter::toDTO)
                .collect(Collectors.toList());
    }
}
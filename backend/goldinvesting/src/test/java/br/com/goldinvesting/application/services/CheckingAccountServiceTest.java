package br.com.goldinvesting.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.goldinvesting.application.dto.CheckingAccountDTO;
import br.com.goldinvesting.application.dto.converter.CheckingAccountConverter;
import br.com.goldinvesting.application.ports.out.BrokerRepository;
import br.com.goldinvesting.application.ports.out.CheckingAccountRepository;
import br.com.goldinvesting.application.ports.out.TransactionRepository;
import br.com.goldinvesting.application.ports.out.UserRepository;
import br.com.goldinvesting.domain.model.Broker;
import br.com.goldinvesting.domain.model.CheckingAccount;
import br.com.goldinvesting.domain.model.Status;
import br.com.goldinvesting.domain.model.Transaction;
import br.com.goldinvesting.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class CheckingAccountServiceTest {

    @Mock
    private CheckingAccountRepository checkingAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BrokerRepository brokerRepository;

    @InjectMocks
    private CheckingAccountService checkingAccountService;

    private CheckingAccountDTO checkingAccountDTO;
    private CheckingAccount checkingAccount;
    private User user;
    private Broker broker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        broker = new Broker(1L, "XP Investimentos");

        checkingAccountDTO = new CheckingAccountDTO();
        checkingAccountDTO.setId(1L);
        checkingAccountDTO.setBroker(broker);

        user = new User();
        user.setId(1L);

        broker.setId(1L);

        checkingAccount = CheckingAccountConverter.toEntity(checkingAccountDTO);
        checkingAccount.setBroker(broker);
    }

    @Test
    void testCreateCheckingAccount() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(brokerRepository.findById(1L)).thenReturn(Optional.of(broker));
        when(checkingAccountRepository.save(any(CheckingAccount.class))).thenReturn(checkingAccount);

        CheckingAccountDTO createdCheckingAccountDTO = checkingAccountService.createCheckingAccount(checkingAccountDTO, 1L);

        assertNotNull(createdCheckingAccountDTO, "The created CheckingAccountDTO should not be null");
        assertEquals(checkingAccount.getId(), createdCheckingAccountDTO.getId(), "The checking account ID should match");
        assertEquals(checkingAccount.getBroker().getId(), createdCheckingAccountDTO.getBroker().getId(), "The Broker ID should match");

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testGetCheckingAccountById() {
        when(checkingAccountRepository.findById(1L)).thenReturn(Optional.of(checkingAccount));

        CheckingAccount foundCheckingAccount = checkingAccountService.getCheckingAccountById(1L);

        assertNotNull(foundCheckingAccount, "The found CheckingAccount should not be null");
        assertEquals(checkingAccount.getId(), foundCheckingAccount.getId(), "The checking account ID should match");

        verify(checkingAccountRepository, times(1)).findById(1L);
    }

    @Test
    void testConcludeCheckingAccount() {
        Transaction transaction = new Transaction();
        transaction.setInvestment(checkingAccount);
        transaction.setStatus(Status.ACTIVE);

        when(transactionRepository.findByInvestmentId(1L)).thenReturn(List.of(transaction));

        checkingAccountService.concludeCheckingAccount(1L);

        assertEquals(Status.CONCLUDED, transaction.getStatus(), "The transaction status should be CONCLUDED");

        verify(transactionRepository, times(1)).save(transaction);
    }
}

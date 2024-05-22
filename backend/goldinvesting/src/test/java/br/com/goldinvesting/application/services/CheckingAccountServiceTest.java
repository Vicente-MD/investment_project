package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.dto.CheckingAccountDTO;
import br.com.goldinvesting.application.ports.out.CheckingAccountRepository;
import br.com.goldinvesting.application.ports.out.InvestmentTypeRepository;
import br.com.goldinvesting.application.ports.out.StatusRepository;
import br.com.goldinvesting.application.ports.out.TransactionRepository;
import br.com.goldinvesting.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckingAccountServiceTest {

    @Mock
    private CheckingAccountRepository checkingAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private InvestmentTypeRepository investmentTypeRepository;

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private CheckingAccountService checkingAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCheckingAccount() {
        CheckingAccountDTO checkingAccountDTO = new CheckingAccountDTO();
        checkingAccountDTO.setTitle("Checking Account 1");
        checkingAccountDTO.setYieldRate(1.5);
        checkingAccountDTO.setInitialDate(LocalDate.now());
        checkingAccountDTO.setInitialValue(1000.0);
        checkingAccountDTO.setBroker(new Broker(1L, "Broker 1"));
        User user = new User();
        Wallet wallet = new Wallet();
        user.setWallet(wallet);
        checkingAccountDTO.setUser(user);

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setId(1L);
        checkingAccount.setTitle(checkingAccountDTO.getTitle());
        checkingAccount.setYieldRate(checkingAccountDTO.getYieldRate());
        checkingAccount.setInitialDate(checkingAccountDTO.getInitialDate());
        checkingAccount.setInitialValue(checkingAccountDTO.getInitialValue());
        checkingAccount.setBroker(checkingAccountDTO.getBroker());

        when(checkingAccountRepository.save(any(CheckingAccount.class))).thenReturn(checkingAccount);
        when(investmentTypeRepository.findByInvestmentType("CHECKING_ACCOUNT")).thenReturn(Optional.of(new InvestmentType(1L, "CHECKING_ACCOUNT")));
        when(statusRepository.findByStatus("ACTIVE")).thenReturn(Optional.of(new Status(1L, "ACTIVE")));

        CheckingAccount savedCheckingAccount = checkingAccountService.createCheckingAccount(checkingAccountDTO);

        assertNotNull(savedCheckingAccount);
        assertEquals(checkingAccount.getTitle(), savedCheckingAccount.getTitle());

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionCaptor.capture());
        Transaction savedTransaction = transactionCaptor.getValue();
        assertEquals("CHECKING_ACCOUNT", savedTransaction.getInvestmentType().getInvestmentType());
        assertEquals("ACTIVE", savedTransaction.getStatus().getStatus());
    }

    @Test
    void getCheckingAccountById() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setId(1L);

        when(checkingAccountRepository.findById(1L)).thenReturn(Optional.of(checkingAccount));

        CheckingAccount foundCheckingAccount = checkingAccountService.getCheckingAccountById(1L);

        assertNotNull(foundCheckingAccount);
        assertEquals(1L, foundCheckingAccount.getId());
    }

    @Test
    void deleteCheckingAccount() {
        checkingAccountService.deleteCheckingAccount(1L);
        verify(checkingAccountRepository, times(1)).deleteById(1L);
    }

    @Test
    void getCheckingAccounts() {
        checkingAccountService.getCheckingAccounts();
        verify(checkingAccountRepository, times(1)).findAll();
    }

    @Test
    void concludeCheckingAccount() {
        InvestmentType investmentType = new InvestmentType(1L, "CHECKING_ACCOUNT");
        when(investmentTypeRepository.findByInvestmentType("CHECKING_ACCOUNT")).thenReturn(Optional.of(investmentType));

        checkingAccountService.concludeCheckingAccount(1L);

        verify(transactionRepository, times(1)).setStatus(Long.parseLong(investmentType.getId() + "1"), 2L);
    }
}

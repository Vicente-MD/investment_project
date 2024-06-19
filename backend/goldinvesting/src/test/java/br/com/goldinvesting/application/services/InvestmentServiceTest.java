package br.com.goldinvesting.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.goldinvesting.application.dto.InvestmentDTO;
import br.com.goldinvesting.application.dto.InvestmentDataDTO;
import br.com.goldinvesting.application.ports.out.StockDataRepository;
import br.com.goldinvesting.application.ports.out.TransactionRepository;
import br.com.goldinvesting.application.services.InvestmentService;
import br.com.goldinvesting.domain.model.Broker;
import br.com.goldinvesting.domain.model.FixedIncome;
import br.com.goldinvesting.domain.model.InvestmentType;
import br.com.goldinvesting.domain.model.Status;
import br.com.goldinvesting.domain.model.Stock;
import br.com.goldinvesting.domain.model.StockData;
import br.com.goldinvesting.domain.model.StockSymbol;
import br.com.goldinvesting.domain.model.Transaction;
import br.com.goldinvesting.domain.model.User;

public class InvestmentServiceTest {

    @InjectMocks
    private InvestmentService investmentService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private StockDataRepository stockDataRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetInvestments() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john@example.com", "password");
        FixedIncome fixedIncome = new FixedIncome();
        fixedIncome.setInvestmentType(InvestmentType.FIXED_INCOME);
        Transaction transaction = new Transaction(1L, user, Status.ACTIVE, fixedIncome);

        when(transactionRepository.findByUserId(userId)).thenReturn(Collections.singletonList(transaction));

        List<InvestmentDTO> investments = investmentService.getInvestments(userId);

        assertEquals(1, investments.size());
        assertEquals("ACTIVE", investments.get(0).getStatus());
    }

    @Test
    public void testGetStockHistory() {
        User user = new User(1L, "John Doe", "john@example.com", "password");
        Stock stock = new Stock();
        stock.setPurchaseDate(LocalDate.now().minusDays(1));
        stock.setQuantity(1);
        stock.setStockSymbol(new StockSymbol(1L, "AAPL", "Apple Inc.", null));
        Transaction transaction = new Transaction(1L, user, Status.ACTIVE, stock);
        StockData stockData = new StockData();
        stockData.setDate(LocalDate.now());
        stockData.setValue(100.0);

        when(stockDataRepository.findByStockSymbolId(stock.getStockSymbol().getId())).thenReturn(Collections.singletonList(stockData));

        List<InvestmentDataDTO> history = investmentService.getStockHistory(stock, Collections.emptyList(), transaction);

        assertEquals(1, history.size());
        assertEquals(100.0, history.get(0).getPrice());
    }


    @Test
    public void testGetFixedIncomeHistory() {
        User user = new User(1L, "John Doe", "john@example.com", "password");
        Broker broker = new Broker(1L, "Broker");
        FixedIncome fixedIncome = new FixedIncome(
            "Government Bond", "Treasury", 5.0, LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), 1000.0, broker);
        fixedIncome.setId(1L);
        fixedIncome.setInvestmentType(InvestmentType.FIXED_INCOME);
        Transaction transaction = new Transaction(1L, user, Status.ACTIVE, fixedIncome);

        List<InvestmentDataDTO> history = investmentService.getFixedIncomeHistory(fixedIncome, new ArrayList<>(), transaction);

        assertEquals(false, history.isEmpty());
        assertEquals(12, history.size());
    }
}


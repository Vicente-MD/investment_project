package br.com.goldinvesting.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.goldinvesting.application.dto.StockDTO;
import br.com.goldinvesting.application.dto.converter.StockConverter;
import br.com.goldinvesting.application.ports.out.BrokerRepository;
import br.com.goldinvesting.application.ports.out.StockRepository;
import br.com.goldinvesting.application.ports.out.StockSymbolRepository;
import br.com.goldinvesting.application.ports.out.TransactionRepository;
import br.com.goldinvesting.application.ports.out.UserRepository;
import br.com.goldinvesting.domain.model.Broker;
import br.com.goldinvesting.domain.model.Status;
import br.com.goldinvesting.domain.model.Stock;
import br.com.goldinvesting.domain.model.StockSymbol;
import br.com.goldinvesting.domain.model.Transaction;
import br.com.goldinvesting.domain.model.User;

class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StockSymbolRepository stockSymbolRepository;

    @Mock
    private BrokerRepository brokerRepository;

    @InjectMocks
    private StockService stockService;

    private StockDTO stockDTO;
    private Stock stock;
    private User user;
    private StockSymbol stockSymbol;
    private Broker broker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setStockSymbol(new StockSymbol(1L, "AAPL", "Apple Inc.", null));
        stockDTO.setBroker(new Broker(1L, "XP Investimentos"));

        user = new User();
        user.setId(1L);

        stockSymbol = new StockSymbol();
        stockSymbol.setId(1L);

        broker = new Broker();
        broker.setId(1L);

        stock = StockConverter.toEntity(stockDTO);
        stock.setStockSymbol(stockSymbol);
        stock.setBroker(broker);
    }

    @Test
    void testCreateStock() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(stockSymbolRepository.findById(1L)).thenReturn(Optional.of(stockSymbol));
        when(brokerRepository.findById(1L)).thenReturn(Optional.of(broker));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        StockDTO createdStockDTO = stockService.createStock(stockDTO, 1L);

        assertNotNull(createdStockDTO, "The created StockDTO should not be null");
        assertEquals(stock.getId(), createdStockDTO.getId(), "The stock ID should match");
        assertEquals(stock.getStockSymbol().getId(), createdStockDTO.getStockSymbol().getId(),
                "The StockSymbol ID should match");
        assertEquals(stock.getBroker().getId(), createdStockDTO.getBroker().getId(), "The Broker ID should match");

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testGetStockById() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        StockDTO foundStockDTO = stockService.getStockById(1L);

        assertNotNull(foundStockDTO, "The found StockDTO should not be null");
        assertEquals(stock.getId(), foundStockDTO.getId(), "The stock ID should match");

        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    void testSellStock() {
        Transaction transaction = new Transaction();
        transaction.setInvestment(stock);
        transaction.setStatus(Status.ACTIVE);

        when(transactionRepository.findByInvestmentId(1L)).thenReturn(List.of(transaction));

        stockService.sellStock(1L);

        assertEquals(Status.SOLD, transaction.getStatus(), "The transaction status should be SOLD");

        verify(transactionRepository, times(1)).save(transaction);
    }
}

package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.ports.in.StockUseCase;
import br.com.goldinvesting.application.dto.StockDTO;
import br.com.goldinvesting.application.dto.converter.StockConverter;
import br.com.goldinvesting.application.ports.out.BrokerRepository;
import br.com.goldinvesting.application.ports.out.StockRepository;
import br.com.goldinvesting.application.ports.out.StockSymbolRepository;
import br.com.goldinvesting.application.ports.out.TransactionRepository;
import br.com.goldinvesting.application.ports.out.UserRepository;
import br.com.goldinvesting.domain.model.Broker;
import br.com.goldinvesting.domain.model.InvestmentType;
import br.com.goldinvesting.domain.model.Status;
import br.com.goldinvesting.domain.model.Stock;
import br.com.goldinvesting.domain.model.StockSymbol;
import br.com.goldinvesting.domain.model.Transaction;
import br.com.goldinvesting.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService implements StockUseCase {

    private final StockRepository stockRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final StockSymbolRepository stockSymbolRepository;
    private final BrokerRepository brokerRepository;

    @Override
    public StockDTO createStock(StockDTO stockDTO, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        StockSymbol stockSymbol = stockSymbolRepository.findById(stockDTO.getStockSymbol().getId()).orElseThrow(() -> new IllegalArgumentException("Stock Symbol not found"));
        Broker broker = brokerRepository.findById(stockDTO.getBroker().getId()).orElseThrow(() -> new IllegalArgumentException("Broker not found"));

        Stock stock = StockConverter.toEntity(stockDTO);
        stock.setStockSymbol(stockSymbol);
        stock.setBroker(broker);
        stock.setInvestmentType(InvestmentType.STOCK);
        
        Stock stockSaved = stockRepository.save(stock);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setStatus(Status.ACTIVE);
        transaction.setInvestment(stock);

        transactionRepository.save(transaction);

        return StockConverter.toDTO(stockSaved);
    }

    @Override
    public StockDTO getStockById(long id) {
        return stockRepository.findById(id)
                .map(StockConverter::toDTO)
                .orElse(null);
    }

    @Override
    public void deleteStock(long id) {
        stockRepository.deleteById(id);
    }

    @Override
    public List<StockDTO> getStocks() {
        return stockRepository.findAll().stream()
                .map(StockConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void sellStock(long id) {
        var transaction = transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transaction.setStatus(Status.SOLD);
        transactionRepository.save(transaction);
    }
}

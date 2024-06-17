package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.ports.in.StockUseCase;
import br.com.goldinvesting.application.dto.StockDTO;
import br.com.goldinvesting.application.dto.converter.StockConverter;
import br.com.goldinvesting.application.ports.out.StockRepository;
import br.com.goldinvesting.application.ports.out.TransactionRepository;
import br.com.goldinvesting.domain.model.Stock;
import br.com.goldinvesting.domain.model.Transaction;
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

    @Override
    public StockDTO createStock(StockDTO stockDTO) {
        Stock stock = StockConverter.toEntity(stockDTO);

        stock = stockRepository.save(stock);

        Transaction transaction = new Transaction();
        transaction.setInvestment(stock);
        transaction.setUser(stockDTO.getTransaction().getUser());
        transaction.setStatus(stockDTO.getTransaction().getStatus());

        transactionRepository.save(transaction);

        StockDTO savedStockDTO = StockConverter.toDTO(stock);
        savedStockDTO.setTransaction(transaction);

        return savedStockDTO;
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
}

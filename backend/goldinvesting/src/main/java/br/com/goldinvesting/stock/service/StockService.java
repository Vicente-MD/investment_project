package br.com.goldinvesting.stock.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.goldinvesting.investmenttype.repository.InvestmentTypeRepository;
import br.com.goldinvesting.status.repository.StatusRepository;
import br.com.goldinvesting.stock.model.Stock;
import br.com.goldinvesting.stock.model.StockDTO;
import br.com.goldinvesting.stock.repository.StockRepository;
import br.com.goldinvesting.transaction.model.FeignInvestment;
import br.com.goldinvesting.transaction.model.Transaction;
import br.com.goldinvesting.transaction.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final TransactionRepository transactionRepository;
    private final InvestmentTypeRepository investmentTypeRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public Stock createStock(StockDTO stockDTO) {
        var stock = stockRepository.save(stockDTO.getStock());
        var investment = FeignInvestment.builder()
                .id(stock.getId())
                .stock(stock.getStock())
                .broker(stock.getBroker())
                .quantity(stock.getQuantity())
                .initialValue(stock.getInitialValue())
                .initialDate(stock.getInitialDate())
                .price(stock.getPrice()).build();
        var investmentType = investmentTypeRepository.findByInvestmentType("STOCK").get();
        var status = statusRepository.findByStatus("ACTIVE").get();
        var wallet = stockDTO.getUser().getWallet();
        var id = "" + investmentType.getId() + stock.getId();
        var transaction = new Transaction(Long.parseLong(id), investment, investmentType, wallet, status);
        transactionRepository.save(transaction);

        return stock;
    }

    @Transactional
    public Stock getStockById(long id) {
        var stock = stockRepository.findById(id);
        if (stock.isPresent())
            return stock.get();
        return null;
    }

    @Transactional
    public void deleteStock(long id) {
        stockRepository.deleteById(id);
    }

    @Transactional
    public List<Stock> getStocks() {
        return stockRepository.findAll();
    }

    @Transactional
    public void concludeStock(long id) {
        var investmentType = investmentTypeRepository.findByInvestmentType("STOCK").get();
        var idTransaction = "" + investmentType.getId() + id;
        transactionRepository.setStatus(Long.parseLong(idTransaction), 2L);
    }
}

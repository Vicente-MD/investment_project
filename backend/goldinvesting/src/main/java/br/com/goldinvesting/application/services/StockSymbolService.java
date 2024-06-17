package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.dto.StockSymbolDTO;
import br.com.goldinvesting.application.dto.converter.StockSymbolConverter;
import br.com.goldinvesting.application.ports.in.StockSymbolUseCase;
import br.com.goldinvesting.application.ports.out.StockSymbolRepository;
import br.com.goldinvesting.domain.model.StockSymbol;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockSymbolService implements StockSymbolUseCase {
    private final StockSymbolRepository stockSymbolRepository;

    @Override
    public List<StockSymbolDTO> getStockSymbolByText(String inputText) {
        List<StockSymbol> stockSymbols = stockSymbolRepository.findByTickerContainingIgnoreCase(inputText);
        return stockSymbols.stream()
                .map(StockSymbolConverter::toDTO)
                .collect(Collectors.toList());
    }
}

package br.com.goldinvesting.infrastructure.adapters.in.api;

import br.com.goldinvesting.application.dto.StockSymbolDTO;
import br.com.goldinvesting.application.ports.in.StockSymbolUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks-symbols")
@RequiredArgsConstructor
public class StockSymbolController {
    private final StockSymbolUseCase stockSymbolUseCase;


    @GetMapping("/get-by-text")
    public ResponseEntity<List<StockSymbolDTO>> getStockSymbolByText(@RequestParam("input") String inputText) {
        return ResponseEntity.ok(stockSymbolUseCase.getStockSymbolByText(inputText));
    }
}

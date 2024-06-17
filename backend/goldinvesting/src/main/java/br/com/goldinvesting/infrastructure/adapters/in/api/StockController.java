package br.com.goldinvesting.infrastructure.adapters.in.api;


import br.com.goldinvesting.application.dto.StockDTO;
import br.com.goldinvesting.application.ports.in.StockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockUseCase stockUseCase;

    @PostMapping
    public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(stockUseCase.createStock(stockDTO));
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        return ResponseEntity.ok(stockUseCase.getStocks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Long id) {
        return ResponseEntity.ok(stockUseCase.getStockById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockUseCase.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
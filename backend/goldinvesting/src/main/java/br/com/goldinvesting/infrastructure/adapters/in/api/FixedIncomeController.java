package br.com.goldinvesting.infrastructure.adapters.in.api;


import br.com.goldinvesting.application.dto.FixedIncomeDTO;
import br.com.goldinvesting.application.ports.in.FixedIncomeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fixed-incomes")
@RequiredArgsConstructor
public class FixedIncomeController {
    private final FixedIncomeUseCase fixedIncomeUseCase;

    @PostMapping("/{userId}")
    public ResponseEntity<FixedIncomeDTO> createFixedIncome(@RequestBody FixedIncomeDTO fixedIncomeDTO, @PathVariable Long userId) {
        return ResponseEntity.ok(fixedIncomeUseCase.createFixedIncome(fixedIncomeDTO, userId));
    }

    @GetMapping
    public ResponseEntity<List<FixedIncomeDTO>> getAllFixedIncomes() {
        return ResponseEntity.ok(fixedIncomeUseCase.getFixedIncomes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FixedIncomeDTO> getFixedIncomeById(@PathVariable Long id) {
        return ResponseEntity.ok(fixedIncomeUseCase.getFixedIncomeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFixedIncome(@PathVariable Long id) {
        fixedIncomeUseCase.deleteFixedIncome(id);
        return ResponseEntity.noContent().build();
    }
}

package br.com.goldinvesting.infrastructure.adapters.in.api;

import br.com.goldinvesting.application.dto.InvestmentDTO;
import br.com.goldinvesting.application.dto.InvestmentDataDTO;
import br.com.goldinvesting.application.ports.in.InvestmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/my-investments")
@RequiredArgsConstructor
public class InvestmentController {
    private final InvestmentUseCase investmentUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<List<InvestmentDTO>> getInvestmentById(@PathVariable long userId) {
        var investments = investmentUseCase.getInvestments(userId);
        if (investments == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(investments);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<InvestmentDataDTO>> getAllInvestmentHistory(@PathVariable long userId) {
        var investments = investmentUseCase.getAllInvestmentHistories(userId);
        if (investments == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(investments);
    }
}

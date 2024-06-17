package br.com.goldinvesting.infrastructure.adapters.in.api;

import br.com.goldinvesting.application.dto.BrokerDTO;
import br.com.goldinvesting.application.ports.in.BrokerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brokers")
@RequiredArgsConstructor
public class BrokerController {
    private final BrokerUseCase brokerUseCase;

    @PostMapping
    public ResponseEntity<BrokerDTO> createBroker(@RequestBody BrokerDTO brokerDTO) {
        return ResponseEntity.ok(brokerUseCase.createBroker(brokerDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrokerDTO> getBrokerById(@PathVariable long id) {
        BrokerDTO brokerDTO = brokerUseCase.getBrokerById(id);
        if (brokerDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brokerDTO);
    }

    @GetMapping("/get-by-text")
    public ResponseEntity<List<BrokerDTO>> getBrokerByText(@RequestParam("input") String inputText) {
        return ResponseEntity.ok(brokerUseCase.getBrokerByText(inputText));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBroker(@PathVariable long id) {
        brokerUseCase.deleteBroker(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BrokerDTO>> getBrokers() {
        return ResponseEntity.ok(brokerUseCase.getBrokers());
    }
}

package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.dto.BrokerDTO;
import br.com.goldinvesting.application.dto.converter.BrokerConverter;
import br.com.goldinvesting.application.ports.in.BrokerUseCase;
import br.com.goldinvesting.application.ports.out.BrokerRepository;
import br.com.goldinvesting.domain.model.Broker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrokerService implements BrokerUseCase {
    private final BrokerRepository brokerRepository;

    @Transactional
    @Override
    public BrokerDTO createBroker(BrokerDTO brokerDTO) {
        Broker broker = BrokerConverter.toEntity(brokerDTO);
        Broker savedBroker = brokerRepository.save(broker);
        return BrokerConverter.toDTO(savedBroker);
    }

    @Transactional
    @Override
    public BrokerDTO getBrokerById(long id) {
        return brokerRepository.findById(id)
                .map(BrokerConverter::toDTO)
                .orElse(null);
    }

    @Transactional
    @Override
    public void deleteBroker(long id) {
        brokerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<BrokerDTO> getBrokers() {
        return brokerRepository.findAll().stream()
                .map(BrokerConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BrokerDTO> getBrokerByText(String inputText) {
        List<Broker> brokers = brokerRepository.findByNameContainingIgnoreCase(inputText);
        return brokers.stream()
                .map(BrokerConverter::toDTO)
                .collect(Collectors.toList());
    }
}

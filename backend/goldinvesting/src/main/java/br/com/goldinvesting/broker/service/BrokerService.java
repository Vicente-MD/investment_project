package br.com.goldinvesting.broker.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.goldinvesting.broker.model.Broker;
import br.com.goldinvesting.broker.repository.BrokerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrokerService {
    private final BrokerRepository brokerRepository;

    @Transactional
    public Broker createBroker(Broker broker) {
        return brokerRepository.save(broker);
    }

    @Transactional
    public Broker getBrokerById(long id) {
        var broker = brokerRepository.findById(id);
        if (broker.isPresent())
            return broker.get();
        return null;
    }

    @Transactional
    public void deleteBroker(long id) {
        brokerRepository.deleteById(id);
    }

    @Transactional
    public List<Broker> getBrokers() {
        return brokerRepository.findAll();
    }

}

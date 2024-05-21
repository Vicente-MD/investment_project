package br.com.goldinvesting.application.ports.in;

import br.com.goldinvesting.application.dto.BrokerDTO;
import java.util.List;

public interface BrokerUseCase {
    BrokerDTO createBroker(BrokerDTO brokerDTO);
    BrokerDTO getBrokerById(long id);
    void deleteBroker(long id);
    List<BrokerDTO> getBrokers();
}

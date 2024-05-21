package br.com.goldinvesting.application.dto.converter;

import br.com.goldinvesting.application.dto.BrokerDTO;
import br.com.goldinvesting.domain.model.Broker;

public class BrokerConverter {

    public static BrokerDTO toDTO(Broker broker) {
        if (broker == null) {
            return null;
        }
        return new BrokerDTO(
                broker.getId(),
                broker.getName()
        );
    }

    public static Broker toEntity(BrokerDTO brokerDTO) {
        if (brokerDTO == null) {
            return null;
        }
        return new Broker(
                brokerDTO.getId(),
                brokerDTO.getName()
        );
    }
}

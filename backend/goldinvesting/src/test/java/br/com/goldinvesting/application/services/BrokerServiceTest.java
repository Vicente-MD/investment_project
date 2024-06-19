package br.com.goldinvesting.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.goldinvesting.application.dto.BrokerDTO;
import br.com.goldinvesting.application.dto.converter.BrokerConverter;
import br.com.goldinvesting.application.ports.out.BrokerRepository;
import br.com.goldinvesting.domain.model.Broker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class BrokerServiceTest {

    @Mock
    private BrokerRepository brokerRepository;

    @InjectMocks
    private BrokerService brokerService;

    private BrokerDTO brokerDTO;
    private Broker broker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        brokerDTO = new BrokerDTO();
        brokerDTO.setId(1L);
        brokerDTO.setName("Broker Name");

        broker = BrokerConverter.toEntity(brokerDTO);
    }

    @Test
    void testCreateBroker() {
        when(brokerRepository.save(any(Broker.class))).thenReturn(broker);

        BrokerDTO createdBrokerDTO = brokerService.createBroker(brokerDTO);

        assertNotNull(createdBrokerDTO, "The created BrokerDTO should not be null");
        assertEquals(broker.getId(), createdBrokerDTO.getId(), "The broker ID should match");
        assertEquals(broker.getName(), createdBrokerDTO.getName(), "The broker name should match");

        verify(brokerRepository, times(1)).save(any(Broker.class));
    }

    @Test
    void testGetBrokerById() {
        when(brokerRepository.findById(1L)).thenReturn(Optional.of(broker));

        BrokerDTO foundBrokerDTO = brokerService.getBrokerById(1L);

        assertNotNull(foundBrokerDTO, "The found BrokerDTO should not be null");
        assertEquals(broker.getId(), foundBrokerDTO.getId(), "The broker ID should match");

        verify(brokerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBrokerByText() {
        when(brokerRepository.findByNameContainingIgnoreCase("Broker")).thenReturn(List.of(broker));

        List<BrokerDTO> brokers = brokerService.getBrokerByText("Broker");

        assertNotNull(brokers, "The brokers list should not be null");
        assertEquals(1, brokers.size(), "The brokers list size should be 1");
        assertEquals(broker.getId(), brokers.get(0).getId(), "The broker ID should match");
        assertEquals(broker.getName(), brokers.get(0).getName(), "The broker name should match");

        verify(brokerRepository, times(1)).findByNameContainingIgnoreCase("Broker");
    }
}


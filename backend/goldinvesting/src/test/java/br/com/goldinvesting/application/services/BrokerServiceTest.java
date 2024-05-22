package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.dto.BrokerDTO;
import br.com.goldinvesting.application.ports.out.BrokerRepository;
import br.com.goldinvesting.domain.model.Broker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrokerServiceTest {

    @Mock
    private BrokerRepository brokerRepository;

    @InjectMocks
    private BrokerService brokerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBroker() {
        BrokerDTO brokerDTO = new BrokerDTO();
        brokerDTO.setName("Broker 1");

        Broker broker = new Broker();
        broker.setName("Broker 1");

        Broker savedBroker = new Broker();
        savedBroker.setId(1L);
        savedBroker.setName("Broker 1");

        when(brokerRepository.save(any(Broker.class))).thenReturn(savedBroker);

        BrokerDTO result = brokerService.createBroker(brokerDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Broker 1", result.getName());
    }

    @Test
    void getBrokerById() {
        Broker broker = new Broker();
        broker.setId(1L);
        broker.setName("Broker 1");

        when(brokerRepository.findById(1L)).thenReturn(Optional.of(broker));

        BrokerDTO result = brokerService.getBrokerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Broker 1", result.getName());
    }

    @Test
    void deleteBroker() {
        doNothing().when(brokerRepository).deleteById(1L);

        brokerService.deleteBroker(1L);

        verify(brokerRepository, times(1)).deleteById(1L);
    }

    @Test
    void getBrokers() {
        Broker broker1 = new Broker();
        broker1.setId(1L);
        broker1.setName("Broker 1");

        Broker broker2 = new Broker();
        broker2.setId(2L);
        broker2.setName("Broker 2");

        when(brokerRepository.findAll()).thenReturn(Arrays.asList(broker1, broker2));

        List<BrokerDTO> result = brokerService.getBrokers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Broker 1", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Broker 2", result.get(1).getName());
    }
}

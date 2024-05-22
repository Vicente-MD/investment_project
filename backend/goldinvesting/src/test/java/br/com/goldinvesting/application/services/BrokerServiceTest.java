package br.com.goldinvesting.application.services;

import br.com.goldinvesting.application.dto.BrokerDTO;
import br.com.goldinvesting.application.dto.converter.BrokerConverter;
import br.com.goldinvesting.application.ports.out.BrokerRepository;
import br.com.goldinvesting.domain.model.Broker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BrokerServiceTest {

    @Mock
    private BrokerRepository brokerRepository;

    @InjectMocks
    private BrokerService brokerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBroker() {
        BrokerDTO brokerDTO = new BrokerDTO();
        Broker broker = new Broker();
        Broker savedBroker = new Broker();

        when(BrokerConverter.toEntity(brokerDTO)).thenReturn(broker);
        when(brokerRepository.save(broker)).thenReturn(savedBroker);
        when(BrokerConverter.toDTO(savedBroker)).thenReturn(brokerDTO);

        BrokerDTO result = brokerService.createBroker(brokerDTO);

        assertEquals(brokerDTO, result);
        verify(brokerRepository, times(1)).save(broker);
    }

    @Test
    void testGetBrokerById() {
        long id = 1L;
        Broker broker = new Broker();
        BrokerDTO brokerDTO = new BrokerDTO();

        when(brokerRepository.findById(id)).thenReturn(Optional.of(broker));
        when(BrokerConverter.toDTO(broker)).thenReturn(brokerDTO);

        BrokerDTO result = brokerService.getBrokerById(id);

        assertEquals(brokerDTO, result);
        verify(brokerRepository, times(1)).findById(id);
    }

    @Test
    void testGetBrokerByIdNotFound() {
        long id = 1L;

        when(brokerRepository.findById(id)).thenReturn(Optional.empty());

        BrokerDTO result = brokerService.getBrokerById(id);

        assertNull(result);
        verify(brokerRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteBroker() {
        long id = 1L;

        doNothing().when(brokerRepository).deleteById(id);

        brokerService.deleteBroker(id);

        verify(brokerRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetBrokers() {
        Broker broker = new Broker();
        BrokerDTO brokerDTO = new BrokerDTO();
        List<Broker> brokers = Collections.singletonList(broker);
        List<BrokerDTO> brokerDTOs = Collections.singletonList(brokerDTO);

        when(brokerRepository.findAll()).thenReturn(brokers);
        when(BrokerConverter.toDTO(broker)).thenReturn(brokerDTO);

        List<BrokerDTO> result = brokerService.getBrokers();

        assertEquals(brokerDTOs, result);
        verify(brokerRepository, times(1)).findAll();
    }
}

package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokerRepository extends JpaRepository<Broker, Long> {
}

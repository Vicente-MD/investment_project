package br.com.goldinvesting.broker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.goldinvesting.broker.model.Broker;

@Repository
public interface BrokerRepository extends JpaRepository<Broker, Long> {
}

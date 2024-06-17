package br.com.goldinvesting.application.ports.out;

import br.com.goldinvesting.domain.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrokerRepository extends JpaRepository<Broker, Long> {
    @Query("SELECT b FROM Broker b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :inputText, '%'))")
    List<Broker> findByNameContainingIgnoreCase(@Param("inputText") String inputText);
}

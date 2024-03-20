package br.com.goldinvesting.stockdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.goldinvesting.stockdb.model.StockDB;

@Repository
public interface StockDBRepository extends JpaRepository<StockDB, String> {
}

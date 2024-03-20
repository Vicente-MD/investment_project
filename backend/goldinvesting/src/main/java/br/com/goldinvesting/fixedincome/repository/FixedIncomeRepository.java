package br.com.goldinvesting.fixedincome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.goldinvesting.fixedincome.model.FixedIncomeModel;

@Repository
public interface FixedIncomeRepository extends JpaRepository<FixedIncomeModel, Long>{
}
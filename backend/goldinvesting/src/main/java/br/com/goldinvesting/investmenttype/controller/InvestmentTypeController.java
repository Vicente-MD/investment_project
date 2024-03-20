package br.com.goldinvesting.investmenttype.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goldinvesting.investmenttype.model.InvestmentType;
import br.com.goldinvesting.investmenttype.repository.InvestmentTypeRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/investmentType")
@RequiredArgsConstructor
public class InvestmentTypeController {
    private final InvestmentTypeRepository investmentTypeRepository;

    @PostMapping
    public ResponseEntity<?> createInvestmentType(@RequestBody InvestmentType investmentType){
        return ResponseEntity.ok(investmentTypeRepository.save(investmentType));
    }

    @GetMapping
    public ResponseEntity<?> findInvestmentType(){
        return ResponseEntity.ok(investmentTypeRepository.findAll());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteInvestmentType(){
        investmentTypeRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
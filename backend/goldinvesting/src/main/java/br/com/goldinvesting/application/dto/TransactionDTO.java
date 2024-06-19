package br.com.goldinvesting.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private FeignInvestmentDTO investment;
    private InvestmentTypeDTO investmentType;
    private StatusDTO status;
}

package br.com.goldinvesting.application.dto;

import br.com.goldinvesting.domain.model.Investment;
import lombok.Data;

@Data
public class InvestmentDTO {
    private Long id;
    private String investmentName;
    private String investmentType;
    private Long investmentId;
    private String status;
    private Investment investment;
}

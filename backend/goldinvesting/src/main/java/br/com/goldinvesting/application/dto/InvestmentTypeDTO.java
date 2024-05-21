package br.com.goldinvesting.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentTypeDTO {
    private Long id;
    private String investmentType;
}

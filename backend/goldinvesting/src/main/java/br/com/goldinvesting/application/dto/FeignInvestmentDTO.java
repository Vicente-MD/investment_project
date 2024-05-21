package br.com.goldinvesting.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeignInvestmentDTO {
    private Long id;
    private String title;
    private String issuer;
    private double yieldRate;
    private String initialDate;
    private String finalDate;
    private double initialValue;
    private BrokerDTO broker;
}

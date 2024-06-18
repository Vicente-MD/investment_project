package br.com.goldinvesting.application.dto;

import java.time.LocalDate;

import br.com.goldinvesting.domain.model.Broker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedIncomeDTO {
    private Long id;
    private String paper;
    private String issuer = "";
    private double yieldRate;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private double initialValue;
    private Broker broker;
}

package br.com.goldinvesting.application.dto;

import br.com.goldinvesting.domain.model.Broker;
import br.com.goldinvesting.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckingAccountDTO {
    private Long id;
    private String title;
    private double yieldRate;
    private LocalDate initialDate;
    private double initialValue;
    private Broker broker;
    private User user;
}

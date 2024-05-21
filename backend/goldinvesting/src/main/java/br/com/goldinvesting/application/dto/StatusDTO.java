package br.com.goldinvesting.application.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    private Long id;
    @Size(min = 1, max = 50)
    private String status;
}

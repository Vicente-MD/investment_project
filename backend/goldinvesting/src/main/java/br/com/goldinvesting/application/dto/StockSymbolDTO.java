package br.com.goldinvesting.application.dto;

import br.com.goldinvesting.domain.model.Stock;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockSymbolDTO {
    private Long id;
    private String ticker;
    private String companyName;
}

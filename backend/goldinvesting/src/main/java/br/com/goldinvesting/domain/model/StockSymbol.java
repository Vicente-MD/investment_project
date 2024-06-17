package br.com.goldinvesting.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// símbolo da ação -> exemplo: AAPL para Apple

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockSymbol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String ticker;

    @NotNull
    @Size(min = 1, max = 100)
    private String companyName;

    @OneToMany(mappedBy = "stockSymbol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stock> stocks;
}

package br.com.goldinvesting.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockDB {
    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;
}

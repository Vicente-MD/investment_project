package br.com.goldinvesting.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity representing a broker.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Broker {

    /**
     * Unique identifier for the broker.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the broker.
     * This field is mandatory and must be between 2 and 100 characters.
     */
    @NotNull
    @Size(min = 2, max = 100)
    private String name;
}

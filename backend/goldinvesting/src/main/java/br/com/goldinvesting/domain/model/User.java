package br.com.goldinvesting.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the user.
     * This field is mandatory and must be between 1 and 100 characters.
     */
    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    /**
     * Email of the user.
     * This field is mandatory, must be a valid email format, and must be up to 100 characters.
     */
    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    /**
     * Password of the user.
     * This field is mandatory and must be between 8 and 100 characters.
     */
    @NotNull
    @Size(min = 8, max = 100)
    private String password;
}
